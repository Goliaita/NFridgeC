package jersey.server;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import dao.UserDao;




@Path("Login/{Username}&{Password}")
public class Login {
	

		private UserDao userDao = new UserDao();		
		
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public String getIt(@PathParam("Username") String Username, 
								@PathParam("Password")  String Password) throws JSONException{

		    return  userDao.doLogin(Username,Password);
		    
		}
		
	}



