package main.java.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.server.database.DatabaseException;
import main.java.server.facade.*;
import main.java.shared.communication.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author tchambs
 *
 */
public class SearchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	private XStream xmlStream = new XStream(new DomDriver());	
	private ServerFacade facade = new ServerFacade();
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Search_Params params = (Search_Params)xmlStream.fromXML(exchange.getRequestBody());
		
		ArrayList<Search_Result> results = null;
		
		try {
			results = facade.search(params);
		}
		catch (DatabaseException | ServerFacadeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //0 means read until the end
		xmlStream.toXML(results, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}

}
