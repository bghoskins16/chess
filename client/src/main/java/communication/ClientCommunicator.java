package communication;

import com.google.gson.Gson;
import model.AuthData;
import request.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientCommunicator {
    static Gson serializer = new Gson();


    public ClientCommunicator() {
    }

    public String register(RegisterRequest req){
        System.out.println("http request");
        try {
            URL url = new URL("http://localhost:8080/user");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.connect();

            try(OutputStream requestBody = connection.getOutputStream();) {
                // Write request body to OutputStream ...
                String message = serializer.toJson(req);
                System.out.println("sent message: " + message);
                requestBody.write(message.getBytes());
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();

                // OR

                //connection.getHeaderField("Content-Length");

                InputStream responseBody = connection.getInputStream();
                // Read response body from InputStream ...
                String responseText = new String(responseBody.readAllBytes(), StandardCharsets.UTF_8);
                AuthData authData = serializer.fromJson(responseText, AuthData.class);
                System.out.println("recieved auth: " + authData.authToken());
                return authData.authToken();
            }
            else {
                // SERVER RETURNED AN HTTP ERROR

                System.out.println("recieved bad status code");
                InputStream responseBody = connection.getErrorStream();
                // Read and process error response body from InputStream ...
            }


            serializer.toJson(req);
        }
        catch (Exception ex){
            System.out.println("exeption in http");
        }
        return null;
    }
}
