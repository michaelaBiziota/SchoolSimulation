/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import utils.DButils;

/**
 *
 * @author bizmi
 */
public class DaoSchedule {

    public ArrayList<Date> getSchedulePerCourse(int courseid) {
        ArrayList<Date> shedulePerCourse = new ArrayList<>();
        try {
            String sql = "Select * from schedule where c_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, courseid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Date d = rs.getDate("schedule_date");
                shedulePerCourse.add(d);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoSchedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shedulePerCourse;
    }

    public java.sql.Date setDates(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        java.sql.Date sqld;
        try {
            d = sdf1.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(DaoSchedule.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, 1);
        Date d1 = c.getTime();
        sqld = new java.sql.Date(d1.getTime());
        return sqld;
    }

    public void createSchedulePerCourse() {
        while (true) {
            try {
                System.out.println("All the courses are:");
                DaoCourse ds = new DaoCourse();
                DaoSchedule s = new DaoSchedule();
                Scanner sc = new Scanner(System.in);
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("Select the course you want to schedule.Type the course id");
                int cid = sc.nextInt();
                System.out.println("Remember that this course is already sheduled in the following dates:");
                for (Date d : s.getSchedulePerCourse(cid)) {
                    System.out.println(d);
                }
                System.out.println("Write the date you want to schedule this course(yyyy-MM-dd)");
                String d = sc.next();
                //                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //                java.util.Date date = sdf1.parse(d);
                //                java.sql.Date sqld = new java.sql.Date(date.getTime());
                String sql = "Insert into schedule(schedule_date,c_id)VALUES(?,?)";
                java.sql.Date sqld = s.setDates(d);
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setDate(1, sqld);
                pst.setInt(2, cid);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Successful new entry data.");
                System.out.println("DO you want to schedule another course?");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateSchedulePerCourse() {
        while (true) {
            try {
                System.out.println("All the courses are:");
                DaoCourse ds = new DaoCourse();
                DaoSchedule s = new DaoSchedule();
                Scanner sc = new Scanner(System.in);
                for (Course c : ds.getAllCourses()) {
                    System.out.println(c);
                }
                System.out.println("For which course do you want to update the " + "data about its schedule? Type the course id");
                int cid = sc.nextInt();
                System.out.println("This course is scheduled for:");
                for (Date d : s.getSchedulePerCourse(cid)) {
                    System.out.println(d);
                }
                System.out.println("Which of the above dates do you want to change? (yyyy-MM-dd).");
                String d = sc.next();
                java.sql.Date sqld = s.setDates(d);
                System.out.println("Now write the new date.(yyyy-MM-dd)");
                String d1 = sc.next();
                java.sql.Date sqld1 = s.setDates(d1);
                String sql = "Update schedule Set schedule_date=? where schedule_date=? and c_id=?";
                PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                pst.setDate(1, sqld1);
                pst.setDate(2, sqld);
                pst.setInt(3, cid);
                pst.executeUpdate();
                pst.close();
                DButils.closeconnection();
                System.out.println("Successful data update");
                System.out.println("Do you want to update more data about the schedule in a course?Type yes or no.");
                if (sc.next().equals("no")) {
                    break;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteSchedulePerCourse() {
        while (true) {
            System.out.println("All the courses are:");
            DaoCourse ds = new DaoCourse();
            DaoSchedule s = new DaoSchedule();
            Scanner sc = new Scanner(System.in);
            for (Course c : ds.getAllCourses()) {
                System.out.println(c);
            }
            System.out.println("For which course do you want to delete a scheduled date?Type the course id");
            int cid = sc.nextInt();
            while (true) {
                try {
                    System.out.println("This course is scheduled for:");
                    for (Date d : s.getSchedulePerCourse(cid)) {
                        System.out.println(d);
                    }
                    System.out.println("Which of the above dates do you want to delete? (yyyy-MM-dd).");
                    String d = sc.next();
                    java.sql.Date sqld = s.setDates(d);
                    String sql = "Delete from schedule where c_id=? and schedule_date=?";
                    PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
                    pst.setInt(1, cid);
                    pst.setDate(2, sqld);
                    pst.executeUpdate();
                    pst.close();
                    DButils.closeconnection();
                    System.out.println("Deleted successfully");
                    System.out.println("Do you want to delete another scheduled date of this course?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DaoHeadMaster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Do you want to delete a scheduled date of another course?Type yes or no");
            if (sc.next().equals("no")) {
                break;
            }
        }
    }

    public ArrayList<Date> getDailyScedulePerCoursePerStudent(int stId) {
        ArrayList<Date> scheduledate = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        DaoCoursesUsers d = new DaoCoursesUsers();
        System.out.println("The courses that you are enrolled are:");
        try {
            for (Course c : d.getCoursesPerStudent(stId)) {
                System.out.println(c);
            }
            System.out.println("Select a course to see the daily schedule:");
            int cId = sc.nextInt();
            String sql = "Select schedule_date from schedule where c_id=?";
            PreparedStatement pst = DButils.getconnection().prepareStatement(sql);
            pst.setInt(1, cId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Date d1 = rs.getDate("schedule_date");
                scheduledate.add(d1);
            }
            pst.close();
            DButils.closeconnection();
        } catch (SQLException ex) {
            Logger.getLogger(DaoStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return scheduledate;
    }

    public void deleteACourseFromSchedule(int cid) {
        try {
            String sql = "Delete from schedule where c_id=?";
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
