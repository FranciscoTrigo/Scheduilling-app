/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yamif
 */
public class Cita {
    private String type;
    private int manyType;
    private String month;
    private int manyMonth;
    private String location;
    private int manyLocation;
    


//constructor
public Cita() {

}

public Cita(String type, int manyType, String month, int manyMonth, String location, int manyLocation) {
    setType(type);
    setManyType(manyType);
    setMonth(month);
    setManyMonth(manyMonth); 
    setLocation(location);
    setManyLocation(manyLocation);
}

public String getType() {
    return type;
}

public int getManyType() {
    return manyType;
}

public String getMonth() {
    return month;
}

public int getManyMonth() {
    return manyMonth;
}

public String getLocation() {
    return location;
}

public int getManyLocation() {
    return manyLocation;
}


/////////////////////////

public void setType(String type) {
    this.type = type;
}

public void setManyType(int manyType) {
    this.manyType = manyType;
}

public void setMonth(String month) {
    this.month = month;
}

public void setManyMonth(int manyMonth) {
    this.manyMonth = manyMonth;
}

public void setLocation(String location) {
    this.location = location;
}

public void setManyLocation(int manyLocation) {
    this.manyLocation = manyLocation;
}






}