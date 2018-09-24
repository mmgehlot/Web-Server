package cmpe202.project.webserver;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

public class StartServer implements State {
	LoginHandler loginHandler;
	
	@Override
	public void doAction(ServerContext serverContext) throws IOException {
		
    	HttpServer server = serverContext.getServer();

    	if(serverContext.getServerState().equals("Stop")){
	    	AppContext appContext = new AppContext();
	    	ArrayList<String> appList = appContext.getAppList();
	    	
	    	for(String app : appList)
	    		if(!app.equalsIgnoreCase("ROOT"))
	    			server.createContext("/" + app, new RequestHandler(app));
	    	
//	    	HttpContext rootCtx =  server.createContext("/", new RequestHandler("ROOT"));
	    	HttpContext uploadCtx = server.createContext("/Upload", new FileUploader());
	    	HttpContext admintCtx = server.createContext("/admin", new AdminHandler());
	    	HttpContext adminCtx = server.createContext("/", new RootHandler());
//	    	HttpContext developerCtx = server.createContext("/", new DeveloperHandler());
	    	HttpContext shutdownCtx =  server.createContext("/Shutdown", new ShutdownHandler());
	    	
	    	adminCtx.setAuthenticator(getRootAuthenticator());
	    	uploadCtx.setAuthenticator(getRootAuthenticator());
	    	shutdownCtx.setAuthenticator(getRootAuthenticator());
//	    	developerCtx.setAuthenticator(getDeveloperAuthenticator());
	    	uploadCtx.setAuthenticator(getDeveloperAuthenticator());
	    	
	    	server.setExecutor(null);
	    	server.start();
	    	serverContext.setServerState("Start");
	    	System.out.println("Server has started");
    	}
	}
	
	private Authenticator getRootAuthenticator() {
		loginHandler = new LoginHandler();
		Authenticator authenticator = new BasicAuthenticator("ROOT") {
			public boolean checkCredentials(String username, String password) {
				return loginHandler.authenticate(username, password);
			}
		  };
		  return authenticator;
		}	

	private Authenticator getDeveloperAuthenticator() {
		loginHandler = new LoginHandler();
		Authenticator authenticator = new BasicAuthenticator("DEVELOPER") {
			public boolean checkCredentials(String username, String password) {
				return loginHandler.authenticate(username, password);
			}
		  };
		  return authenticator;
		}	
	
}
