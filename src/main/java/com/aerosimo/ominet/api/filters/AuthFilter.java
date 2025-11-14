/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthFilter.java                                                 *
 * Created:   14/11/2025, 01:26                                               *
 * Modified:  14/11/2025, 01:26                                               *
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

package com.aerosimo.ominet.api.filters;

import com.aerosimo.ominet.api.Secured;
import com.aerosimo.ominet.dao.impl.APIResponseDTO;
import com.aerosimo.ominet.dao.mapper.AuthDAO;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    private static final Logger log = LogManager.getLogger(AuthFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
        log.info("AuthFilter checking Authorization header: {}", authHeader);

        // Check header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing or invalid Authorization header");
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new APIResponseDTO("unsuccessful", "Missing or invalid Authorization header"))
                            .build()
            );
            return;
        }

        // Extract token
        String token = authHeader.substring("Bearer ".length()).trim();

        // Validate token via AuthDAO
        String validationResult;
        try {
            validationResult = AuthDAO.validateAuthKey(token);
        } catch (Exception e) {
            log.error("Auth DB error during token validation", e);
            requestContext.abortWith(
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new APIResponseDTO("unsuccessful", "Authentication service error"))
                            .build()
            );
            return;
        }

        // Token invalid, revoked, or expired
        if (!"valid".equalsIgnoreCase(validationResult)) {
            log.warn("Invalid or revoked token: {}", token);
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .entity(new APIResponseDTO("unsuccessful", "Invalid or expired token"))
                            .build()
            );
            return;
        }

        // Token valid — allow request to proceed
        log.info("Token validated successfully: {}", token);
    }
}