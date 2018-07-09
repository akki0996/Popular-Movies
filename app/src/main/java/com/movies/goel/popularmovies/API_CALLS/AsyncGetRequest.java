package com.movies.goel.popularmovies.API_CALLS;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncGetRequest extends AsyncTask<String, Void, String> {

    private static final String TAG = "RESULTS";

    @Override
    protected String doInBackground(String... params) {
        String myUrl = params[0];
        String resultStringFromServer = "";
        int responseCode;

        try {
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.connect();

            /* Response code from the server */
            responseCode = conn.getResponseCode();
            Log.d("Response Code", "" + responseCode);

            switch (responseCode) {
                case 200: /* Success */
                    resultStringFromServer = convertInputStreamToString(conn);
                    break;
                case 401: /*Authentication Issues */
                    resultStringFromServer = "Authentication Failed";
                    break;
                case 422: /* Invalid Parameters */
                    resultStringFromServer = "Invalid Parameters";
                    break;
                case 429: /* Invalid Parameters */
                    resultStringFromServer = "Request Reached Limit";
                    break;
                case 504: /* Server Timed Out */
                    resultStringFromServer = "Server Timed Out";
                    break;
                default:
                    resultStringFromServer = "Unknown Error";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStringFromServer;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }


    private String convertInputStreamToString(HttpURLConnection connection) {
        String inputLine;
        String resultFromServer = " ";
        try {
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) stringBuilder.append(inputLine);

            reader.close();
            streamReader.close();

            resultFromServer = stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultFromServer;
    }
}