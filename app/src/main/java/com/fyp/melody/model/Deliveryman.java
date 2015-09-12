package com.fyp.melody.model;

import com.fyp.melody.JSON.Json2Deliveryman;

/**
 * Created by Hananideen on 13/9/2015.
 */
public class Deliveryman {

    public String Name, Phone, Plate;

    public Deliveryman() {}

    public Deliveryman(String name, String phone, String plate){
        Name = name;
        Phone = phone;
        Plate = plate;
    }

    public Deliveryman (Json2Deliveryman jDeliveryman){
        Name = jDeliveryman.name;
        Phone = jDeliveryman.phone;
        Plate = jDeliveryman.plate;
    }

    public String getName(){
        return Name;
    }

    public void setName(String name){
        Name = name;
    }

    public String getPhone(){
        return Phone;
    }

    public void setPhone(String phone){
        Phone = phone;
    }

    public String getPlate(){
        return Plate;
    }

    public void setPlate(String plate){
        Plate = plate;
    }

}
