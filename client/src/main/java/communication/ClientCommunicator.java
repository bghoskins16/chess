package communication;

import com.google.gson.Gson;
import request.*;

public class ClientCommunicator {
    static Gson serializer = new Gson();


    public ClientCommunicator() {
    }

    public String register(RegisterRequest req){
        serializer.toJson(req);

        return "auth";

    }
}
