/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthCoreFilter.java                                                 *
 * Created:   14/11/2025, 01:35                                               *
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
public class AuthCoreFilter implements ContainerRequestFilter {

    private static final Logger log = LogManager.getLogger(AuthCoreFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();

        // Allow public paths
        if (path.startsWith("public") || path.equals("health")) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(requestContext, "Missing or invalid Authorization header", Response.Status.UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        if (token.isEmpty()) {
            abort(requestContext, "Authorization token is empty", Response.Status.UNAUTHORIZED);
            return;
        }

        // Validate token
        AuthStatus status;
        try {
            status = AuthStatus.valueOf(AuthDAO.validateAuthKey(token));
        } catch (Exception e) {
            log.error("Error validating token", e);
            abort(requestContext, "Authentication service error", Response.Status.INTERNAL_SERVER_ERROR);
            return;
        }

        if (status != AuthStatus.VALID) {
            log.warn("Token invalid: {}...", token.substring(0, Math.min(6, token.length())));
            abort(requestContext, "Invalid or expired token", Response.Status.UNAUTHORIZED);
            return;
        }

        // Token is valid
        log.info("Token validated successfully: {}...", token.substring(0, Math.min(6, token.length())));
    }

    private void abort(ContainerRequestContext ctx, String msg, Response.Status status) {
        ctx.abortWith(
                Response.status(status)
                        .entity(new APIResponseDTO("unsuccessful", msg))
                        .type("application/json")
                        .build()
        );
    }
}