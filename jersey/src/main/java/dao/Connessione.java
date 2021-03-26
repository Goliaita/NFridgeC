package dao;
import java.sql.*;

public class Connessione{
		
	private Connection conn = null;
	
	public Connection getConnection(){
		
		/*
		 * Class taken from Oracle's website
		 */
	
		try {

			conn = DriverManager.getConnection("jdbc:mysql://localhost/dbremoto?", "root", "");
				  
		} 
		
			catch (SQLException ex) {
				
				System.out.println("connessione.java");
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
		
			}
		
		return conn;
		
	}
	
}
