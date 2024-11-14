package communication;

import com.google.gson.Gson;
import model.AuthData;
import request.*;
import response.CreateGameResponse;
import response.ErrorResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientCommunicator {
    static Gson serializer = new Gson();
    String path = "http://localhost:8080";

    public ClientCommunicator() {
    }

    public String clear() {

        try {
            URL url = new URL(path + "/db");
            String responseText = httpDelete(url, null);

            if (responseText == null) {
                return null;
            }

            ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
            if (errorResponse.message() != null) {
                return errorResponse.message();
            }
            return responseText;

        } catch (Exception e) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String register(RegisterRequest req) {
        try {
            URL url = new URL(path + "/user");
            String message = serializer.toJson(req);

            String responseText = httpPost(url, message, null);

            if (responseText == null) {
                return null;
            }

            AuthData authData = serializer.fromJson(responseText, AuthData.class);

            if (authData.authToken() == null) {
                ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
                return errorResponse.message();
            }

            return authData.authToken();

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String login(LoginRequest req) {
        try {
            URL url = new URL(path + "/session");
            String message = serializer.toJson(req);
            String responseText = httpPost(url, message, null);

            if (responseText == null) {
                return null;
            }

            AuthData authData = serializer.fromJson(responseText, AuthData.class);

            if (authData.authToken() == null) {
                ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
                return errorResponse.message();
            }

            return authData.authToken();

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String logout(LogoutRequest req) {
        try {
            URL url = new URL(path + "/session");

            String responseText = httpDelete(url, req.authToken());

            if (responseText == null) {
                return null;
            }

            ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
            if (errorResponse.message() != null) {
                return errorResponse.message();
            }
            return responseText;

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String listGames(ListGamesRequest req) {
        try {
            URL url = new URL(path + "/game");

            String responseText = httpGet(url, req.authToken());

            if (responseText == null) {
                return null;
            }

            ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
            if (errorResponse.message() != null) {
                return errorResponse.message();
            }

            return responseText;

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String createGame(CreateGameRequest req) {
        try {
            URL url = new URL(path + "/game");
            String message = serializer.toJson(new CreateGameRequest(null, req.gameName()));

            String responseText = httpPost(url, message, req.authToken());
            if (responseText == null) {
                return null;
            }

            ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
            if (errorResponse.message() != null) {
                return errorResponse.message();
            }

            CreateGameResponse createGameResponse = serializer.fromJson(responseText, CreateGameResponse.class);
            return "" + createGameResponse.gameID();

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String joinGame(JoinGameRequest req) {
        try {
            URL url = new URL(path + "/game");
            String message = serializer.toJson(new JoinGameRequest(null, req.playerColor(), req.gameID()));

            String responseText = httpPut(url, message, req.authToken());
            if (responseText == null) {
                return null;
            }

            ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
            if (errorResponse.message() != null) {
                return errorResponse.message();
            }

            return responseText;

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
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
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    String httpGet(URL url, String authToken) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            // Set HTTP auth header, if necessary
            if (authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            connection.connect();

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
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }


    String httpDelete(URL url, String authToken) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);

            // Set HTTP auth header, if necessary
            if (authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            connection.connect();

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
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    String httpPut(URL url, String message, String authToken) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod("PUT");
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
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

}
