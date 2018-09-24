package cmpe202.project.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class AdminHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange t) throws IOException {
		String url = t.getRequestURI().toString();
		Headers head = t.getRequestHeaders();
		List<String> params = head.get("cmd");
		String cmd = params.get(0);
//		String[] cmdList = cmd.split(",");
		
		OutputStream out = t.getResponseBody();
		AppContext appContext = new AppContext();
		
		try{
			WebDBUtility w = new WebDBUtility();
			
			if(cmd.startsWith("Stop")){
				String appName = cmd.substring(cmd.indexOf(",")+1,cmd.length());
				WebDBEntry entry = w.getWebAppEntry(appName);
				if(entry.getStatus().equals("Start")){
					System.out.println(w.updateWebAppRunState(entry.getName(), "Stop"));
				}
			}
			
			if(cmd.equals("getapps")){
//				String apps = appContext.getAppList().toString();
//				apps = apps.substring(1,apps.length()-1);
				String apps = w.userWithAppList();
				System.out.println(apps);
				t.sendResponseHeaders(200, 0);
				out.write(apps.getBytes());
			}
			
			if(cmd.startsWith("CreateUser")){
				
			}
			
			if(cmd.startsWith("Start")){
				String appName = cmd.substring(cmd.indexOf(",")+1,cmd.length());
				WebDBEntry entry = w.getWebAppEntry(appName);
				if(entry.getStatus().equals("Stop")){
					System.out.println(w.updateWebAppRunState(entry.getName(), "Start"));
				}
			}
			
			if(cmd.startsWith("undeploy")){
				String appName = cmd.substring(cmd.indexOf(",")+1,cmd.length());
				if(w.deleteWebDBEntry(appName)){
					System.out.println("Deleted : " + appName);
				}
			}
			
			if(cmd.startsWith("deploy")){
				
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		out.close();
	}
	
	

}
