package communication;

import com.google.gson.Gson;
import model.AuthData;
import request.*;
import response.ErrorResponse;

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

    public String register(RegisterRequest req) {
        try {
            URL url = new URL("http://localhost:8080/user");
            String message = serializer.toJson(req);

            String responseText = httpPost(url, message, null);
            AuthData authData = serializer.fromJson(responseText, AuthData.class);

            if (authData.authToken() == null){
                ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
                return errorResponse.message();
            }

            return authData.authToken();

        } catch (Exception ex) {
            System.out.println("exception in register");
        }
        return null;
    }

    String httpPost(URL url, String message, String authToken) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Set HTTP auth header, if necessary
            if (authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            connection.connect();

            OutputStream requestBody = connection.getOutputStream();
            // Write request body to OutputStream ...
            if (message != null) {
                requestBody.write(message.getBytes());
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();
                // Read response body from InputStream ...
                return new String(responseBody.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                // SERVER RETURNED AN HTTP ERROR
                InputStream responseBody = connection.getErrorStream();
                // Read and process error response body from InputStream ...
                return new String(responseBody.readAllBytes(), StandardCharsets.UTF_8);

            }
        } catch (Exception ex) {
            System.out.println("exception in http");
        }

        return null;
    }
}
