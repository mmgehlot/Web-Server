package cmpe202.project.webserver;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange t) throws IOException {
		String url = t.getRequestURI().toString();

		OutputStream out = t.getResponseBody();
		if(url.equals("/"))
			url = "manager.html";
		
		String path = Constants.APP_HOME + "\\root\\" + url;
		StaticResourceHandler.send(t,path,out);
		out.close();
	}
}
