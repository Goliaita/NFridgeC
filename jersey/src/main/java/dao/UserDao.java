package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDao {
	
	private Statement stmt;
	private ResultSet rs;
	
	
	private Connessione conn = new Connessione();
	
	
	private void closeInstance(){
		
		try{
			
			conn.getConnection().close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	public String doLogin(String Username, String Password) throws JSONException{
		
		JSONObject obj = new JSONObject();
		
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			try{
				
				stmt = conn.getConnection().createStatement();
				
				String query = "select * from user left join user_has_fridge on user.idUser = "
						+ "user_has_fridge.user_idUser where user.Username = \"" + Username + 
						"\" and user.Password = \"" + Password + "\"";
				
				rs = stmt.executeQuery(query);
				rs = stmt.getResultSet();

				rs.next();

				obj.put("Result", "true");
				
				obj.put("UserId", rs.getInt("idUser"));
				
				obj.put("FridgeId", rs.getInt("Fridge_idFridge"));
				
				
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
			
			
		}catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
				
			obj.put("Result", "false");
				
		}
		
		closeInstance();
				
		return obj.toString();
	}

}
