/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthCoreREST.java                                                   *
 * Created:   14/11/2025, 01:41                                               *
 * Modified:  27/11/2025, 15:51                                               *
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
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/auth")
public class AuthCoreREST {

    private static final Logger log = LogManager.getLogger(AuthCoreREST.class);

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequestDTO req) {
        log.info("Registering user: {}", req.getEmail());
        APIResponseDTO result = AuthDAO.registerUser(req.getUsername(), req.getEmail(), req.getPassword());
        return Response.ok(result).build();
    }

    @POST
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verify(VerifyRequestDTO req) {
        log.info("Verifying token: {}", req.getToken());
        String result = AuthDAO.verifyEmail(req.getToken());
        if ("success".equalsIgnoreCase(result)) {
            return Response.ok(new APIResponseDTO("success", "email verified successfully")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", "invalid or expired token"))
                    .build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO req) {
        log.info("Login attempt for user: {}", req.getUsername());
        APIResponseDTO result = AuthDAO.userLogin(req.getUsername(), req.getPassword());
        if ("success".equalsIgnoreCase(result.getStatus())) {
            // AuthDAO.userLogin returns authKey in message
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new APIResponseDTO("unsuccessful", "invalid credentials"))
                    .build();
        }
    }

    @POST
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validate(ValidateRequestDTO req) {
        log.info("Validating authKey: {}", req.getAuthKey());
        String result = AuthDAO.validateAuthKey(req.getAuthKey());
        if ("valid".equalsIgnoreCase(result)) {
            return Response.ok(new APIResponseDTO("success", "token is valid")).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new APIResponseDTO("unsuccessful", "invalid or expired token"))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(LogoutRequestDTO req) {
        log.info("Logging out token: {}", req.getAuthKey());
        String result = AuthDAO.userLogout(req.getAuthKey());
        if ("success".equalsIgnoreCase(result)) {
            return Response.ok(new APIResponseDTO("success", "logged out successfully")).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", "logout failed"))
                    .build();
        }
    }

    @POST
    @Path("/forgot")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response forgot(ForgotRequestDTO req) {
        log.info("Frgoot Password for account with email: {}", req.getEmail());
        ForgotResponseDTO result = AuthDAO.forgotPassword(req.getEmail());
        if ("valid".equalsIgnoreCase(result.getStatus())) {
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new APIResponseDTO("unsuccessful", "invalid email"))
                    .build();
        }
    }

    @POST
    @Path("/reset")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reset(ResetRequestDTO req) {
        log.info("Password reset with this verification: {}", req.getVerificationToken());
        String result = AuthDAO.resetPassword(req.getVerificationToken(),req.getPassword());
        if ("success".equalsIgnoreCase(result)) {
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new APIResponseDTO("unsuccessful", "invalid credentials"))
                    .build();
        }
    }
}