package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import java.sql.Statement;


public class FridgeHasCatalogueDao {
		
	private ArrayList<String> nfcIds = new ArrayList<String>();
	
	private Statement stmt;
	private ResultSet rs;
	
	private Connessione conn = new Connessione();

	
	/**
	 * Close connection to database
	 */
	
	private void closeInstance(){
		
		try{
			conn.getConnection().close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
	public ArrayList<String> getNfcIds(int FridgeId){
		
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		
			String query = "select * from fridge_has_catalogue where fridge_idFridge = " 
																			+ FridgeId + ";";
				
		
			try {
			
				stmt = conn.getConnection().createStatement();
			
				rs = stmt.executeQuery(query);
			
				rs = stmt.getResultSet();
		
				while(rs.next()){
				
					nfcIds.add(rs.getString(1));
				
				}
			
			}catch (SQLException e){
				e.printStackTrace();
				System.out.println(e.getSQLState());
			}finally {
				if (rs != null) {					
					try {						
						rs.close();					
					}catch(SQLException sqlEx){ 
						sqlEx.printStackTrace();
					} 						
					rs = null;						
				}				
				if (stmt != null) {					
					try {						
						stmt.close();				
					}catch(SQLException sqlEx){ 
						sqlEx.printStackTrace();
					} 					
				}				
					stmt = null;				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		closeInstance();
		
		return nfcIds;
		
	}
	
	
	

}
