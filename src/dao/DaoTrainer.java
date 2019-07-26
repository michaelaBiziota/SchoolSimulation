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
public class DaoTrainer {

    static List<String> passlist = new ArrayList<>();

    private String password = "trainerabc";
    String hashedPassword = DButils.hashingPassword(password);

    public void trainerLogin() {

        Scanner sc = new Scanner(System.in);
        System.out.println("give your password");
        while (true) {
            String passwordlogin = sc.next();
            DaoTrainer da = new DaoTrainer();
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
        m.menuTrainer();

    }

    public boolean insertPassword() {
        String sqlPrepInsert = "Update users Set password=? Where user_type=?";

        try {
            PreparedStatement stm = DButils.getconnection().prepareStatement(sqlPrepInsert);
            stm.setString(1, hashedPassword);
            stm.setString(2, "trainer");
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }

        DButils.closeconnection();
        return true;

    }

    public List<String> getPasswords() {

        String select = "Select password from users where user_type=?";
        try {
            PreparedStatement k = DButils.getconnection().prepareStatement(select);
            k.setString(1, "trainer");
            ResultSet sd = k.executeQuery();
            while (sd.next()) {
                String a = sd.getString("password");
                passlist.add(a);

            }
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return passlist;

    }

    public ArrayList<User> getAllTrainers() {
        ArrayList<User> allTrainers = new ArrayList<>();
        try {
            String sql = "Select * from users where user_type=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setString(1, "trainer");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                User u1 = new User(id, firstname, lastname);
                allTrainers.add(u1);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoTrainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allTrainers;
    }

    public void changeTrainerPassword(String newPassword) {
        password = newPassword;
        hashedPassword = DButils.hashingPassword(password);

    }

    public void deleteATrainer() {
        DaoCoursesUsers cu = new DaoCoursesUsers();
        while (true) {
            try {
                DaoTrainer ds = new DaoTrainer();
                Scanner sc = new Scanner(System.in);
                System.out.println("All the trainers are:");
                for (User c : ds.getAllTrainers()) {
                    System.out.println(c);
                }
                System.out.println("Which trainer do you want to delete? Select by typing their id.");
                int id = sc.nextInt();
                cu.deleteAuserFromCoursesUsers(id);
                String sql = "Delete from users where user_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Trainer deleted successfully.");
                System.out.println("This trainer has also been deleted from the trainers and courses list.");
                System.out.println("Do you want to delete another trainer?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createATrainer() {
        while (true) {
            try {
                Scanner sc1 = new Scanner(System.in);
                System.out.println("Let's register the new trainer in the school system. Type his firstname");
                String fname = sc1.nextLine();
                System.out.println("Now type his lastname.");
                String lname = sc1.nextLine();
                String sql = "INSERT INTO users(firstname,lastname,user_type)VALUES(?,?,?)";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setString(1, fname);
                pst.setString(2, lname);
                pst.setString(3, "trainer");
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                DaoTrainer dm = new DaoTrainer();
                dm.insertPassword();
                System.out.println("Successful entry");
                System.out.println("Do you want to register another trainer in the school system?Type yes or no.");
                if (sc1.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateATrainer() {
        DaoTrainer dst = new DaoTrainer();
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                Scanner sc1 = new Scanner(System.in);
                System.out.println("All the  trainers are:");
                for (User c : dst.getAllTrainers()) {
                    System.out.println(c);
                }
                System.out.println("Which trainer's data do you want to update? Select by typing their id.");
                int id = sc.nextInt();
                System.out.println("Update the trainer's data by typing the altered firstname");
                String fname = sc1.nextLine();
                System.out.println("Type the altered lastname");
                String lname = sc1.nextLine();
                System.out.println("Do you want to change the password? " + "Remember that all the trainers have the same password so a new password will be given to all.Type yes or no");
                if (sc.next().equals("yes")) {
                    System.out.println("Enter the new password.");
                    dst.changeTrainerPassword(sc.next());
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
                System.out.println("Trainer's data updated successfully");
                System.out.println("Do you want to update another trainer's data?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
