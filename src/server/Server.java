package server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.database.*;

public class Server {

	private static final int SERVER_PORT_NUMBER = 8080;
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

	private Server() {
		return;
	}

	private void run() throws ServerFacadeException {

		logger.info("Initializing Database");

		ServerFacade.initialize();

		logger.info("Initializing HTTP Server");

		try {
			server = HttpServer.create(
					new InetSocketAddress(SERVER_PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
		} catch (IOException e) {

			 System.out.println("Could not create HTTP server: " +
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
	
	private HttpHandler validateUserHandler = new ValidateUserHandler();
	private HttpHandler getProjectsHandler = new GetProjectsHandler();
	
	private HttpHandler getSampleImageHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Process UpdateContact request

			// Database db = new Database();
			// db.startTransaction();
			// ...
			// db.endTransaction();
		}
	};

	private HttpHandler downloadBatchHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Process DeleteContact request

			// Database db = new Database();
			// db.startTransaction();
			// ...
			// db.endTransaction();
		}
	};
	
	private HttpHandler submitBatchHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Process DeleteContact request

			// Database db = new Database();
			// db.startTransaction();
			// ...
			// db.endTransaction();
		}
	};
	
	private HttpHandler getFieldsHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Process DeleteContact request

			// Database db = new Database();
			// db.startTransaction();
			// ...
			// db.endTransaction();
		}
	};
	
	private HttpHandler searchHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Process DeleteContact request

			// Database db = new Database();
			// db.startTransaction();
			// ...
			// db.endTransaction();
		}
	};
	
	private HttpHandler downloadFileHandler = new HttpHandler() {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Process DeleteContact request

			// Database db = new Database();
			// db.startTransaction();
			// ...
			// db.endTransaction();
		}
	};
	
	public static void main(String[] args) throws ServerFacadeException {
		new Server().run();
	}

}