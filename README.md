# Cy.io io Game framework [Group NV_4] COM S 309 ISU
  This project was the product of a semester-long group project for the Computer Science 309 course at Iowa State University. Out team consisted of myself and three other team members with a range of prior programming experiences. As stated below, the original concept was to allow anyone to customize and create online games with intuitive configuration files and images they would supply. That game would then be hosted on our platform, abstracting any programming and hosting needs away from the user. The final product that we completed did not ultimately live up to the intended initial dream, although each team member contributed in various ways in accordance with their skill level.

### Personal Contributions (Matthew Dwyer)
  My personal (Matthew Dwyer) responsibilities were on the frontend game engine written in JavaScript and the backend server hosting capabilities written in Java using the Spring Boot framework. The entirety of the frontend game engine was my own work, along with roughly 1/3 - 1/2 of the backend server code, specifically any part relating to server-client communications or API relating to server connections. I also contributed the initial layout of the homepage prior to being converted to React modules and collaborated to get both realtime chat and notification WebSockets working in the Android application using ScokJS and STOMP. Finally, I also created the entirety of the CD/CI pipeline that automatically deployed both the front and backend code to both production and development environments set up on our backend server set up by myself (Project originally hosted on GitLab).
  
### Disclaimers
  - This project was originally hosted on GitLab, thus not all branches and contributors were properly copied when moved ot GitHub.
  - One contributor (Quinn Strum) is not credited with their work in this project despite spearheading the Android application development.
  - CD/CI was implemented and all phases working properly running tests, building, and deploying to both dev and prod environments while hosted in GitLab.

### Final Deliverables
- Basic home landing page connected to the backend game database using REST APIs that displays games that can be played (REACT + CSS)
- Customizable in-browser real-time game engine capable of multiple WebSocket connections and retrieving game data using REST APIs (JavaScript)
- Backend server that managed database connections and updates over REST APIs, WebSockets with all connected clients, and OAuth2 authentification and user login and creation (Spring Boot - Java)
- Android app that allowed users to sign in, chat with players in-game, display user data, and show live game leaderboards (Android - Java)

### Major Features
- Realtime game engine capable of adapting basic game features based on loaded JSON config file
- Realtime player data, leaderboard, chat, and notification WebSockets between multiple clients
- backend REST API capable of user authentification and creation, game management, server management, and admin actions
- 20+ Unit tests covering the frontend game engine, backend management, and Android code.
- Documentation for all parts of the project.

### Project Missteps
  While we are very proud of what we were able to accomplish, looking back, there would be a few changes that would be made. Primarily, we would have reduced our project scope. Initially, our group members slightly overestimated the familiarity some members of our team had with various technologies and frameworks. This lead to parts of our project obe left ina an incompleted state, and team members having to deviate from their predefined tasks to complete other parts of the project. Overall, one of the biggest takeaways from this project was the continued importance of team communication and collaboration.
  

### Incolpleted Parts
- The main React home landing page, login page, admin page, user creation page, and game creation page were all ultimately left in uncompleted states.
- The game engine could have been more feature-rich and expanded upon the game customizability.
- The backend server only currently supports running one server instance at a time.
- The Android application UI is very basic and functionality is overall in a proof of concept state.

### Final Project Poster

![Project Poster]()

### Initial Proposal

**Team 45 - 309 Project Proposal**
----------------------------------

### Team Photo

![Group_Picture.jpg](https://canvas.iastate.edu/groups/99987/files/8597068/preview)

Members left to right: Cole Martin, Matt Dwyer, Tom "Jidong" Sun, Quinn Sturm.

### Team Members & Experience - Group NZ 4

**Cole Martin (Tech Lead):**

*   *   Experience:
        *   Senior in Com S
        *   Proficient in Java, C, C++, and JavaScript 
        *   One Internship (JavaScript, React, Git)
    *   Contact Info:
        *   Email: [colem@iastate.edu](mailto:colem@iastate.edu)
        *   Phone: (712) 309-2429

**Matt Dwyer (Test Lead):**

*   *   Experience:
        *   Sophomore CprE (227, 228, 185)
        *   Internships:
            *   Propelware (JS, Apex, Salesforce)
            *   John Deere (Python, AWS)
            *   Zirous (Java \[Android\], Python, JS, HTML, AWS)
        *   Personal Experiences
            *   Java
            *   Android
            *   HTML, CSS, JS
            *   Python (Research)
            *   Machine Learning
            *   AWS
    *   Contact Info:
        *   Email: [dwyer@iastate.edu](mailto:dwyer@iastate.edu)
        *   Phone: (651) 366-7677

**Tom "Jidong" Sun (Repo Lead):**

*   *   Experience:
        *   Sophomore in CprE (185,227,228)
        *   Java | 3 years, project experience
        *   Android Studio | project experience 
        *   C++ | 1 year 
        *   Python | Started 3 weeks ago 
        *   Newbie | Yes
    *   Contact Info:
        *   Email: [jidongs@iastate.edu](mailto:jidongs@iastate.edu)
        *   Phone: (563) 650-9971

**Quinn Sturm (Schedule Lead):**

*   *   Experience:
        *   Junior in Comp Sci
        *   Java | 2 Semesters | 227, 228
        *   Newbie | I switched majors last year so my programming knowledge is fairly limited.
    *   Contact Info:
        *   Email: [qdsturm@iastate.edu](mailto:qdsturm@iastate.edu)
        *   Phone: (952) 440-3574

### Project 1: Cy.io

**Description: **

  The Cy.io will be an IO game engine/generator that creates and hosts IO games based on config files and assets provided by users. It is a server hosting application and file plug-in for the games that abstract programming, hosting, and server management from the game maker. This project would deal with multiple users types, unauthenticated game playing users, people who made an account and can upload games, and an admin account for framework debugging and management of service as a whole. A REST API would be employed for user management and game creation, while real-time web-sockets would be sued for communicating between client and server in real-time during gameplay.
  
  The client-side will be a web-app where the users can upload and edit custom config files for their games and users can play the games published. The game will also be displayed and played on the client-side through custom javascript to run the games in the browser. The server-side will compile the config files and generate the games. The server will then host the real-time game for the client-side web-app, automatically scaling to traffic and managing load.

### Language/Platform/Libraries: 

**Server side -** 

*   Java/Spring boot

*   Config file validater & parser (Java)
*   File storage (Spring boot?)
*   Database of files/user/games (Relation Database SQL)
*   Web sockets (Spring Boot)
*   Rest API (React / Spring Boot)
*   Game servers (Java / Spring Boot)
*   Optional Features

*   AI/genetic algorithm
*   Cheat Detection
*   Cloud Scaling

**Client side -**

*   HTML/CSS (Pages)

*   Login
*   Account Creation
*   Home/Game list
*   Game page (Where you play the game)
*   Create game Page
*   Optional Pages

*   Admin / Debug Page

*   Java Script

*   Customizable game engine

*   Socket.io 

*   Connections (Sockets)

*   AJAX

*   Rest Requests


**Large/Complex Aspects:**

  Server Side file management and hosting (Spring Boot / React JS), Database management (SQL & RDS), real time web-sockets (Socket.io & Spring Boot), Rest API (Spring Boot / React JS), game server management (Java Threading & Load balancing), 5+ screens/pages (HTML & CSS), client side game engine (JS & socket.io & Ajax). Optional components could include game opponent AI's, an additional Admin user page, cheat detection, and migration to the cloud (AWS) for better scaling capabilities.
