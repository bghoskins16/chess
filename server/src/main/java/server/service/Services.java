package server.service;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;

public class Services {
    DataAccess database = new MemoryDataAccess();

    public Services() {
    }
}