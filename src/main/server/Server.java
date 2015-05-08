package main.server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import main.server.database.*;
import main.server.facade.ServerFacade;
import main.server.facade.ServerFacadeException;

import com.sun.net.httpserver.*;

public class Server {

	private static final int DEFAULT_SERVER_PORT_NUMBER = 39640;
	private static final int MAX_WAITING_CONNECTIONS = 10;

	private static Logger logger;

	static {
		try {
			initLog();
		} catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}

	private static void initLog() throws IOException {

		Level logLevel = Level.FINE;

		logger = Logger.getLogger("recordindexer");
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);

		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	private HttpServer server;
	private HttpHandler validateUserHandler = new ValidateUserHandler();
	private HttpHandler getProjectsHandler = new GetProjectsHandler();
	private HttpHandler getSampleImageHandler = new GetSampleImageHandler();
	private HttpHandler downloadBatchHandler = new DownloadBatchHandler();	
	private HttpHandler submitBatchHandler = new SubmitBatchHandler();	
	private HttpHandler getFieldsHandler = new GetFieldsHandler();	
	private HttpHandler searchHandler = new SearchHandler();
	private HttpHandler downloadFileHandler = new DownloadFileHandler();
	
	private Server() {
		return;
	}

	private void run(int port) throws DatabaseException {

		logger.info("Initializing Database");

		try {
			ServerFacade.initialize();
		} catch (ServerFacadeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		logger.info("Initializing HTTP Server");

		try {
			server = HttpServer.create(
					new InetSocketAddress(port),
					MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {

			 System.out.println("Could not create HTTP main.server: " +
			 e.getMessage());
			 e.printStackTrace();

			logger.log(Level.SEVERE, e.getMessage(), e);

			return;
		}

		server.setExecutor(null); // use the default executor

		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/", downloadFileHandler);

		logger.info("Starting HTTP Server");

		server.start();
	}
	
	
	public static void main(String[] args) throws DatabaseException {
		if (args.length == 1)
			new Server().run(Integer.parseInt(args[0]));
		else
			new Server().run(DEFAULT_SERVER_PORT_NUMBER);
	}

}