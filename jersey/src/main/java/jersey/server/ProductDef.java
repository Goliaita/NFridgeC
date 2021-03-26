package jersey.server;

import java.util.ArrayList;

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




@Path("getProductDef/{productName}&{FridgeId}")
public class ProductDef {
	
	private CatalogueDao catDao = new CatalogueDao();
	
	private ArrayList<Catalogue> catArray= new ArrayList<Catalogue>();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIt(@PathParam("productName") String productName, 
												@PathParam("FridgeId") int id) throws JSONException {
	    			
		catArray = catDao.getAProduct(productName, id);
				
		JSONObject obj = new JSONObject();
		
		obj.put("product", getJsonArray(catArray));

	    	 
	    return  obj.toString();
	    
	}
	
	
	private JSONArray getJsonArray(ArrayList<Catalogue> arrayList){
		
		JSONArray array = new JSONArray();
		
		for(int i = 0; i < arrayList.size(); i++){
			
			try {
				
				JSONObject obj = new JSONObject();
				
				obj.put("ProductName", arrayList.get(i).getNomeProdotto());
				
				obj.put("ProductManifacturer", arrayList.get(i).getProduttore());
				
				obj.put("ExpryDate", arrayList.get(i).getScadenza());
				
				array.put(obj);
														
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return array;
	}
	
	
	
}
