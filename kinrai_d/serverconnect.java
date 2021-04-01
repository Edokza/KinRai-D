package com.tni.pattarapong.kinrai_d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class serverconnect {
    private static HttpURLConnection connection;

    public static String getData(String path) throws InterruptedException {
        String[] returnvalue = new String[1];
        final CountDownLatch latch = new CountDownLatch(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    returnvalue[0] = (String) sentgetrequest(path);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        latch.await();
        return returnvalue[0];
    }

    private static String sentgetrequest(String path){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try{
            URL url = new URL("https://kinrai-d-3e7dc-default-rtdb.firebaseio.com/" + path + ".json");
            connection  = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            //System.out.println("Noerr");
            //System.out.println(responseContent.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return responseContent.toString();
    }

    public static void addData(String path, String content){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    sentputrequest(path, content);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }

    private static void sentputrequest(String path, String content){
        String oldData = sentgetrequest(path);
        try{
            URL url = new URL("https://kinrai-d-3e7dc-default-rtdb.firebaseio.com/" + path + ".json");
            connection  = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            //connection.setRequestProperty("Content-Type", "application/json; utf-8");
            //connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);


            String jsonInputString;
            if(oldData != "ul") {
                oldData = oldData.substring(1, oldData.length()-1);
                content = content.substring(1, content.length()-1);
                jsonInputString = "{" + oldData + ", " + content + "}";
            }else{
                jsonInputString ="{" + content + "}";
            }
            System.out.println("jsonInputString");
            System.out.println(jsonInputString);


            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                //System.out.println(response.toString());
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }//function sentputrequest
}
