/**
 * 
 */
package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.database.DatabaseException;
import server.facade.ServerFacade;
import server.facade.ServerFacadeException;
import shared.communication.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author tchambs
 *
 */
public class GetSampleImageHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	private XStream xmlStream = new XStream(new DomDriver());	
	private ServerFacade facade = new ServerFacade();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		GetSampleImage_Params params = (GetSampleImage_Params)xmlStream.fromXML(exchange.getRequestBody());
		GetSampleImage_Result result = null;
		
		try {
			result = facade.GetSampleImage(params);
		}
		catch (ServerFacadeException | DatabaseException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //0 means read until the end
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close(); //if this fails, the server is unavailable/database is locked, restart both.
	}

}
