/**
 * 
 */
package main.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author tchambs
 *
 */
public class DownloadFileHandler implements HttpHandler {

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String filepath = exchange.getRequestURI().getPath();
		String file = "database" + File.separator + filepath;
		
		Path path = Paths.get(file);
		byte[] image = Files.readAllBytes(path);
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		OutputStream os = exchange.getResponseBody();
		os.write(image);
		os.close();
	}

}
