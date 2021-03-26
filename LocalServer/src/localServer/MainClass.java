package localServer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.JerseyClientBuilder;


public class MainClass {
	
	final static int FridgeId = 10;

	public static void main(String[] args) {
		
		
		Client client = JerseyClientBuilder.newClient();
				
		WebTarget target = client.target("http://192.168.1.2:8080/jersey/webapi/PutData/"
																		+ args[0] + "&" + FridgeId);
		
		target.request().post(Entity.text("http://192.168.1.2:8080/jersey/webapi/PutData/"
																		+ args[0] + "&" + FridgeId));
		
	}

}
