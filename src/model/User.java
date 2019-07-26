/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author bizmi
 */
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String user_type;
    private String password;

    public User() {
    }

    public User(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }


    

    public User(String firstname, String lastname, String user_type, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.user_type = user_type;
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname;
    }
    
    


    
    
    

}
