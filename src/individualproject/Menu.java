/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package individualproject;

import dao.DaoAssignment;
import dao.DaoAssignmentsUsers;
import dao.DaoCourse;
import dao.DaoCoursesAssignments;
import dao.DaoCoursesUsers;
import dao.DaoHeadMaster;
import dao.DaoSchedule;
import dao.DaoStudent;
import dao.DaoTrainer;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import model.Course;
import model.User;

/**
 *
 * @author bizmi
 */
public class Menu {

    public static void login() {
        String[] roles = {"student", "trainer", "headMaster"};
        System.out.println("Welcome to the school app.");
        System.out.println("choose your role");
        for (String role : roles) {
            System.out.println(role);
        }
        Scanner sc = new Scanner(System.in);
        String userRole = sc.next();
        while (!Arrays.asList(roles).contains(userRole)) {
            System.out.println("Please type again your role");
            userRole = sc.next();
        }
        if (userRole.equals("student")) {
            DaoStudent d = new DaoStudent();
            d.studentLogin();
        }
        if (userRole.equals("trainer")) {
            DaoTrainer d = new DaoTrainer();
            d.trainerLogin();
        }
        if (userRole.equals("headMaster")) {
            DaoHeadMaster d = new DaoHeadMaster();
            d.headMasterLogin();
        }
    }

    public void menuStudents() {
        Scanner sc = new Scanner(System.in);
        DaoStudent df = new DaoStudent();
        DaoCoursesUsers su = new DaoCoursesUsers();
        DaoAssignment da = new DaoAssignment();
        DaoSchedule s = new DaoSchedule();
        DaoAssignmentsUsers au = new DaoAssignmentsUsers();
        System.out.println("What is your id? If you dont remember your ID you can check it from the list below:");
        for (User u : df.getAllStudents()) {
            System.out.println(u);
        }
        int stId = sc.nextInt();
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("if you want to enroll in a course press 1");
            System.out.println("if you want to submit an assignment press 2");
            System.out.println("if you want to see your daily schedule per course press 3");
            System.out.println("if you want to see the submission date of the assignments per course press 4");
            int answer = sc.nextInt();
            if (answer == 1) {
                su.enrollInACourse(stId);
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            } else if (answer == 2) {
                da.submitAnAssignment(stId);
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            } else if (answer == 3) {
                while (true) {
                    for (Date d : s.getDailyScedulePerCoursePerStudent(stId)) {
                        System.out.println(d);
                    }
                    System.out.println("Do you want to see the schedule for another course?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                }
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            } else if (answer == 4) {
                while (true) {
                    au.getTheSubdatesOfAssignmentPerStudentPerCourse(stId);
                    System.out.println("Do you want to see the submission dates for another assignment?Type yes or no");
                    if (sc.next().equals("no")) {
                        break;
                    }
                }
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            }
        }
    }

    public void menuTrainer() {
        Scanner sc = new Scanner(System.in);
        DaoTrainer df = new DaoTrainer();
        DaoCoursesUsers cu = new DaoCoursesUsers();
        DaoAssignmentsUsers au = new DaoAssignmentsUsers();
        System.out.println("What is your id? If you dont remember your ID you can check it from the list below:");
        for (User u : df.getAllTrainers()) {
            System.out.println(u);
        }
        int trId = sc.nextInt();
        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("If you want to view the courses that have been appointed to you press 1");
            System.out.println("If you want to view all the students per course press 2");
            System.out.println("If you want to view all the assignments per student per course press 3");
            System.out.println("If you want to mark an assignment press 4");
            int answer = sc.nextInt();
            if (answer == 2) {
                cu.getStudentsPerCourse();
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            } else if (answer == 3) {
                au.getAssignmentsPerStudentPerCourse();
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            } else if (answer == 4) {
                au.markAnAssignmentPerStudentPerCourse();
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            } else if (answer == 1) {
                System.out.println("The courses you are enrolled in are:");
                for (Course c : cu.getCoursesByTrainer(trId)) {
                    System.out.println(c);
                }
                System.out.println("continue your navigation?Type yes or no");
                if (sc.next().equals("no")) {
                    break;
                }
            }
        }
    }

    public void menuHeadMaster() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            DaoCourse dc = new DaoCourse();
            DaoStudent ds = new DaoStudent();
            DaoTrainer dt = new DaoTrainer();
            DaoAssignment da = new DaoAssignment();
            DaoCoursesUsers cu = new DaoCoursesUsers();
            DaoHeadMaster hm = new DaoHeadMaster();
            DaoCoursesAssignments ca = new DaoCoursesAssignments();
            DaoSchedule s=new DaoSchedule();
            System.out.println("What do you want to do?");
            System.out.println("CRUD on courses? press 1");
            System.out.println("CRUD on students? press 2");
            System.out.println("CRUD on trainers? press 3");
            System.out.println("CRUD on assignments? press 4");
            System.out.println("CRUD on students per course? press 5");
            System.out.println("CRUD on trainers per course? press 6");
            System.out.println("CRUD on assignments per course? press 7");
            System.out.println("CRUD on schedule per course? press 8");
            System.out.println("Log out? press 9");

            int answer = sc.nextInt();
            if (answer == 1) {
                System.out.println("If you want to create a course press 1.");
                System.out.println("If you want to view all the courses press 2.");
                System.out.println("If you want to update course data press 3.");
                System.out.println("If you want to delete a course  press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    dc.createACourse();
                } else if (a == 2) {
                    hm.headMasterGetAllCourses();
                } else if (a == 3) {
                    dc.updateACourse();
                } else if (a == 4) {
                    dc.deleteACourse();
                }
            } else if (answer == 2) {
                System.out.println("If you want to register a new student to the school system press 1.");
                System.out.println("If you want to view all the students press 2.");
                System.out.println("If you want to update a student's data press 3.");
                System.out.println("If you want to delete a student press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    ds.createAStudent();
                } else if (a == 2) {
                    hm.headMasterGetAllStudents();
                } else if (a == 3) {
                    ds.updateAStudent();
                } else if (a == 4) {
                    ds.deleteAStudent();
                }
            } else if (answer == 3) {
                System.out.println("If you want to register a new trainer to the school system press 1.");
                System.out.println("If you want to view all the trainers press 2.");
                System.out.println("If you want to update a trainer's data press 3.");
                System.out.println("If you want to delete a trainer press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    dt.createATrainer();
                } else if (a == 2) {
                    hm.headMasterGetAllSTrainers();
                } else if (a == 3) {
                    dt.updateATrainer();
                } else if (a == 4) {
                    dt.deleteATrainer();
                }
            } else if (answer == 4) {
                System.out.println("If you want to create a new assignment press 1.");
                System.out.println("If you want to view all the assignments press 2.");
                System.out.println("If you want to update assignment data press 3.");
                System.out.println("If you want to delete an assignment press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    da.createAnAssignment();
                } else if (a == 2) {
                    hm.headMasterGetAllSAssignments();
                } else if (a == 3) {
                    da.updateAnAssignment();
                } else if (a == 4) {
                    da.deleteAnAssignmet();
                }
            } else if (answer == 5) {
                System.out.println("If you want to enroll a student to a course press 1.");
                System.out.println("If you want to view all students enrolled in a course press 2.");
                System.out.println("If you want to update the data on a student enrolled in a course press 3.");
                System.out.println("If you want to drop a student from a course press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    cu.enrollAStudentInACourse();
                } else if (a == 2) {
                    cu.getStudentsPerCourse();
                } else if (a == 3) {
                    cu.updateStudentsPerCourse();
                } else if (a == 4) {
                    cu.deleteStudentsPerCourse();
                }
            } else if (answer == 6) {
                System.out.println("If you want to appoint a course to a trainer press 1.");
                System.out.println("If you want to view all the trainers who teach a course press 2.");
                System.out.println("If you want to update the data on a trainer and the appointed course press 3.");
                System.out.println("If you want to drop a trainer from a course press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    cu.enrollATrainerInACourse();
                } else if (a == 2) {
                    hm.headmasterGetTrainersPerCourse();
                } else if (a == 3) {
                    cu.updateTrainersPerCourse();
                } else if (a == 4) {
                    cu.deleteTrainersPerCourse();
                }
            } else if (answer == 7) {
                System.out.println("If you want to appoint an assignment to a course press 1.");
                System.out.println("If you want to view all the assignments per course press 2.");
                System.out.println("If you want to update the data on a course and the appointed assignment press 3.");
                System.out.println("If you want to delete an assignment from a course press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    ca.createAnAssignemtPerCourse();
                } else if (a == 2) {
                    hm.headmasterGetAssignmentPerCourse();
                } else if (a == 3) {
                    ca.updateAssignmentsPerCourse();
                } else if (a == 4) {
                    ca.deleteAssignmentPerCourse();
                }
            } else if (answer == 8) {
                System.out.println("If you want to schedule a course press 1.");
                System.out.println("If you want to view the schedule per course press 2.");
                System.out.println("If you want to update the data about a course and the scheduled dates press 3.");
                System.out.println("If you want to delete a scheduled date from a course press 4.");
                int a = sc.nextInt();
                if (a == 1) {
                    s.createSchedulePerCourse();
                } else if (a == 2) {
                    hm.headMasterGetSchedulePerCourse();
                } else if (a == 3) {
                    s.updateSchedulePerCourse();
                } else if (a == 4) {
                    s.deleteSchedulePerCourse();
                }

            }
            else{break;}

        }

    }
}
