/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      MailSession.java                                                *
 * Created:   01/11/2025, 16:30                                               *
 * Modified:  01/11/2025, 16:30                                               *
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

package com.aerosimo.ominet.core.config;

import jakarta.mail.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

public class MailSession {

    private static final Logger log = LogManager.getLogger(MailSession.class);

    /**
     * Returns a JavaMail Session from JNDI ("mail/aerosimo").
     * Each call does a fresh lookup to avoid stale references.
     *
     * @return Jakarta Mail Session or null if lookup fails
     */
    public static Session email() {
        log.info("Preparing to get email session from JNDI");
        try {
            Context ctx = new InitialContext();
            try {
                Context env = (Context) ctx.lookup("java:/comp/env");
                Session sess = (Session) env.lookup("mail/aerosimo");
                log.debug("Successfully retrieved mail session via JNDI");
                return sess;
            } finally {
                ctx.close(); // prevent context leak
            }
        } catch (Exception err) {
            log.error("Email session lookup failed in {}", Connect.class.getName(), err);
            return null;
        }
    }
}