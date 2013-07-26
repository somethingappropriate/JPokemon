package org.jpokemon.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jpokemon.JPokemonConstants;

/**
 * Main server component of the entire project. Contains the server and all of
 * its configuration, and assigns the servlets and filtering, as well as
 * contexts, etc.
 * 
 * @author Graham
 */
public class JPokemonServerMain {
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();

		logger.trace("Spinning up servlets");
		ServletContextHandler root = new ServletContextHandler();
		root.setContextPath("/");
		root.addServlet(new ServletHolder(new FileServlet()), "/*");
		root.addServlet(new ServletHolder(new PlayerServlet()), PlayerServlet.URL_PATH);
		root.addServlet(new ServletHolder(new LoginServlet()), LoginServlet.URL_PATH);

		// Instantiate the server on the specified port
		logger.info("Server starting on port " + JPokemonConstants.SERVER_PORT);
		Server server = new Server(JPokemonConstants.SERVER_PORT);
		server.setHandler(root);
		server.start();
		server.join();
	}

	private static Logger logger = Logger.getLogger(JPokemonServerMain.class);
}