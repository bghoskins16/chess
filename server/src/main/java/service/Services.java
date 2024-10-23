package service;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;

public class Services {
    final DataAccess database;

    public Services(DataAccess dataAccess) {
         database = dataAccess;
    }

    public Services() {
        database = new MemoryDataAccess();
    }

    public void clear(){
        database.clear();
    }

}
