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
public class Course {
    private int id;
    private String title;
    private String stream;

    public int getId() {
        return id;
    }
 

    public String getTitle() {
        return title;
    }

    public String getStream() {
        return stream;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public Course() {
    }

    public Course(int id, String title, String stream) {
        this.id = id;
        this.title = title;
        this.stream = stream;
    }



    @Override
    public String toString() {
        return "id=" + id + ", title=" + title + ", stream=" + stream;
    }
    
    
}
