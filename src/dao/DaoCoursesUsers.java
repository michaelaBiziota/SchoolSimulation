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
import model.User;
import utils.DButils;

/**
 *
 * @author bizmi
 */
public class DaoCoursesUsers {

    public ArrayList<Course> getCoursesPerStudent(int studentId) {
        ArrayList<Course> coursesPerStudent = new ArrayList<>();
        try {
            String sql = "Select c.c_id,title,stream FROM (Course c INNER JOIN userspercourse uc on uc.c_id=c.c_id)" + "INNER JOIN users u on u.user_id=uc.user_id where u.user_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("c_id");
                String title = rs.getString("title");
                String stream = rs.getString("stream");
                Course c = new Course(cid, title, stream);
                coursesPerStudent.add(c);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coursesPerStudent;
    }

    public ArrayList<User> getStudentsPerCourse(int courseId) {
        ArrayList<User> studentsPerCourse = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        try {
            String sql = "Select u.user_id,firstname,lastname from (users u INNER JOIN userspercourse uc on u.user_id=uc.user_id)" + "INNER JOIN course c on c.c_id=uc.c_id where c.c_id=? and u.user_type='student'";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, courseId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int sId = rs.getInt("user_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                User su = new User(sId, firstname, lastname);
                studentsPerCourse.add(su);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoCoursesUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studentsPerCourse;
    }

    public ArrayList<User> getTrainersPerCourse(int courseId) {
        ArrayList<User> trainersPerCourse = new ArrayList<>();
        try {
            String sql = "Select u.user_id,firstname,lastname from(Users u INNER JOIN userspercourse uc on u.user_id=uc.user_id) where uc.c_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, courseId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String fn = rs.getString("firstname");
                String ln = rs.getString("lastname");
                User u = new User(id, fn, ln);
                trainersPerCourse.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoCoursesUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trainersPerCourse;
    }

    public void deleteStudentsPerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoCoursesUsers du = new DaoCoursesUsers();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For which course do you want to delete a student? Type the course id");
            int cid = sc.nextInt();
            while (true) {
                try {
                    System.out.println("This course has the following students:");
                    for (User u : du.getStudentsPerCourse(cid)) {
                        System.out.println(u);
                    }
                    System.out.println("Which student do you want to delete from the course? Type the student id.");
                    int uid = sc.nextInt();
                    String sql = "Delete from userspercourse where user_id=? and c_id=?";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, uid);
                    pst.setInt(2, cid);
                    pst.executeUpdate();
                    pst.close();
                    DButils.closeconnection();
                    System.out.println("Deleted successfully");
                    System.out.println("Delete more students in this course?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Do you want to delete a student from another course?Type yes or no.");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }

    public void deleteTrainersPerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoCoursesUsers du = new DaoCoursesUsers();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For which course do you want to delete a trainer? Type the course id");
            int cid = sc.nextInt();
            while (true) {
                try {
                    System.out.println("This course has the following trainers:");
                    for (User u : du.getTrainersPerCourse(cid)) {
                        System.out.println(u);
                    }
                    System.out.println("Which trainer do you want to delete from the course? Type the trainer id.");
                    int uid = sc.nextInt();
                    String sql = "Delete from userspercourse where user_id=? and c_id=?";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, uid);
                    pst.setInt(2, cid);
                    pst.executeUpdate();
                    pst.close();
                    DButils.closeconnection();
                    System.out.println("Deleted successfully");
                    System.out.println("Delete more trainers in this course?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Do you want to delete a trainer from another course?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }

    public void enrollATrainerInACourse() {
        try {
            while (true) {
                System.out.println("The list of all the trainers is:");
                DaoTrainer d1 = new DaoTrainer();
                for (User u : d1.getAllTrainers()) {
                    System.out.println(u);
                }
                System.out.println("Type trainer id");
                Scanner sc = new Scanner(System.in);
                int userId = sc.nextInt();
                while (true) {
                    System.out.println("All the courses are:");
                    DaoCourse dv = new DaoCourse();
                    for (Course c : dv.getAllCourses()) {
                        System.out.println(c);
                    }
                    System.out.println("Which course do you want to appoint to this trainer? Type the course id.");
                    System.out.println("Remember that the following courses have been appointed to this trainer:");
                    for (Course u : getCoursesByTrainer(userId)) {
                        System.out.println(u);
                    }
                    int courseId = sc.nextInt();
                    String sql = "Insert into userspercourse(user_id,c_id)Values(?,?)";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, userId);
                    pst.setInt(2, courseId);
                    pst.executeUpdate();
                    System.out.println("successful new data entry ");
                    System.out.println("Do you want to appoint to this trainer more courses? Type yer or no");
                    String answer = sc.next();
                    if (answer.equals("no")) {
                        break;
                    }
                }
                System.out.println("Do you want to appoint a course to another trainer? type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enrollAStudentInACourse() {
        try {
            while (true) {
                DaoAssignmentsUsers au = new DaoAssignmentsUsers();
                System.out.println("The list of all the students is:");
                DaoStudent d1 = new DaoStudent();
                DaoCoursesUsers du = new DaoCoursesUsers();
                for (User u : d1.getAllStudents()) {
                    System.out.println(u);
                }
                System.out.println("Type student id");
                Scanner sc = new Scanner(System.in);
                int userId = sc.nextInt();
                while (true) {
                    DaoCourse dv = new DaoCourse();
                    System.out.println("All the courses are:");
                    for (Course c : dv.getAllCourses()) {
                        System.out.println(c);
                    }
                    System.out.println("Remember that this student is already enrolled in the following courses:");
                    for (Course c : du.getCoursesPerStudent(userId)) {
                        System.out.println(c);
                    }
                    System.out.println("In which course do you want to  enroll this student? Type the course id.");
                    int courseId = sc.nextInt();
                    String sql = "Insert into userspercourse(user_id,c_id)Values(?,?)";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, userId);
                    pst.setInt(2, courseId);
                    pst.executeUpdate();
                    System.out.println("Registered successfully");
                    au.createAssignmentsPerUser(courseId, userId);
                    System.out.println("Do you want to enroll this student to more courses? Type yer or no");
                    String answer = sc.next();
                    if (answer.equals("no")) {
                        break;
                    }
                }
                System.out.println("Do you want to enroll another student to a course? type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStudentsPerCourse() {
        while (true) {
            try {
                System.out.println("All the courses are:");
                DaoCourse ds = new DaoCourse();
                DaoStudent d = new DaoStudent();
                DaoCoursesUsers du = new DaoCoursesUsers();
                Scanner sc = new Scanner(System.in);
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("For which course do you want to update the " + "data about the students that are enrolled in? Type the course id");
                int cid = sc.nextInt();
                System.out.println("This course has the following students:");
                for (User u : du.getStudentsPerCourse(cid)) {
                    System.out.println(u);
                }
                System.out.println("For which student this course's data need to be changed? Type the student id.");
                int uid = sc.nextInt();
                System.out.println("Now select the student that should replace the one selected. Type the student id");
                System.out.println("Remember that all the students are:");
                for (User u : d.getAllStudents()) {
                    System.out.println(u);
                }
                int nuid = sc.nextInt();
                String sql = "Update userspercourse Set user_id=? where user_id=? and c_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, nuid);
                pst.setInt(2, uid);
                pst.setInt(3, cid);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Successful data update");
                System.out.println("Do you want to update more data about students enrolled in courses?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateTrainersPerCourse() {
        while (true) {
            try {
                System.out.println("All the courses are:");
                DaoCourse ds = new DaoCourse();
                DaoTrainer dt = new DaoTrainer();
                DaoCoursesUsers du = new DaoCoursesUsers();
                Scanner sc = new Scanner(System.in);
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("For which course do you want to update the " + "data about the trainers that teach it? Type the course id");
                int cid = sc.nextInt();
                System.out.println("This course has the following trainers:");
                for (User u : du.getTrainersPerCourse(cid)) {
                    System.out.println(u);
                }
                System.out.println("For which trainer this course's data need to be changed? Type the trainer id.");
                int uid = sc.nextInt();
                System.out.println("Now select the trainer that should replace the one selected. Type the trainer id");
                System.out.println("Remember that all the trainers are:");
                for (User u : dt.getAllTrainers()) {
                    System.out.println(u);
                }
                int nuid = sc.nextInt();
                String sql = "Update userspercourse Set user_id=? where user_id=? and c_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, nuid);
                pst.setInt(2, uid);
                pst.setInt(3, cid);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Successful data update");
                System.out.println("Do you want to update more data about the trainers and their appointed courses?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getStudentsPerCourse() {
        DaoCourse d = new DaoCourse();
        DaoCoursesUsers du = new DaoCoursesUsers();
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("All the courses are:");
            for (Course c : d.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For Which course do you want to see the students that are enrolled in?type the course id");
            int courseId = sc.nextInt();
            System.out.println("The students enrolled in this course are:");
            for (User u : du.getStudentsPerCourse(courseId)) {
                System.out.println(u);
            }
            System.out.println("Do you want to see the students enrolled in another course?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }

    public ArrayList<Course> getCoursesByTrainer(int trainerid) {
        ArrayList<Course> coursesPerTrainer = new ArrayList<>();
        try {
            String sql = "Select c.c_id,title,stream from (Course c INNER JOIN userspercourse uc on c.c_id=uc.c_id)" + "INNER JOIN users u on u.user_id=uc.user_id where u.user_id=? and u.user_type='trainer'";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, trainerid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("c_id");
                String title = rs.getString("title");
                String stream = rs.getString("stream");
                Course c = new Course(id, title, stream);
                coursesPerTrainer.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coursesPerTrainer;
    }

    public void enrollInACourse(int studentId) {
        DaoCoursesUsers du = new DaoCoursesUsers();
        DaoAssignmentsUsers au = new DaoAssignmentsUsers();
        try {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("All the courses are:");
                DaoCourse dv = new DaoCourse();
                for (Course c : dv.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("In which course do you want to  enroll? Type the course id.");
                System.out.println("Remember that you are already enrolled in the following courses:");
                for (Course c : du.getCoursesPerStudent(studentId)) {
                    System.out.println(c);
                }
                int courseId = sc.nextInt();
                String sql = "Insert into userspercourse(user_id,c_id)Values(?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, studentId);
                pst.setInt(2, courseId);
                pst.executeUpdate();
                System.out.println("Successful enrollement");
                au.createAssignmentsPerUser(courseId, studentId);
                System.out.println("Do you want to enroll to more courses? Type yer or no");
                String answer = sc.next();
                if (answer.equals("no")) {
                    break;
                }
                DButils.closeconnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAuserFromCoursesUsers(int userid) {
        try {
            String sql = "Delete from userspercourse where user_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, userid);
            pst.executeUpdate();
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteACourseFromCoursesUsers(int cid) {
        try {
            String sql = "Delete from userspercourse where c_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, cid);
            pst.executeUpdate();
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoAssignmentsUsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
