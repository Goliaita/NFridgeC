package com.raffide.nfridgec.jerseyClient;

import android.os.AsyncTask;




public abstract class GetTask extends AsyncTask<String, String, String>{


    private MyJerseyClient myClient;

    private String url;

    private String path;
    private String[] param;


    public abstract void receiveData(Object object);




    protected GetTask(String path, String url, String[] param){

        this.path = path;
        this.param = param;
        this.url = url;
        myClient = new MyJerseyClient();
    }

    @Override
    protected String doInBackground(String... params){

        return myClient.getForString(url, path, param);

    }


    @Override
    protected void onPostExecute(String result){

        receiveData(result);

    }
}
