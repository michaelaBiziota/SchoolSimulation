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
import utils.DButils;

/**
 *
 * @author bizmi
 */
public class DaoCoursesAssignments {

    public ArrayList<Assignment> getAssignmentsPerCourse(int courseId) {
        ArrayList assignmentsPerCourse = new ArrayList<>();
        try {
            String sql = "Select a.a_id,a.title,submissiondate from(assignments a INNER JOIN coursesandassignments ca on ca.a_id=a.a_id)" + "where ca.c_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, courseId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int aid = rs.getInt("a_id");
                String title = rs.getString("title");
                Date subd = rs.getDate("submissiondate");
                Assignment a = new Assignment(aid, title, subd);
                assignmentsPerCourse.add(a);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) { 
            Logger.getLogger(DaoCoursesAssignments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assignmentsPerCourse;
    }

    public void updateAssignmentsPerCourse() {
        while (true) {
            try {
                System.out.println("All the courses are:");
                DaoCourse ds = new DaoCourse();
                DaoCoursesAssignments da=new DaoCoursesAssignments();
                DaoAssignment dg=new DaoAssignment();
                Scanner sc = new Scanner(System.in);
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("For which course do you want to update the " + "data about the appointed assignments? Type the course id");
                int cid = sc.nextInt();
                System.out.println("This course has the following assignments:");
                for (Assignment a : da.getAssignmentsPerCourse(cid)) {
                    System.out.println(a);
                }
                System.out.println("For which assignmet this course's data need to be changed? Type the assignment id.");
                int aid = sc.nextInt();
                System.out.println("Now select the assignment that should replace the one selected. Type the assignment id");
                System.out.println("Remember that all the assignments are:");
                for (Assignment a : dg.getAllAssignments()) {
                    System.out.println(a);
                }
                int naid = sc.nextInt();
                String sql = "Update coursesandassignments Set a_id=? where a_id=? and c_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, naid);
                pst.setInt(2, aid);
                pst.setInt(3, cid);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Successful data update");
                System.out.println("Do you want to update more data about the appointed assignments in a course?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createAnAssignemtPerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoAssignment da=new DaoAssignment();
            DaoCoursesAssignments dca=new DaoCoursesAssignments();
            DaoAssignmentsUsers au=new DaoAssignmentsUsers();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("In which course do you want to appoint an assignment? Type the course id");
            int cid = sc.nextInt();
            while (true) {
                try {
                    System.out.println("Which assignment do you want to appoint to the selected course?" + "Type the assignment id");
                    System.out.println("All the assignments are:");
                    for (Assignment a : da.getAllAssignments()) {
                        System.out.println(a);
                    }
                    System.out.println("Remember that this course has the following assignments:");
                    for (Assignment a : dca.getAssignmentsPerCourse(cid)) {
                        System.out.println(a);
                    }
                    int aid = sc.nextInt();
                    String sql = "Insert into coursesandassignments(c_id,a_id)Values (?,?)";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, cid);
                    pst.setInt(2, aid);
                    pst.executeUpdate();
                    pst.close();
                    DButils.closeconnection();
                    System.out.println("Successful new data entry.");
                    au.createAssignmentsForAllUsers(aid, cid);
                    System.out.println("Do you want to appoint another assignment in this course?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Do you want to appoint an assignment in another course?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }

    public void deleteAssignmentPerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoCoursesAssignments s=new DaoCoursesAssignments();
            DaoAssignmentsUsers au= new DaoAssignmentsUsers();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("Select the course you want to delete an appointed assignment.Type the course id");
            int cid = sc.nextInt();
            while (true) {
                try {
                    System.out.println("This course has the following assignments:");
                    for (Assignment a : s.getAssignmentsPerCourse(cid)) {
                        System.out.println(a);
                    }
                    System.out.println("Which assignment do you want to delete from this course?Type the assignment id");
                    int aid = sc.nextInt();
                    String sql = "Delete from coursesandassignments where c_id=? and a_id=?";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, cid);
                    pst.setInt(2, aid);
                    pst.executeUpdate();
                    pst.close();
                    DButils.closeconnection();
                    System.out.println("Deleted successfully");
                    au.deleteAnAssignmentForUsers(aid);
                    System.out.println("The assignment has also been deleted from the students that attend this course");
                    System.out.println("Do you want to delete another assignment from this course?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Do you want to delete an assignment from another course?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }
    public void deleteAnAssignmentFromAssignmentCourses(int aid){
            try {
            String sql="Delete from coursesandassignments where a_id=?";
            PreparedStatement pst=DButils.getconnection().prepareStatement(sql);
            pst.setInt(1,aid);
            pst.executeUpdate();
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void deleteACourseFromAssignmentCourses(int cid){
            try {
            String sql="Delete from coursesandassignments where c_id=?";
            PreparedStatement pst=DButils.getconnection().prepareStatement(sql);
            pst.setInt(1,cid);
            pst.executeUpdate();
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }}
}
