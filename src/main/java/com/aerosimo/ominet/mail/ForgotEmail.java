/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ForgotEmail.java                                                *
 * Created:   11/12/2025, 19:26                                               *
 * Modified:  11/12/2025, 19:26                                               *
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

public class ForgotEmail {

    private static final Logger log = LogManager.getLogger(ForgotEmail.class.getName());

    public static String sendMail(String username, String email, String token) {
        log.info("Preparing email body content to the following email address: {}", email);
        StringBuilder memo;
        String response;
        try {
            memo = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "  <title>Reset Your Password</title>\n" +
                    "</head>\n" +
                    "<body style=\"margin:0; padding:0; font-family: Arial, sans-serif; background-color:#f4f4f7; color:#333;\">\n" +
                    "  <table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#f4f4f7; padding:20px;\">\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\">\n" +
                    "        <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 6px rgba(0,0,0,0.1);\">\n" +
                    "          <!-- Header -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" style=\"background-color:#4d3b7a; padding:20px;\">\n" +
                    "              <img src=\"https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png\" alt=\"Aerosimo Logo\" style=\"max-width:100px; display:block;\">\n" +
                    "              <h1 style=\"color:#ffffff; margin:10px 0 0; font-size:24px;\">Password Reset Request</h1>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "\n" +
                    "          <!-- Body -->\n" +
                    "          <tr>\n" +
                    "            <td style=\"padding:30px; font-size:16px; line-height:1.6; color:#333;\">\n" +
                    "              <p>Hi ");
            memo.append(username);
            memo.append(",</p>\n" +
                    "              <p>We received a request to <strong>reset your Ominet account password</strong>. To proceed, please use the secure reset token below.</p>\n" +
                    "\n" +
                    "              <p>This token allows you to create a new password for your account:</p>\n" +
                    "\n" +
                    "              <!-- Reset Token Box -->\n" +
                    "              <div style=\"text-align:center; margin:30px 0;\">\n" +
                    "                <p style=\"font-size:18px; margin-bottom:10px;\">Your password reset token:</p>\n" +
                    "                <div style=\"display:inline-block; background-color:#f4f4f7; padding:15px 25px; border-radius:6px; font-size:24px; letter-spacing:5px; font-weight:bold; color:#4d3b7a; border:1px solid #ddd;\">");
            memo.append(token);
            memo.append("</div>\n" +
                    "              </div>\n" +
                    "\n" +
                    "              <p>Enter this token in the password reset page to continue.</p>\n" +
                    "              <p>If you did <strong>not</strong> request a password reset, you can safely ignore this email. Your account remains secure.</p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "\n" +
                    "          <!-- Footer -->\n" +
                    "          <tr>\n" +
                    "            <td align=\"center\" style=\"background-color:#f8f9fa; padding:20px; font-size:14px; color:#666;\">\n" +
                    "              <p>&copy; <script>document.write(new Date().getFullYear());</script> Ominet by Aerosimo Ltd. All rights reserved.</p>\n" +
                    "              <p>\n" +
                    "                <a href=\"mailto:support@aerosimo.com?subject=Password%20Reset%20Assistance\" style=\"color:#4d3b7a; text-decoration:none;\">Contact Support</a> |\n" +
                    "                <a href=\"https://yourdomain.com/security\" style=\"color:#4d3b7a; text-decoration:none;\">Security Settings</a>\n" +
                    "              </p>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </table>\n" +
                    "</body>\n" +
                    "</html>");
            response = Herald.announce(email,"Reset Your Password",memo.toString(),"");
            log.info("Sent forgot password email from Ominet to {}", email);
        } catch (Exception err) {
            response = "Forgot Password Email failed";
            log.error("{} failed with email error - {}", ForgotEmail.class.getName(), err);
        }
        return response;
    }
}