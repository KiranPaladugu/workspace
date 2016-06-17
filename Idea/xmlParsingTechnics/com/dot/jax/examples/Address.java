/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.jax.examples;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "house", "street", "city", "state", "zipcode" })
public class Address {
    private String house;
    private String street;
    private String city;
    private String state;
    private int zipcode;

    public synchronized String getHouse() {
        return house;
    }

    public synchronized void setHouse(String house) {
        this.house = house;
    }

    public synchronized String getStreet() {
        return street;
    }

    public synchronized void setStreet(String street) {
        this.street = street;
    }

    public synchronized String getCity() {
        return city;
    }

    public synchronized void setCity(String city) {
        this.city = city;
    }

    public synchronized String getState() {
        return state;
    }

    public synchronized void setState(String state) {
        this.state = state;
    }

    public synchronized int getZipcode() {
        return zipcode;
    }

    public synchronized void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Address [house=" + house + ", street=" + street + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode
                + "]";
    }

}
