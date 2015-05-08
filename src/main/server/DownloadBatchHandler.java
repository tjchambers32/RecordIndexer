/**
 * 
 */
package main.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.server.database.DatabaseException;
import main.server.facade.ServerFacade;
import main.server.facade.ServerFacadeException;
import main.shared.communication.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author tchambs
 *
 */
public class DownloadBatchHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("recordindexer"); 
	private XStream xmlStream = new XStream(new DomDriver());	
	private ServerFacade facade = new ServerFacade();
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		DownloadBatch_Params params = (DownloadBatch_Params)xmlStream.fromXML(exchange.getRequestBody());
		
		DownloadBatch_Result result = null;
		
		try {
			result = facade.downloadBatch(params);
		}
		catch (DatabaseException | ServerFacadeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;
		}
		
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //0 means read until the end
		xmlStream.toXML(result, exchange.getResponseBody());
		exchange.getResponseBody().close();
	}
}
