package jersey.server;

import java.util.ArrayList;
import java.util.Comparator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.Catalogue;
import dao.CatalogueDao;



@Path("getFridgeContent/{FridgeId}")
public class FridgeContent {
	
	
	CatalogueDao catalogueDao = new CatalogueDao();
	
	ArrayList<Catalogue> catArray = new ArrayList<Catalogue>();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt(@PathParam("FridgeId") int FridgeId) throws JSONException {
    	
    	catArray = catalogueDao.getAllProduct(FridgeId);
    	
    	JSONObject obj = new JSONObject();
		
		obj.put("product", orderProduct(catArray));
    	    	
        return  obj.toString();
        
    }
    
    
    
    private JSONArray orderProduct(ArrayList<Catalogue> catArray){

    	
    	JSONArray arr = new JSONArray();
    	
    	
    	catArray.sort(new Comparator<Catalogue>(){

			@Override
			public int compare(Catalogue arg0, Catalogue arg1) {
				
				return arg0.getCategoria().compareTo(arg1.getCategoria());
			}
    		
    	});
    	
    	int count = 0; //Count number of a object in the fridge
    	    	
    	for(int i = 0; i < catArray.size(); i++){
    		
    		
    		if(i != catArray.size()-1){
    			    			
	    		if(catArray.get(i).getCategoria()
	    				.equals(catArray.get(i + 1).getCategoria())){
	    			
	    			count++;
	    			
	    		}else{
	    			
	    			count++;
	    			
	    			try {
	    				
	    				JSONObject obj = new JSONObject();
	    				
						obj.put("Categoria", catArray.get(i).getCategoria());
						
						obj.put("ProductQuantity", count);
						
						arr.put(obj);
												
						count = 0;
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
	    			
	    		}
    		
    		}else{
    			
    			count++;
    			
    			try {
    				
    				JSONObject obj = new JSONObject();
    				
					obj.put("Categoria", catArray.get(i).getCategoria());
					
					obj.put("ProductQuantity", count);
										
					arr.put(obj);
					
					count = 0;
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
    			
    		}
    		
    	}
    	
    	return arr;

    }
    
    }
