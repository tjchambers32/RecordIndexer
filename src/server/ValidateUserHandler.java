/**
 * 
 * @author tchambs
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.facade.ServerFacadeException;
import shared.communication.*;

public class ValidateUserHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	private XStream xmlStream = new XStream(new DomDriver());	
	private ServerFacade facade = new ServerFacade();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		ValidateUser_Params params = (ValidateUser_Params)xmlStream.fromXML(exchange.getRequestBody());
		
		ValidateUser_Result result = null;
		
		try {
			result = facade.validateUser(params);
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