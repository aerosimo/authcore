/******************************************************************************
 * This piece of work is to enhance authcore project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      main.js                                                         *
 * Created:   14/11/2025, 01:03                                               *
 * Modified:  14/11/2025, 01:03                                               *
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

// Use JSP context path to ensure correct deployment URL
const contextPath = "<%= request.getContextPath() %>";
async function tryEndpoint(endpoint, payloadId, responseId) {
  const payloadText = document.getElementById(payloadId).value;
  const responseBox = document.getElementById(responseId);
  // Reset previous content and badge
  responseBox.textContent = "⏳ Sending request...";
  responseBox.className = "";
  // Clean endpoint path
  const cleanEndpoint = endpoint.startsWith("/") ? endpoint.substring(1) : endpoint;
  const fullUrl = `${contextPath}/${cleanEndpoint}`;
  console.log("Sending request to:", fullUrl); // Debugging
  try {
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
      // Success badge
      const badge = document.createElement("span");
      badge.className = "status-badge success";
      badge.textContent = "✅ Success";
      responseBox.appendChild(badge);
    } else {
      const text = await response.text();
      responseBox.textContent = "⚠️ Received non-JSON response:\n\n" + text.substring(0, 500);
      const badge = document.createElement("span");
      badge.className = "status-badge error";
      badge.textContent = "❌ Error";
      responseBox.appendChild(badge);
    }
  } catch (err) {
    responseBox.textContent = "❌ Error: " + err.message;
    const badge = document.createElement("span");
    badge.className = "status-badge error";
    badge.textContent = "❌ Error";
    responseBox.appendChild(badge);
  }
}