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
public class Assignment {
    private int id;
    private String title;
    private Date submissiondate;

    public Assignment() {
    }

    public String getTitle() {
        return title;
    }

    public Date getSubmissiondate() {
        return submissiondate;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubmissiondate(Date submissiondate) {
        this.submissiondate = submissiondate;
    }

    public int getId() {
        return id;
    }

    public Assignment(int id, String title, Date submissiondate) {
        this.id = id;
        this.title = title;
        this.submissiondate = submissiondate;
    }

    @Override
    public String toString() {
        return  "id=" + id + ", title=" + title + ", submissiondate=" + submissiondate;
    }






    
    
}
