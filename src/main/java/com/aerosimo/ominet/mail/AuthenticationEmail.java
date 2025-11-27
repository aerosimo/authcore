/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthenticationEmail.java                                        *
 * Created:   03/11/2025, 02:16                                               *
 * Modified:  03/11/2025, 02:16                                               *
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

package com.aerosimo.ominet.mail;

import com.aerosimo.ominet.core.model.Herald;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationEmail {

    private static final Logger log = LogManager.getLogger(AuthenticationEmail.class.getName());

    public static String sendMail(String username, String email, String authkey) {
        log.info("Sending email to {} with token {}", email, authkey);
        String response;
        StringBuilder memo;
        try {
            memo = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <title>Ominet Authentication Token</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      font-family: system-ui, -apple-system, Segoe UI, Roboto, \"Helvetica Neue\", Arial, sans-serif;\n" +
                    "      background-color: #f4f4f8;\n" +
                    "      margin: 0;\n" +
                    "      padding: 0;\n" +
                    "      color: #333;\n" +
                    "    }\n" +
                    "    .container {\n" +
                    "      max-width: 600px;\n" +
                    "      margin: 30px auto;\n" +
                    "      background: #fff;\n" +
                    "      border-radius: 10px;\n" +
                    "      box-shadow: 0 3px 12px rgba(0,0,0,0.1);\n" +
                    "      overflow: hidden;\n" +
                    "    }\n" +
                    "    .header {\n" +
                    "      background-color: #4d3b7a;\n" +
                    "      color: #fff;\n" +
                    "      text-align: center;\n" +
                    "      padding: 25px 15px;\n" +
                    "    }\n" +
                    "    .header img {\n" +
                    "      max-width: 70px;\n" +
                    "      margin-bottom: 10px;\n" +
                    "    }\n" +
                    "    .content {\n" +
                    "      padding: 25px;\n" +
                    "      text-align: center;\n" +
                    "    }\n" +
                    "    .content h1 {\n" +
                    "      font-size: 20px;\n" +
                    "      color: #4d3b7a;\n" +
                    "      margin-bottom: 10px;\n" +
                    "    }\n" +
                    "    .content p {\n" +
                    "      font-size: 14px;\n" +
                    "      margin: 6px 0;\n" +
                    "      line-height: 1.6;\n" +
                    "    }\n" +
                    "    .token-box {\n" +
                    "      background: #f9f9fb;\n" +
                    "      border: 2px dashed #4d3b7a;\n" +
                    "      border-radius: 8px;\n" +
                    "      display: inline-block;\n" +
                    "      padding: 15px 25px;\n" +
                    "      margin: 20px 0;\n" +
                    "    }\n" +
                    "    .token {\n" +
                    "      font-size: 24px;\n" +
                    "      font-weight: bold;\n" +
                    "      color: #4d3b7a;\n" +
                    "      letter-spacing: 4px;\n" +
                    "    }\n" +
                    "    .expiry {\n" +
                    "      color: #dc3545;\n" +
                    "      font-size: 13px;\n" +
                    "      margin-top: 10px;\n" +
                    "    }\n" +
                    "    .footer {\n" +
                    "      font-size: 12px;\n" +
                    "      color: #777;\n" +
                    "      text-align: center;\n" +
                    "      background: #f1f1f1;\n" +
                    "      padding: 15px;\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"container\">\n" +
                    "    <div class=\"header\">\n" +
                    "      <img src=\"https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png\" alt=\"Ominet Logo\">\n" +
                    "      <h2>Ominet Authentication</h2>\n" +
                    "    </div>\n" +
                    "    <div class=\"content\">\n" +
                    "      <h1>Hi, <strong>");
            memo.append(username);
            memo.append("</strong></h1>\n" +
                    "      <p>Please use the following authentication key to complete your login process.</p>\n" +
                    "      <div class=\"token-box\">\n" +
                    "        <div class=\"token\">");
            memo.append(authkey);
            memo.append("</div>\n" +
                    "      </div>\n" +
                    "      <p class=\"expiry\">This token is valid for <strong>2 hours</strong>.</p>\n" +
                    "      <p>If you did not request this, you can safely ignore this email.</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\">\n" +
                    "      &copy; <script>document.write(new Date().getFullYear());</script> Ominet by Aerosimo Ltd. All rights reserved.<br>\n" +
                    "      This is an automated message, please do not reply.\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>");
            response = Herald.announce(email,"Ominet login notification",memo.toString(),"");
            log.info("Sent login notification email from Ominet to {}", email);
        } catch (Exception err) {
            response = "Login Email failed";
            log.error("{} failed with email error - {}", AuthenticationEmail.class.getName(), err);
        }
        return response;
    }
}