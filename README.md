![MIT License](src/main/webapp/assets/img/MIT.png "MIT")

<details>
  <summary>License</summary>

**MIT License © 2025 Aerosimo**

Permission is hereby granted, free of charge, to any person obtaining a copy  
of this software and associated documentation files (the "Software"), to deal  
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
SOFTWARE.

The characters, names, events, articles, templates, or information provided by  
Aerosimo Ltd are fictional and for reference only. While we strive to keep the  
information up to date and correct, we make no representations or warranties of  
any kind, express or implied, about the completeness, accuracy, reliability,  
suitability, or availability with respect to the information, articles, templates,  
or related graphics contained in this document or any part of the project.  
Any reliance you place on such information is therefore strictly at your own risk.
</details>

---

![Project Cover](src/main/webapp/assets/img/cover.jpg "AuthCore")

# AuthCore
### 🔐 A Hub Where All Authentication Flows Converge

**AuthCore** — short for *Authentication Core* — is a centralized REST-based authentication service that provides token-driven access management for enterprise Java applications.

> “A hub where all authentication flows converge — emphasizing the foundational role of authentication.”

AuthCore serves as a **custom token authentication hub** designed to emulate lightweight JWT-style validation, registration, and session management using **Core Java + JAX-RS + JSP** and Oracle 19c as backend storage.

---

## 🧠 Project Badges & Tech Stack

<p align="center">
  <img src="https://img.shields.io/badge/Java-23%2B-orange?logo=java&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Jakarta%20EE-10-blue?logo=jakartaee&logoColor=white" alt="Jakarta EE 10">
  <img src="https://img.shields.io/badge/Apache%20TomEE-10.1.44-critical?logo=apachetomcat&logoColor=white" alt="Apache TomEE">
  <img src="https://img.shields.io/badge/Oracle%20Database-19c-red?logo=oracle&logoColor=white" alt="Oracle 19c">
  <img src="https://img.shields.io/badge/Maven-3.9%2B-C71A36?logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/REST%20API-JAX--RS-green?logo=rest&logoColor=white" alt="JAX-RS">
  <img src="https://img.shields.io/badge/Frontend-JSP%20%2F%20FetchAPI-purple?logo=html5&logoColor=white" alt="JSP + Fetch API">
  <img src="https://img.shields.io/badge/Logging-Log4j-yellow?logo=apache&logoColor=white" alt="Log4j">
</p>

---

## 🌍 Features

- ✅ RESTful authentication and validation APIs (`JAX-RS`)
- ✅ Custom token-based authentication (JWT-like)
- ✅ JSP dashboard for interactive endpoint testing
- ✅ Oracle 19c integration through PL/SQL packages
- ✅ Token persistence, validation, and logout handling
- ✅ Log4j-based auditing for login and verification events
- ✅ Built and deployed on **Apache TomEE 10 (Jakarta EE 10)**

---

## 🧩 Architecture Overview

       +-----------------------------+
       |        JSP Dashboard         |
       |  (Interactive API Tester)    |
       +-------------+---------------+
                     |
                     v
       +-----------------------------+
       |      AuthREST Service        |
       | (register / login / validate)|
       +-------------+---------------+
                     |
                     v
       +-----------------------------+
       |        AuthDAO Layer         |
       | (Token/Account operations)   |
       +-------------+---------------+
                     |
                     v
       +-----------------------------+
       |       Oracle 19c DB          |
       |    (authentication_pkg)      |
       +-----------------------------+


---

## 🛡️ AuthCore Security Architecture

        +-------------------+
        |     Client App    |
        | (Web, Mobile)    |
        +---------+---------+
                  |
                  v
        +-------------------+
        |   AuthREST API    |
        |  (Login/Register) |
        +---------+---------+
                  |
                  v
        +-------------------+
        |     AuthFilter    |
        |     (@Secured)    |
        +---------+---------+
                  |
           Token Valid? Yes/No
                  |
           +------+------+
           |             |
        +---v---+    +---v---+
        | Valid |    | Invalid|
        | Token |    | Token  |
        +-------+    +-------+
            |            |
    Access Granted   401/403 Response

---

## 🗃️ Project Structure

<pre>
authcore/
├─ README.md
├─ LICENSE
├─ authcore.yaml
├─ documentation.html
├─ pom.xml
├─ src/main/java/com/aerosimo/ominet/api/
│  ├─ AuthApplication.java
│  ├─ Secured.java
├─ src/main/java/com/aerosimo/ominet/api/rest/
│  ├─ AuthREST.java
├─ src/main/java/com/aerosimo/ominet/api/filters/
│  ├─ AuthFilter.java
├─ src/main/java/com/aerosimo/ominet/core/config/
│  ├─ Connect.java
├─ src/main/java/com/aerosimo/ominet/core/model/
│  ├─ Postmaster.java
│  ├─ Spectre.java
├─ src/main/java/com/aerosimo/ominet/dao/impl/
│  ├─ APIResponseDTO.java
│  ├─ RegisterRequestDTO.java
│  ├─ LoginRequestDTO.java
│  ├─ ValidateRequestDTO.java
│  ├─ LogoutRequestDTO.java
│  ├─ VerifyRequestDTO.java
├─ src/main/java/com/aerosimo/ominet/dao/mapper/
│  ├─ AuthDAO.java
├─ src/main/java/com/aerosimo/ominet/mail/
│  ├─ AuthenticationEmail.java
│  ├─ WelcomeEmail.java
├─ src/main/webapp/WEB-INF/
│  ├─ web.xml
├─ src/main/webapp/
│  ├─ index.jsp
│  ├─ assets/img/
│     ├─ logo.png
│     ├─ MIT.png
│     ├─ project.webp
</pre>

---

## 📚 API Documentation

- Swagger/OpenAPI: [authcore.yaml](authcore.yaml)
- Interactive docs: [documentation.html](documentation.html)

## ⚙️ Configuration Files

### **web.xml**

Defines REST endpoints and Jersey servlet mapping:

```xml
<servlet>
    <servlet-name>rest</servlet-name>
    <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
    <init-param>
        <param-name>jakarta.ws.rs.Application</param-name>
        <param-value>com.aerosimo.ominet.api.AuthApplication</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <servlet-name>rest</servlet-name>
    <url-pattern>/api/*</url-pattern>
</servlet-mapping>
```
🧱 Maven Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>jakarta.platform</groupId>
        <artifactId>jakarta.jakartaee-api</artifactId>
        <version>10.0.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jersey.containers</groupId>
        <artifactId>jersey-container-servlet</artifactId>
        <version>2.25.2</version>
    </dependency>
    <dependency>
        <groupId>org.glassfish.jersey.media</groupId>
        <artifactId>jersey-media-json-jackson</artifactId>
        <version>2.25.2</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.25.2</version>
    </dependency>
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <version>23.9.0.25.07</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

---
## 🚀 Deployment Guide
### Prerequisites

* Java: 23 or higher
* Server: Apache TomEE 10.1.44 (Jakarta EE 10)
* Database: Oracle 19c
* Build Tool: Maven 3.9+
* JNDI DataSource: jdbc/hats

### Steps

1. Clone repository 
```bash
   git clone https://github.com/aerosimo/authcore.git
   cd authcore
```
2. Build
```bash
    mvn clean package
```
3. Deploy

   *  Copy the WAR to TomEE’s /webapps folder.
   * Start TomEE.
   
4. Access endpoints

   * REST: http://localhost:8080/authcore/api/auth/register
   * JSP Dashboard: http://localhost:8080/authcore/index.jsp

---
## 🔒 REST Endpoints

| Endpoint         | Method | Description                      |
| ---------------- | ------ | -------------------------------- |
| `/auth/register` | POST   | Register a new user              |
| `/auth/verify`   | POST   | Verify email or activation token |
| `/auth/login`    | POST   | Authenticate user credentials    |
| `/auth/validate` | POST   | Validate token authenticity      |
| `/auth/logout`   | POST   | Log out a user session           |

### 💡 Example Requests
#### Register

```bash
    curl -X POST http://localhost:8080/authcore/api/auth/register \
         -H "Content-Type: application/json" \
         -d '{"username":"user1","email":"user1@mail.com","password":"Pass@123"}'
```

#### Login

```bash
    curl -X POST http://localhost:8080/authcore/api/auth/login \
         -H "Content-Type: application/json" \
         -d '{"username":"user1","password":"Pass@123"}'
```

#### Validate Token

```bash
    curl -X POST http://localhost:8080/authcore/api/auth/validate \
         -H "Content-Type: application/json" \
         -d '{"authKey":"ABCDEF12345"}'
```

## 🤝 Contributing
Contributions, feedback, and pull requests are welcome!
Help improve AuthCore — the hub where all authentication flows converge.

---
![Aerosimo Logo](src/main/webapp/assets/img/logo.png "Aerosimo")