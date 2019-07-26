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
public class DaoStudent {

    static List<String> passlist = new ArrayList<>();

    private String password = "studentabc";
    String hashedPassword = DButils.hashingPassword(password);
    
    public void studentLogin() {

        Scanner sc = new Scanner(System.in);
        System.out.println("give your password");
        while (true) {
            String passwordlogin = sc.next();
            DaoStudent da = new DaoStudent();
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
                System.out.println("Incorrect password. Try again");
            }

        }
        Menu m = new Menu();
        m.menuStudents();

    }

    public boolean insertPassword() {
        String sqlPrepInsert = "Update users Set password=? Where user_type=?";
        try {
            PreparedStatement stm = DButils.getconnection().prepareStatement(sqlPrepInsert);
            stm.setString(1, hashedPassword);
            stm.setString(2, "student");
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }

        DButils.closeconnection();
        return true;

    }

    public List<String> getPasswords() {

        String select = "Select password from users where user_type=?";
        try {
            PreparedStatement k = DButils.getconnection().prepareStatement(select);
            k.setString(1, "student");
            ResultSet sd = k.executeQuery();
            while (sd.next()) {
                String a = sd.getString("password");
                passlist.add(a);

            }
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }

        return passlist;

    }



    public ArrayList<User> getAllStudents() {
        ArrayList<User> allStudents = new ArrayList<>();
        try {
            String sql = "Select * from users where user_type=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setString(1, "student");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                User u1 = new User(id, firstname, lastname);
                allStudents.add(u1);
            }
            pst.close();
            DButils.closeconnection();

        } catch (SQLException ex) { 
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allStudents;
    }


    public void changeStudentPassword(String newPassword){
    password=newPassword;
    hashedPassword=DButils.hashingPassword(password);
    
    }

    public void createAStudent() {
        while (true) {
            try {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("Let's register the new student in the school system. Type his firstname");
                String fname = sc1.nextLine();
                System.out.println("Now type his lastname.");
                String lname = sc1.nextLine();
                String sql = "INSERT INTO users(firstname,lastname,user_type)VALUES(?,?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, fname);
                pst.setString(2, lname);
                pst.setString(3, "student");
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                DaoStudent dm = new DaoStudent();
                dm.insertPassword();
                System.out.println("Successful entry");
                System.out.println("Do you want to register another student in the school system?Type yes or no.");
                if (sc1.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateAStudent() {
        DaoStudent dst = new DaoStudent();
        while (true) {
            try {
                DaoStudent ds = new DaoStudent();
                Scanner sc = new Scanner(System.in);
                Scanner sc1 = new Scanner(System.in);
                System.out.println("All the  students are:");
                for (User c : ds.getAllStudents()) {
                    System.out.println(c);
                }
                System.out.println("Which student's data do you want to update? Select by typing their id.");
                int id = sc.nextInt();
                System.out.println("Update the student's data by typing the altered firstname");
                String fname = sc1.nextLine();
                System.out.println("Type the altered lastname");
                String lname = sc1.nextLine();
                System.out.println("Do you want to change the password? " + "Remember that all the students have the same password so a new password will be given to all.Type yes or no");
                if (sc.next().equals("yes")) {
                    System.out.println("Enter the new password.");
                    dst.changeStudentPassword(sc.next());
                }
                String sql = "Update users SET firstname=?, lastname=? where user_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, fname);
                pst.setString(2, lname);
                pst.setInt(3, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                dst.insertPassword();
                System.out.println("Student's data updated successfully");
                System.out.println("Do you want to update another student's data?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteAStudent() {
        while (true) {
            try {
                DaoStudent ds = new DaoStudent();
                DaoAssignmentsUsers au=new DaoAssignmentsUsers();
                DaoCoursesUsers cu=new DaoCoursesUsers();
                Scanner sc = new Scanner(System.in);
                System.out.println("All the students are:");
                for (User c : ds.getAllStudents()) {
                    System.out.println(c);
                }
                System.out.println("Which student do you want to delete? Select by typing their id.");
                int id = sc.nextInt();
                au.deleteAStudentFromAssignmentsUsers(id);
                cu.deleteAuserFromCoursesUsers(id);
                String sql = "Delete from users where user_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Student deleted");
                System.out.println("This student has also been deleted from the assignments and students and courses and students");
                System.out.println("Do you want to delete another student?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
