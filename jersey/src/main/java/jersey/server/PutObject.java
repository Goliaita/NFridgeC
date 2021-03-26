package jersey.server;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import dao.CatalogueDao;


@Path("PutData/{nfcId}&{FridgeId}")
public class PutObject {
	
	private CatalogueDao catDao = new CatalogueDao();
	
	@POST
	public void putIt(@PathParam("nfcId") String nfcId, @PathParam("FridgeId") int FridgeId) {
				
		catDao.putObject(nfcId, FridgeId);
		
	}

}
