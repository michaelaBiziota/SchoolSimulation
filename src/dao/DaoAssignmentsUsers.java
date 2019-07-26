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
public class DaoAssignmentsUsers {

    public ArrayList<Assignment> getAssignmentsPerStudent(int studentId) {
        ArrayList<Assignment> assignmentsPerStudent = new ArrayList<>();
        try {
            String sql = "SELECT a.a_id,a.title,a.submissiondate FROM (Users u INNER JOIN usersandassignments ua ON u.user_id=ua.user_id) " + "INNER JOIN assignments a on a.a_id=ua.a_id where u.user_id=? and u.user_type='student'";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int aId = rs.getInt("a_id");
                String title = rs.getString("title");
                Date submissiondate = rs.getDate("submissiondate");
                Assignment a = new Assignment(aId, title, submissiondate);
                assignmentsPerStudent.add(a);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assignmentsPerStudent;
    }

    public ArrayList<User> getStudentsPerSubmittedAssignment(int assignmentId) {
        ArrayList<User> studentsPerAssignment = new ArrayList<>();
        try {
            String sql = "SELECT u.user_id,firstname,lastname FROM (Users u INNER JOIN usersandassignments ua ON u.user_id=ua.user_id) " + "INNER JOIN assignments a on a.a_id=ua.a_id where a.a_id=? and ua.submitted is not null";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, assignmentId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int stuId = rs.getInt("user_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                User u = new User(stuId, firstname, lastname);
                studentsPerAssignment.add(u);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentsPerAssignment;
    }

    public ArrayList<Assignment> getAssignmentsPerStudentUnsubmitted(int studentsId) {
        ArrayList<Assignment> assignmentsPerStudent = new ArrayList<>();
        try {
            String sql = "SELECT a.a_id,a.title,submissiondate FROM (assignments a INNER JOIN usersandassignments ua ON a.a_id = ua.a_id) " + "INNER JOIN users u on u.user_id=ua.user_id where ua.user_id=? and ua.submitted is null";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, studentsId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("a_id");
                String title = rs.getString("title");
                Date submissionDate = rs.getDate("submissiondate");
                Assignment a1 = new Assignment(id, title, submissionDate);
                assignmentsPerStudent.add(a1);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assignmentsPerStudent;
    }

    public ArrayList<Assignment> getSubmittedAssignmentsPerStudent(int studentId, int courseid) {
        ArrayList<Assignment> assignPerStudent = new ArrayList<>();
        try {
            String sql = "SELECT a.a_id,title,submissiondate FROM ((Users u INNER JOIN usersandassignments ua ON u.user_id=ua.user_id) " + "INNER JOIN assignments a on a.a_id=ua.a_id) INNER JOIN coursesandassignments ca on ca.a_id=a.a_id where u.user_id=? and ca.c_id=? and ua.submitted is not null";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setInt(2, courseid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int aId = rs.getInt("a_id");
                String title = rs.getString("title");
                Date subdate = rs.getDate("submissiondate");
                Assignment u = new Assignment(aId, title, subdate);
                assignPerStudent.add(u);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assignPerStudent;
    }

    public void getAssignmentsPerStudentPerCourse() {
        DaoCourse d = new DaoCourse();
        DaoCoursesUsers du = new DaoCoursesUsers();
        DaoCoursesAssignments da = new DaoCoursesAssignments();
        DaoAssignmentsUsers au = new DaoAssignmentsUsers();
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("All the courses are:");
            for (Course c : d.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For which course do you want to see the assignments that are appointed to the enrolled students?Type the course id");
            int courseId = sc.nextInt();
            System.out.println("The students enrolled in this course are:");
            for (User u : du.getStudentsPerCourse(courseId)) {
                System.out.println(u);
            }
            System.out.println("For which student do you want to see the assignments that are assigned to?Type the student's id");
            int stId = sc.nextInt();
            System.out.println("The assignments that are assigned to this student are:");
            for (Assignment a : au.getAssignmentsPerStudent(stId)) {
                System.out.println(a);
            }
            System.out.println("Do you want to see the assignments of other students or of another course ?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }

    public void markAnAssignmentPerStudentPerCourse() {
        Scanner sc = new Scanner(System.in);
        DaoCourse h = new DaoCourse();
        DaoCoursesUsers du = new DaoCoursesUsers();
        DaoAssignmentsUsers da = new DaoAssignmentsUsers();
        while (true) {
            try {
                System.out.println("All the courses are:");
                for (Course c : h.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("which course's assignments do you want to mark?Type the course ID");
                int courseId = sc.nextInt();
                System.out.println("The students enrolled in this course are:");
                for (User u : du.getStudentsPerCourse(courseId)) {
                    System.out.println(u);
                }
                System.out.println("Which student's assignment do you want to mark? Type the students ID");
                int stid = sc.nextInt();
                System.out.println("This student has the following assignments for this course:");
                for (Assignment a : da.getAssignmentsPerStudentPerCourse(stid, courseId)) {
                    System.out.println(a);
                }
                System.out.println("This student has submitted the following assignments:");
                for (Assignment a : da.getSubmittedAssignmentsPerStudent(stid, courseId)) {
                    System.out.println(a);
                }
                System.out.println("Which assignment do you want to mark? Select by typing the assignment id.");
                int aId = sc.nextInt();
                System.out.println("write their mark:(max 100.00 upto 2 decimals)");
                float mark = sc.nextFloat();
                String sql = "Update usersandassignments Set mark=? where user_id=? and a_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setFloat(1, mark);
                pst.setInt(2, stid);
                pst.setInt(3, aId);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Mark Submitted successfully");
                System.out.println("Do you want to mark another assignment?");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoTrainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getTheSubdatesOfAssignmentPerStudentPerCourse(int stuId) {
        System.out.println("The courses you are enrolled are:");
        DaoCoursesUsers ds = new DaoCoursesUsers();
        DaoCoursesAssignments da = new DaoCoursesAssignments();
        Scanner sc = new Scanner(System.in);
        for (Course c : ds.getCoursesPerStudent(stuId)) {
            System.out.println(c);
        }
        System.out.println("For which course do you want to see the submission date of the appointed assignments?");
        int courseId = sc.nextInt();
        for (Assignment a : da.getAssignmentsPerCourse(courseId)) {
            System.out.println(a);
        }
    }

    public ArrayList<Assignment> getAssignmentsPerStudentPerCourse(int studentId, int courseId) {
        ArrayList<Assignment> assignmentsPerStudent = new ArrayList<>();
        try {
            String sql = "SELECT a.a_id,a.title,a.submissiondate FROM ((Users u INNER JOIN usersandassignments ua ON u.user_id=ua.user_id) " + "INNER JOIN assignments a on a.a_id=ua.a_id ) INNER JOIN coursesandassignments ca on ca.a_id=a.a_id where u.user_id=? and u.user_type='student' and ca.c_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int aId = rs.getInt("a_id");
                String title = rs.getString("title");
                Date submissiondate = rs.getDate("submissiondate");
                Assignment a = new Assignment(aId, title, submissiondate);
                assignmentsPerStudent.add(a);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return assignmentsPerStudent;
    }

    public void createAssignmentsPerUser(int courseId, int userId) {
        DaoCoursesAssignments ca = new DaoCoursesAssignments();
        for (Assignment a : ca.getAssignmentsPerCourse(courseId)) {
            try {
                String sql = "Insert ignore into usersandassignments(user_id,a_id)VALUES(?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, userId);
                pst.setInt(2, a.getId());
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();

            } catch (SQLException ex) {
                Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("The assignments appointed to this course have also been assigned to this student.");
    }

    public void createAssignmentsForAllUsers(int a_id, int courseId) {
        DaoCoursesUsers ca = new DaoCoursesUsers();
        for (User a : ca.getStudentsPerCourse(courseId)) {
            try {
                String sql = "Insert ignore into usersandassignments(user_id,a_id)VALUES(?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, a.getId());
                pst.setInt(2, a_id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();

            } catch (SQLException ex) {
                Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("The assignment appointed to this course have also been assigned to all the student in the course.");
    }
    public void deleteAnAssignmentForUsers(int aid){
        try {
            String sql="Delete from usersandassignments where a_id=?";
            PreparedStatement pst=DButils.getconnection().prepareStatement(sql);
            pst.setInt(1,aid);
            pst.executeUpdate();
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }


}
        public void deleteAStudentFromAssignmentsUsers(int stid){
        try {
            String sql="Delete from usersandassignments where user_id=?";
            PreparedStatement pst=DButils.getconnection().prepareStatement(sql);
            pst.setInt(1,stid);
            pst.executeUpdate();
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}


