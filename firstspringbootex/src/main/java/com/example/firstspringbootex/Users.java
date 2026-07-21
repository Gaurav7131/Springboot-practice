//This class is basically hold the actual and used jackson-databinding
package com.example.firstspringbootex;

import java.util.List;

public class Users {
    private String name;
    private int age;
    private String email;

    // default constructor needed for jackson
    public Users() {
    }

    public Users(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

class ValidationSplit {
    private List<Users> valid;
    private List<Users> invalid;

    // constuctor
    public ValidationSplit(List<Users> valid, List<Users> invalid) {
        this.valid = valid;
        this.invalid = invalid;
    }

    public List<Users> getvalid = valid;
    public List<Users> getinvalid = invalid;

}
