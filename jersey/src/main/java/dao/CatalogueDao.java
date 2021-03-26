package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Statement;



public class CatalogueDao {
	
	private Catalogue catalogue;
	private FridgeHasCatalogueDao fricatDao = new FridgeHasCatalogueDao();
	private Connessione conn = new Connessione();
	
	private ArrayList<Catalogue> catArray = new ArrayList<Catalogue>();
	private ArrayList<String> catIds = new ArrayList<String>();
	
	
	
	private Statement stmt;
	private ResultSet rs;
		

	/**
	 * Close connection to database
	 */
	
	private void closeInstance(){
		
		try{
			
			conn.getConnection().close();
			
			stmt = null;
			rs = null;
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public ArrayList<Catalogue> getAProduct(String categories, int FridgeId){
		
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			catArray.clear();
			
			try{
				
				stmt = conn.getConnection().createStatement();
				
				String query = "Select * from catalogue left join fridge_has_catalogue on "
						+ "catalogue.nfcId = fridge_has_catalogue.catalogue_nfcId "
						+ "where fridge_has_catalogue.fridge_idFridge = " + FridgeId + " "
						+ "and catalogue.Category = \"" + categories + "\"";
				
				rs = stmt.executeQuery(query);
				
				rs = stmt.getResultSet();
				
				while(rs.next()){
					
					String nfcId = rs.getString(1);
					
					String nomeProdotto = rs.getString(2);
					
					String Categoria = rs.getString(3);
					
					String Produttore = rs.getString(4);
						
					String Scadenza = rs.getDate(5).toString();	
						
					catalogue = new Catalogue(nfcId, nomeProdotto, Categoria,
															Produttore, Scadenza);
 					catArray.add(catalogue);
					
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
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
		
		return catArray;
		
	}
	
	public ArrayList<Catalogue> getAllProduct(int FridgeId){
		
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
					
			catArray.clear();
			
			catIds = fricatDao.getNfcIds(FridgeId);
			
			for(int i = 0; i < catIds.size(); i++){
				
				String query = "select * from catalogue where nfcId = \"" 
															+ catIds.get(i) + "\"";
				
				
				try {
					
					stmt = conn.getConnection().createStatement();
					
					rs = stmt.executeQuery(query);
					
					rs = stmt.getResultSet();
					
					rs.next();
					
					String nomeProdotto = rs.getString(2);
						
					String Categoria = rs.getString(3);
					
					String Produttore = rs.getString(4);
						
					String Scadenza = rs.getDate(5).toString();
						
					catalogue = new Catalogue(catIds.get(i), nomeProdotto, Categoria,
																Produttore, Scadenza);
 					catArray.add(catalogue);
					
				}catch (SQLException e) {
					e.printStackTrace();
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
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		closeInstance();
		
		return catArray;
		
	}
	
	
	public void putObject(String nfcId, int FridgeId){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			try{
				
				
				String query = "insert into fridge_has_catalogue (fridge_idFridge, catalogue_nfcId)"
																		+ "values (?, ?)";
				
				PreparedStatement preparedStmt = conn.getConnection().prepareStatement(query);
				
				preparedStmt.setInt(1, FridgeId);
				
				preparedStmt.setString(2, nfcId);
				
				preparedStmt.execute();
		
			}catch (SQLException e) {
				

				try{
					
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					String query = "delete from fridge_has_catalogue where fridge_idFridge = " + FridgeId +
											" and catalogue_nfcId = \"" + nfcId + "\"";
					
					stmt = conn.getConnection().createStatement();
					
					stmt.executeUpdate(query);
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}finally {
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
		
	}
	
}
