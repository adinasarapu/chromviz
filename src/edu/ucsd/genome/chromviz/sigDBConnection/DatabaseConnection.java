//This singleton class will get the instance of the database connection
package edu.ucsd.genome.chromviz.sigDBConnection;

import java.sql.*;
import java.io.*;
import java.lang.Exception;

/**
 * get connection with the database through the properties file
 */

public class DatabaseConnection {
	private static Connection con;

	public DatabaseConnection() {
	}

	public static Connection getConnection(String props) throws SQLException,
			IOException, Exception {
		//System.out.println("Is there MySQL connection for " + props + " file?");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection("jdbc:mysql://flygenics.com/" + "flygen_chromviz","flygen_chromviz", "chromviz1234");
		//con = DriverManager.getConnection("jdbc:mysql://localhost/" + "chromviz","root", "upgma123");
		//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + props,"root", "upgma123");
		//System.out.println("I got connection from " + props + " file");				
		return con;
	}
}
