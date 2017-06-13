package by.tilalis.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class PGManager {
	private static final String settings = "/sql.xml";
	protected static Connection connection;
	protected static Statement statement;
	protected static Document document;
	protected static XPath xpath;
	
	public PGManager() {
		try {
			InputStream file = PGUserManager.class.getResourceAsStream(settings);
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			XPathFactory pathFactory = XPathFactory.newInstance();

			xpath = pathFactory.newXPath();
			document = documentBuilder.parse(file);

			connection = DatabaseConnectionManager.getInstance().getConnection();
			statement = connection.createStatement();
		} catch (SQLException | NamingException | ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
}
