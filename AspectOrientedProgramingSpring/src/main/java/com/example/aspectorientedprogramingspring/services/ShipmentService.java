package com.example.aspectorientedprogramingspring.services;

public interface ShipmentService {
    String orderPackage(long id);
    String trackPackage(long id) throws Exception;

}
