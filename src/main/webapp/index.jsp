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
    <title>AuthCore | Aerosimo Ltd</title>
    <style>
      body {
        font-family: "Segoe UI", sans-serif;
        background-color: #f4f3fb;
        margin: 0;
        color: #333;
      }

      header {
        background-color: #4d3b7a;
        color: white;
        text-align: center;
        /* centers text */
        padding: 1rem 0 1.5rem 0;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
        display: flex;
        flex-direction: column;
        /* stack logo + title + subtitle */
        align-items: center;
        /* horizontally center all content */
      }

      header img {
        max-width: 100px;
        margin-bottom: 0.5rem;
        /* spacing between logo and title */
      }

      .container {
        max-width: 1100px;
        margin: 2rem auto;
        padding: 0 1rem;
      }

      .intro {
        text-align: center;
        margin-bottom: 2rem;
      }

      .card {
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        padding: 1.5rem;
        margin-bottom: 1.5rem;
        border-left: 6px solid #4d3b7a;
      }

      .endpoint-title {
        font-size: 1.2rem;
        color: #4d3b7a;
        margin-bottom: 0.3rem;
      }

      .endpoint-url {
        font-family: monospace;
        background: #f1eefb;
        padding: 0.4rem 0.7rem;
        border-radius: 6px;
        display: inline-block;
        color: #222;
      }

      .method {
        font-weight: bold;
        background: #4d3b7a;
        color: #fff;
        border-radius: 5px;
        padding: 0.2rem 0.5rem;
        font-size: 0.85rem;
      }

      textarea {
        width: 100%;
        height: 100px;
        font-family: monospace;
        padding: 0.5rem;
        border-radius: 6px;
        border: 1px solid #ccc;
        margin-top: 0.5rem;
        resize: vertical;
      }

      button {
        background-color: #4d3b7a;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        border-radius: 8px;
        cursor: pointer;
        margin-top: 0.5rem;
      }

      button:hover {
        background-color: #3c2f62;
      }

      pre {
        background: #f1eefb;
        padding: 0.75rem;
        border-radius: 8px;
        overflow-x: auto;
        max-height: 250px;
      }

      footer {
        background-color: #4d3b7a;
        color: white;
        text-align: center;
        padding: 1rem;
        margin-top: 3rem;
        font-size: 0.9rem;
      }
    </style>
  </head>
  <body>
    <header>
      <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="Aerosimo Logo">
      <h1>AuthCore | Aerosimo Ltd</h1>
      <p>A hub where all authentication flows converge</p>
    </header>
    <div class="container">
      <div class="intro">
        <p>Use this dashboard to test the AuthCore Authentication REST API endpoints directly from your browser. Edit the request payloads and click <b>Try It</b> to send live requests. </p>
      </div>
      <!-- Register -->
      <div class="card">
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
        <script>
          // Dynamically get context path from JSP
          const contextPath = "<%= request.getContextPath() %>";

          async function tryEndpoint(endpoint, payloadId, responseId) {
            const payloadText = document.getElementById(payloadId).value;
            const responseBox = document.getElementById(responseId);
            responseBox.textContent = "⏳ Sending request...";

            try {
              // Full endpoint URL based on context
              const fullUrl = `${contextPath}${endpoint}`;

              const response = await fetch(fullUrl, {
                method: "POST",
                headers: {
                  "Content-Type": "application/json",
                  "Accept": "application/json"
                },
                body: payloadText
              });

              const contentType = response.headers.get("content-type");

              if (contentType && contentType.includes("application/json")) {
                const result = await response.json();
                responseBox.textContent = JSON.stringify(result, null, 2);
              } else {
                const text = await response.text();
                responseBox.textContent =
                  "⚠️ Received non-JSON response:\n\n" + text.substring(0, 500);
              }
            } catch (err) {
              responseBox.textContent = "❌ Error: " + err.message;
            }
          }
        </script>
  </body>
</html>