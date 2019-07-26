/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author bizmi
 */
public class DButils {

    public static String hashingPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(10);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);
        

        return (hashed_password);
    }

    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if (null == stored_hash || !stored_hash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return (password_verified);

    }

//    database connection
    private static String USERNAME = "root";
    private static String PASS = "cheimyforever1";
    private static String MYSQLURL = "jdbc:mysql://localhost:3306/schoolsystem?zeroDateTimeBehavior=CONVERT_TO_NULL"
            + "&useUnicode=true"
            + "                &useJDBCCompliantTimezoneShift=false"
            + "                &useLegacyDatetimeCode=true"
            + "                &serverTimezone=UTC"
            + "                &allowPublicKeyRetrieval=true"
            + "                &useSSL=false";
    static Connection con = null;

    public static Connection getconnection() {

        try {
            con = DriverManager.getConnection(MYSQLURL, USERNAME, PASS);

        } catch (SQLException ex) {
            Logger.getLogger(DButils.class.getName()).log(Level.SEVERE, null, ex);

        }
        return con;

    }

    public static void closeconnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DButils.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
