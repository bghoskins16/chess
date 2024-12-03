package communication;

import com.google.gson.Gson;
import model.AuthData;
import request.*;
import response.CreateGameResponse;
import response.ErrorResponse;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HTTPCommunicator {
    static Gson serializer = new Gson();
    String path = "http://localhost:";

    public HTTPCommunicator(int port) {
        path = "http://localhost:" + port;
    }

    public String clear() {

        try {
            URL url = new URL(path + "/db");
            String responseText = httpSend("DELETE", url, null, null);

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

            String responseText = httpSend("POST", url, message, null);

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
            String responseText = httpSend("POST", url, message, null);
            if (responseText == null) {
                return null;
            }

            AuthData auth = serializer.fromJson(responseText, AuthData.class);
            if (auth.authToken() == null) {
                ErrorResponse errorResponse = serializer.fromJson(responseText, ErrorResponse.class);
                return errorResponse.message();
            }

            return auth.authToken();

        } catch (Exception ex) {
            System.out.println("Error: Problem with connection, please try again");
        }
        return null;
    }

    public String logout(LogoutRequest req) {
        try {
            URL url = new URL(path + "/session");

            String responseText = httpSend("DELETE", url, null, req.authToken());

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

            String responseText = httpSend("GET", url, null, req.authToken());

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

            String responseText = httpSend("POST", url, message, req.authToken());
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

            String responseText = httpSend("PUT", url, message, req.authToken());
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

    String httpSend(String method, URL url, String message, String authToken) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(5000);
            connection.setRequestMethod(method);
            connection.setDoOutput(true);

            // Set HTTP auth header, if necessary
            if (authToken != null) {
                connection.addRequestProperty("Authorization", authToken);
            }

            connection.connect();

            if (!method.equals("GET")) {
                OutputStream requestBody = connection.getOutputStream();
                // Write request body to OutputStream ...
                if (message != null) {
                    requestBody.write(message.getBytes());
                }
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
