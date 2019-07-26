/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
public class DaoAssignment {

    public ArrayList<Assignment> getAllAssignments() {
        ArrayList<Assignment> allAssignments = new ArrayList<>();
        try {
            String sql = "Select * from Assignments";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("a_id");
                String title = rs.getString("title");
                Date submissionDate = rs.getDate("submissiondate");
                Assignment a1 = new Assignment(id, title, submissionDate);
                allAssignments.add(a1);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) { 
            Logger.getLogger(DaoAssignment.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allAssignments;
    }



    public void updateAnAssignment() {
        while (true) {
            try {
                DaoAssignment ds = new DaoAssignment();
                DaoSchedule dt=new DaoSchedule();
                Scanner sc = new Scanner(System.in);
                Scanner sc1 = new Scanner(System.in);
                System.out.println("All the assignments are:");
                for (Assignment c : ds.getAllAssignments()) {
                    System.out.println(c);
                }
                System.out.println("Which assignment do you want to update? Select by typing the assignment id.");
                int id = sc.nextInt();
                System.out.println("Write the updated title.");
                String title = sc1.nextLine();
                System.out.println("Now give the updated submission date (yyyy-MM-dd)");
                String d = sc.next();
                java.sql.Date sqld = dt.setDates(d);
                String sql = "Update assignments Set title=?, submissiondate=? where a_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, title);
                pst.setDate(2, sqld);
                pst.setInt(3, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Successful update");
                System.out.println("Do you want to update more assignments?");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createAnAssignment() {
        while (true) {
            try {
                DaoAssignment ds = new DaoAssignment();
                DaoSchedule dt=new DaoSchedule();
                Scanner sc = new Scanner(System.in);
                Scanner sc1 = new Scanner(System.in);
                System.out.println("All the assignments are:");
                for (Assignment c : ds.getAllAssignments()) {
                    System.out.println(c);
                }
                System.out.println("TO insert a new assignment enter its title.");
                String title = sc1.nextLine();
                System.out.println("Now enter the submission date (yyyy-MM-dd)");
                String d = sc.next();
                java.sql.Date sqld = dt.setDates(d);
                String sql = "INSERT INTO assignments (title,submissiondate)VALUES(?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, title);
                pst.setDate(2, sqld);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("You inserted a new assignment successfully.");
                System.out.println("Do you want to insert more assignments in the system?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteAnAssignmet() {
        while (true) {
            try {
                DaoAssignment ds = new DaoAssignment();
                DaoCoursesAssignments ca=new DaoCoursesAssignments();
                DaoAssignmentsUsers au=new DaoAssignmentsUsers();
                Scanner sc = new Scanner(System.in);
                System.out.println("All the assignments are:");
                for (Assignment c : ds.getAllAssignments()) {
                    System.out.println(c);
                }
                System.out.println("Which assignment do you want to delete? Select by typing its id.");
                int id = sc.nextInt();
                au.deleteAnAssignmentForUsers(id);
                ca.deleteAnAssignmentFromAssignmentCourses(id);
                String sql = "Delete from assignments where a_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Assignment deleted successfully.");
                System.out.println("This assignment has also been deleted from assignments and users and from assignments and courses");
                System.out.println("Do you want to delete another assignement?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void submitAnAssignment(int studId) {
        Scanner sc = new Scanner(System.in);
        DaoAssignmentsUsers au = new DaoAssignmentsUsers();
        while (true) {
            try {
                System.out.println("The list of  the assignments that are designed for you and have not been submitted yet is:");
                if (au.getAssignmentsPerStudentUnsubmitted(studId).isEmpty()) {
                    System.out.println("You have submitted all the assignments");
                    break;
                }
                for (Assignment a : au.getAssignmentsPerStudentUnsubmitted(studId)) {
                    System.out.println(a);
                }
                System.out.println("Which assignment do you want to submit? Type its id.");
                int aId = sc.nextInt();
                String sql = "Update usersandassignments SET submitted=true where user_id=? and a_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, studId);
                pst.setInt(2, aId);
                pst.executeUpdate();
                System.out.println("Successful submit. Do you want to submit another assignment? Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
                pst.close();
                DButils.closeconnection();
            } catch (SQLException ex) {
                Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
            }
            DButils.closeconnection();
        }
    }
    

    
}
