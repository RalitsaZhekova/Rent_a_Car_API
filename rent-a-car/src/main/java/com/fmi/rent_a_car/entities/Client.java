package com.fmi.rent_a_car.entities;

public class Client {

    private int id;
    private String name;
    private String address;
    private String phone;
    private int age;
    private int hasAccidents;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHasAccidents() {
        return hasAccidents;
    }

    public void setHasAccidents(int hasAccidents) {
        this.hasAccidents = hasAccidents;
    }
}
