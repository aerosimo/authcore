/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthREST.java                                                   *
 * Created:   07/11/2025, 23:19                                               *
 * Modified:  09/11/2025, 19:30                                               *
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

package com.aerosimo.ominet.api;

import com.aerosimo.ominet.dao.impl.*;
import com.aerosimo.ominet.dao.mapper.AuthDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/auth")
public class AuthREST {

    private static final Logger log = LogManager.getLogger(AuthREST.class.getName());

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequestDTO req) {
        log.info("Received registration request for {}", req.email);
        APIResponseDTO result = AuthDAO.registerUser(req.username, req.email, req.password);
        String status = result.getStatus();
        String message = result.getMessage();
        switch (status.toUpperCase()) {
            case "SUCCESS":
                return Response.ok(new APIResponseDTO("success", message)).build();
            case "EMAIL_EXISTS":
                return Response.status(Response.Status.CONFLICT)
                        .entity(new APIResponseDTO("unsuccessful", "email already registered"))
                        .build();
            case "PASSWORD_ERROR":
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new APIResponseDTO("unsuccessful", "invalid password"))
                        .build();
            default:
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new APIResponseDTO("unsuccessful", message))
                        .build();
        }
    }

    @POST
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verify(VerifyRequestDTO req) {
        log.info("Received verification request for {}", req.token);
        String result = AuthDAO.verifyEmail(req.token);
        if (result.equalsIgnoreCase("success")) {
            return Response.ok(new APIResponseDTO("success", "")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", ""))
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO req) {
        log.info("Preparing to login user...");
        APIResponseDTO result = AuthDAO.userLogin(req.username, req.password);
        String status = result.getStatus();
        String message = result.getMessage();
        if (status.equalsIgnoreCase("success")) {
            return Response.ok(new APIResponseDTO(status, message)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", ""))
                    .build();
        }
    }

    @POST
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validate(ValidateRequestDTO req) {
        log.info("Preparing to validate authentication key...");
        String result = AuthDAO.validateAuthKey(req.authKey);
        if (result.equalsIgnoreCase("valid")) {
            return Response.ok(new APIResponseDTO(result, "")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("invalid", ""))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(LogoutRequestDTO req) {
        log.info("Preparing to logout user...");
        String result = AuthDAO.userLogout(req.authKey);
        log.info("User is now logged out with the following {}", result);
        if (result.equalsIgnoreCase("success")) {
            return Response.ok(new APIResponseDTO(result, "")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", ""))
                    .build();
        }
    }
}