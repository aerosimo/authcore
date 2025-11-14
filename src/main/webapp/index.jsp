<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance authcore project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      index.jsp                                                      ~
  ~ Created:   10/11/2025, 14:25                                              ~
  ~ Modified:  10/11/2025, 14:25                                              ~
  ~                                                                           ~
  ~ Copyright (c)  2025.  Aerosimo Ltd                                        ~
  ~                                                                           ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a   ~
  ~ copy of this software and associated documentation files (the "Software"),~
  ~ to deal in the Software without restriction, including without limitation ~
  ~ the rights to use, copy, modify, merge, publish, distribute, sublicense,  ~
  ~ and/or sell copies of the Software, and to permit persons to whom the     ~
  ~ Software is furnished to do so, subject to the following conditions:      ~
  ~                                                                           ~
  ~ The above copyright notice and this permission notice shall be included   ~
  ~ in all copies or substantial portions of the Software.                    ~
  ~                                                                           ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,           ~
  ~ EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES           ~
  ~ OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                  ~
  ~ NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                ~
  ~ HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,              ~
  ~ WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING              ~
  ~ FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                ~
  ~ OR OTHER DEALINGS IN THE SOFTWARE.                                        ~
  ~                                                                           ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->

 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <!DOCTYPE html>
 <html lang="en">
   <head>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <meta content="Elijah Omisore" name="author">
     <meta content="authcore 1.0.0" name="generator">
     <meta content="authcore" name="application-name">
     <meta content="IE=edge" http-equiv="X-UA-Compatible">
     <meta content="authcore" name="apple-mobile-web-app-title">
     <meta content="Oracle, Java, Tomcat, Maven, SOA, OSB, Jenkins, Bitbucket, Github, MFT" name="keywords">
     <!-- Title -->
     <title>AuthCore | Aerosimo Ltd</title>
     <!-- Favicon -->
     <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
     <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
     <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
     <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
     <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
     <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
     <!-- CSS (local) -->
     <link href="assets/css/main.css" rel="stylesheet">
   </head>
   <body>
     <header>
       <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="Aerosimo Logo">
       <h1>AuthCore | Aerosimo Ltd</h1>
       <p>A hub where all authentication flows converge</p>
     </header>
     <div class="container">
       <div class="intro">
         <p>Use this dashboard to test the AuthCore Authentication REST API endpoints directly from your browser. Edit payloads and click <b>Try It</b> to send live requests. </p>
       </div>
       <!-- Each endpoint card --><%-- Example: Register --%> <div class="card">
         <div class="endpoint-title">Register User</div>
         <div>
           <span class="method">POST</span>
           <span class="endpoint-url">/api/auth/register</span>
         </div>
         <p>Registers a new user with <code>username</code>, <code>email</code>, and <code>password</code>. </p>
         <textarea id="registerPayload">{
   "username": "john_doe",
   "email": "john@example.com",
   "password": "P@ssw0rd"
 }</textarea>
         <button onclick="tryEndpoint('/api/auth/register', 'registerPayload', 'registerResponse')">Try It</button>
         <pre id="registerResponse"></pre>
       </div>
       <!-- Verify -->
       <div class="card">
         <div class="endpoint-title">Verify Email</div>
         <div>
           <span class="method">POST</span>
           <span class="endpoint-url">/api/auth/verify</span>
         </div>
         <p>Verifies a user’s email using the provided <code>token</code>. </p>
         <textarea id="verifyPayload">{
  "token": "VERIFICATION_TOKEN"
}</textarea>
         <button onclick="tryEndpoint('/api/auth/verify', 'verifyPayload', 'verifyResponse')">Try It</button>
         <pre id="verifyResponse"></pre>
       </div>
       <!-- Login -->
       <div class="card">
         <div class="endpoint-title">Login</div>
         <div>
           <span class="method">POST</span>
           <span class="endpoint-url">/api/auth/login</span>
         </div>
         <p>Authenticates a user and returns an <code>authKey</code> for session access. </p>
         <textarea id="loginPayload">{
  "username": "john_doe",
  "password": "P@ssw0rd"
}</textarea>
         <button onclick="tryEndpoint('/api/auth/login', 'loginPayload', 'loginResponse')">Try It</button>
         <pre id="loginResponse"></pre>
       </div>
       <!-- Validate -->
       <div class="card">
         <div class="endpoint-title">Validate Auth Key</div>
         <div>
           <span class="method">POST</span>
           <span class="endpoint-url">/api/auth/validate</span>
         </div>
         <p>Validates a previously issued authentication key.</p>
         <textarea id="validatePayload">{
  "authKey": "authKey_123456"
}</textarea>
         <button onclick="tryEndpoint('/api/auth/validate', 'validatePayload', 'validateResponse')">Try It</button>
         <pre id="validateResponse"></pre>
       </div>
       <!-- Logout -->
       <div class="card">
         <div class="endpoint-title">Logout</div>
         <div>
           <span class="method">POST</span>
           <span class="endpoint-url">/api/auth/logout</span>
         </div>
         <p>Logs out the current session and invalidates the token.</p>
         <textarea id="logoutPayload">{
  "authKey": "authKey_123456"
}</textarea>
         <button onclick="tryEndpoint('/api/auth/logout', 'logoutPayload', 'logoutResponse')">Try It</button>
         <pre id="logoutResponse"></pre>
       </div>
     </div>
     <footer> AuthCore Project &copy; <%= java.time.Year.now() %> | Powered by Aerosimo </footer>
    <!-- JS (local) -->
    <script src="assets/js/main.js"></script>
   </body>
 </html>