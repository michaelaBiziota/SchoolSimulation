/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import individualproject.Menu;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Assignment;
import model.Course;
import model.User;
import utils.DButils;

/**
 *
 * @author bizmi
 */
public class DaoHeadMaster {

    static List<String> passlist = new ArrayList<>();

    private String password = "headmasterabc";
    String hashedPassword = DButils.hashingPassword(password);

    public void headMasterLogin() {

        Scanner sc = new Scanner(System.in);
        System.out.println("give your password");
        while (true) {
            String passwordlogin = sc.next();
            DaoHeadMaster da = new DaoHeadMaster();
            da.getPasswords();
            int count = 0;
            for (String p : passlist) {
                if (DButils.checkPassword(passwordlogin, p)) {
                    count++;
                }
            }
            if (count > 0) {
                System.out.println("Successful login");
                break;
            } else {
                System.out.println("Incorrect password. Try again.");
            }

        }
        Menu m=new Menu();
        m.menuHeadMaster();

    }

    public boolean insertPassword() {
        String sqlPrepInsert = "Update users Set password=? Where user_type=?";

        try {
            PreparedStatement stm = DButils.getconnection().prepareStatement(sqlPrepInsert);
            stm.setString(1, hashedPassword);
            stm.setString(2, "headmaster");
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
        }

        DButils.closeconnection();
        return true;

    }

    public List<String> getPasswords() {

        String select = "Select password from users where user_type=?";
        try {
            PreparedStatement k = DButils.getconnection().prepareStatement(select);
            k.setString(1, "headmaster");
            ResultSet sd = k.executeQuery();
            while (sd.next()) {
                String a = sd.getString("password");
                passlist.add(a);

            }
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
        }

        return passlist;

    }


    public void headmasterGetTrainersPerCourse() {
        while (true) {
            DaoCourse ds = new DaoCourse();
            DaoCoursesUsers cu=new DaoCoursesUsers();
            Scanner sc = new Scanner(System.in);
            System.out.println("All the courses are:");
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For which course do you want to see the trainers who teach it?Type the course id");
            int id = sc.nextInt();
            System.out.println("The trainers who teach this course are:");
            for (User u : cu.getTrainersPerCourse(id)) {
                System.out.println(u);
            }
            System.out.println("Do you want to see the trainers of another course?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }

    }




    public void headmasterGetAssignmentPerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoCoursesAssignments ca=new DaoCoursesAssignments();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For which course do you want to view the appointed assignments? Type the course id");
            int cid = sc.nextInt();
            System.out.println("The assignmets of this course are:");
            for (Assignment a : ca.getAssignmentsPerCourse(cid)) {
                System.out.println(a);
            }
            System.out.println("Do you want to see the appointed assignments of another course?Type yes or no.");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }



    public void headMasterGetSchedulePerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoSchedule s=new DaoSchedule();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("Select the course to view its schedule.Type the course id");
            int cid = sc.nextInt();
            System.out.println("This course is taught the following dates:");
            for (Date d : s.getSchedulePerCourse(cid)) {
                System.out.println(d);
            }
            System.out.println("See the schedule of another course?yes or no.");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }
    public void headMasterGetAllCourses(){
        Scanner sc=new Scanner(System.in);
        while(true){
        System.out.println("All the courses are:");
    DaoCourse dc=new DaoCourse();
    for(Course c: dc.getAllCourses()){System.out.println(c);}
                System.out.println("Return to the menu? type yes or no");
            if(sc.next().equals("yes")){break;}}
    }
    
    public void headMasterGetAllStudents(){
        while(true){
        System.out.println("All the students are:");
        DaoStudent ds=new DaoStudent();
        for(User u: ds.getAllStudents()){System.out.println(u);}
        Scanner sc=new Scanner(System.in);
            System.out.println("Return to the menu? type yes or no");
            if(sc.next().equals("yes")){break;}
        }
    
    }
    public void headMasterGetAllSTrainers(){
        while(true){
        System.out.println("All the trainers are:");
        DaoTrainer ds=new DaoTrainer();
        for(User u: ds.getAllTrainers()){System.out.println(u);}
        Scanner sc=new Scanner(System.in);
            System.out.println("Return to the menu? type yes or no");
            if(sc.next().equals("yes")){break;}
        }
    
    }
        public void headMasterGetAllSAssignments(){
        while(true){
        System.out.println("All the assignments are:");
        DaoAssignment ds=new DaoAssignment();
        for(Assignment u: ds.getAllAssignments()){System.out.println(u);}
        Scanner sc=new Scanner(System.in);
            System.out.println("Return to the menu? type yes or no");
            if(sc.next().equals("yes")){break;}
        }
    
    }

}
