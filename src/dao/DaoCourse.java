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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import utils.DButils;

/**
 *
 * @author bizmi
 */
public class DaoCourse {

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> allCourses = new ArrayList<>();
        try {
            String sql = "Select * from Course";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("c_id");
                String title = rs.getString("title");
                String stream = rs.getString("stream");
                Course c1 = new Course(id, title, stream);
                allCourses.add(c1);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) { 
            Logger.getLogger(DaoCourse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allCourses;
    }

    public void createACourse() {
        while (true) {
            try {
                DaoCourse ds = new DaoCourse();
                Scanner sc = new Scanner(System.in);
                System.out.println("All the existing courses are:");
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("TO create a new course enter the course name.");
                String title = sc.nextLine();
                System.out.println("Now type the course stream.");
                String stream = sc.nextLine();
                String sql = "INSERT INTO course(title,stream)VALUES(?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, title);
                pst.setString(2, stream);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("You created the new course successfully.");
                System.out.println("Do you want to create more courses?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateACourse() {
        while (true) {
            try {
                DaoCourse ds = new DaoCourse();
                Scanner sc = new Scanner(System.in);
                Scanner sc1 = new Scanner(System.in);
                System.out.println("All the existing courses are:");
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("Which course do you want to update? Select by typing its id.");
                int id = sc.nextInt();
                System.out.println("Type the updated course title");
                String title = sc1.nextLine();
                System.out.println("Type the updated stream course");
                String stream = sc1.nextLine();
                String sql = "Update course SET title=?, stream=? where c_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, title);
                pst.setString(2, stream);
                pst.setInt(3, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Courses updated successfully");
                System.out.println("Do you want to update another course?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteACourse() {
        while (true) {
            try {
                DaoCourse ds = new DaoCourse();
                DaoCoursesUsers cu=new DaoCoursesUsers();
                DaoCoursesAssignments ca=new DaoCoursesAssignments();
                DaoSchedule s=new DaoSchedule();
                Scanner sc = new Scanner(System.in);
                System.out.println("All the existing courses are:");
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("Which course do you want to delete? Select by typing its id.");
                int id = sc.nextInt();
                cu.deleteACourseFromCoursesUsers(id);
                ca.deleteACourseFromAssignmentCourses(id);
                s.deleteACourseFromSchedule(id);
                String sql = "Delete from course where c_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Course deleted");
                System.out.println("This course has also been deleted from users and courses,from courses and assignments and from schedule");
                System.out.println("Do you want to delete another course?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
