/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthDAO.java                                                    *
 * Created:   01/11/2025, 02:00                                               *
 * Modified:  01/11/2025, 02:00                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.dao.mapper;

import com.aerosimo.ominet.core.config.Connect;
import com.aerosimo.ominet.core.model.Spectre;
import com.aerosimo.ominet.dao.impl.APIResponseDTO;
import com.aerosimo.ominet.mail.AuthenticationEmail;
import com.aerosimo.ominet.mail.WelcomeEmail;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthDAO {

    private static final Logger log = LogManager.getLogger(AuthDAO.class.getName());

    public static APIResponseDTO registerUser(String username, String email, String password) {
        log.info("Preparing to create user account");
        String result;
        String response;
        String sql = "{call authentication_pkg.registerUser(?,?,?,?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.execute();
            result = stmt.getString(4);
            response = stmt.getString(5);
            log.info("Successfully create user account with following response: {}", response);
            if(response.equalsIgnoreCase("SUCCESS")){
                WelcomeEmail.sendMail(username, email, result);
                return new APIResponseDTO(response,result);
            } else {
                return new APIResponseDTO("unsuccessful", response);
            }
        } catch (SQLException err) {
            log.error("Error in authentication_pkg (CREATE ACCOUNT)", err);
            try {
                Spectre.recordError("TE-20001", "Error in authentication_pkg (CREATE ACCOUNT): " + err.getMessage(), AuthDAO.class.getName());
                return new APIResponseDTO("error", "internal server error");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String verifyEmail(String verificationToken) {
        log.info("Preparing to verify new user email");
        String response;
        String sql = "{call authentication_pkg.verifyEmail(?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, verificationToken);
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(2);
            log.info("Successfully verified email");
        } catch (SQLException err) {
            log.error("Error in authentication_pkg (VERIFY EMAIL)", err);
            try {
                Spectre.recordError("TE-20001", "Error in authentication_pkg (VERIFY EMAIL): " + err.getMessage(), AuthDAO.class.getName());
                response = "internal server error";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    public static APIResponseDTO userLogin(String username, String password) {
        log.info("Preparing to login user");
        String email;
        String authKey;
        String response;
        String sql = "{call authentication_pkg.userLogin(?,?,?,?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.execute();
            email = stmt.getString(3);
            authKey = stmt.getString(4);
            response = stmt.getString(5);
            log.info("Successfully login user with following response: {}", response);
            if(response.equalsIgnoreCase("SUCCESS")){
                AuthenticationEmail.sendMail(username,email,authKey);
                return new APIResponseDTO(response,authKey);
            } else {
                return new APIResponseDTO("unsuccessful", "");
            }
        } catch (SQLException err) {
            log.error("Error in authentication_pkg (LOGIN)", err);
            try {
                Spectre.recordError("TE-20001", "Error in authentication_pkg (LOGIN): " + err.getMessage(), AuthDAO.class.getName());
                return new APIResponseDTO("error", "internal server error");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String validateAuthKey(String authKey) {
        log.info("Preparing to validate authentication key");
        String response;
        String sql = "{call authentication_pkg.validateAuthKey(?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, authKey);
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(2);
            log.info("Successfully validate authentication key with following response: {}", response);
        } catch (SQLException err) {
            log.error("Error in authentication_pkg (VALIDATE AUTHKEY)", err);
            try {
                Spectre.recordError("TE-20001", "Error in authentication_pkg (VALIDATE AUTHKEY): " + err.getMessage(), AuthDAO.class.getName());
                response = "internal server error";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    public static String userLogout(String authKey) {
        log.info("Preparing to logout user");
        String response;
        String sql = "{call authentication_pkg.userLogout(?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, authKey);
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(2);
            log.info("Successfully logged user out with following response: {}", response);
        } catch (SQLException err) {
            log.error("Error in authentication_pkg (LOGOUT)", err);
            try {
                Spectre.recordError("TE-20001", "Error in authentication_pkg (LOGOUT): " + err.getMessage(), AuthDAO.class.getName());
                response = "internal server error";
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }
}