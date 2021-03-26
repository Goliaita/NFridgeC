package com.raffide.nfridgec.jerseyClient;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;


class MyJerseyClient {


    private final String getFridgeContent = "getFridgeContent";
    private final String getProductDef = "getProductDef";
    private final String Login = "Login";

    private WebTarget web;

    private WebTarget getWeb() {
        return web;
    }

    private void setWeb(WebTarget web) {
        this.web = web;
    }

    private String getResponse() {
        return response;
    }

    private void setResponse(String response) {
        this.response = response;
    }

    private String response;



    String getForString(String url, String path, String[] param) {

        Client client = JerseyClientBuilder.newClient().register(AndroidFriendlyFeature.class);


        switch(path){

            case getFridgeContent:

                setWeb(client.target(url).path("/" + getFridgeContent + "/" + param[0]));

                setResponse(getWeb().request().get().readEntity(String.class));

                break;


            case getProductDef:

                setWeb(client.target(url).path("/" + getProductDef + "/" + param[0] + "&" + param[1]));

                setResponse(getWeb().request().get().readEntity(String.class));

                break;


            case Login:

                setWeb(client.target(url).path("/" + Login + "/" + param[0] + "&" + param[1]));

                JSONObject obj = new JSONObject();

                try {
                    obj = new JSONObject(getWeb().request().get().readEntity(String.class));

                    System.out.println(getWeb().request().get().readEntity(String.class));

                    obj.put("Error", "no");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setResponse(obj.toString());

                break;

        }

        return getResponse();

    }

}
