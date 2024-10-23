package server;

public class ClearHandler extends Handler{

    public ClearHandler() {
    }

    public String doHandle(){
        ClearService clearSer = new ClearService();
        clearSer.clear();
        return "{}";
    }
}
