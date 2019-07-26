/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author bizmi
 */
public class Schedule {
    private Date shceduleDate;
    private Course course;

    public Schedule(Date shceduleDate, Course course) {
        this.shceduleDate = shceduleDate;
        this.course = course;
    }

    public Date getShceduleDate() {
        return shceduleDate;
    }

    public Course getCourse() {
        return course;
    }

    public void setShceduleDate(Date shceduleDate) {
        this.shceduleDate = shceduleDate;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    
    
    
}
