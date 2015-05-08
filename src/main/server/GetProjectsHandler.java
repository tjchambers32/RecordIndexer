/**
 * 
 */
package main.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.server.database.DatabaseException;
import main.server.facade.*;
import main.shared.communication.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author tchambs
 *
 */
public class GetProjectsHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	private XStream xmlStream = new XStream(new DomDriver());	
	private ServerFacade facade = new ServerFacade();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetProjects_Params params = (GetProjects_Params)xmlStream.fromXML(exchange.getRequestBody());
		GetProjects_Result result = null;
		
		try {
			result = facade.getProjects(params);
		}
		catch (ServerFacadeException | DatabaseException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //0 means read until the end
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
