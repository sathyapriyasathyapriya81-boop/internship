# FarmMapper

A secure web application for mapping and managing farmland data, built with Spring Security (JWT authentication) and the Google Maps API for location-based visualization.

## Overview

FarmMapper lets users register farmland locations on an interactive map and secures access to that data using JWT-based authentication, so only authorized users can view or modify records.

## Features

- User authentication and authorization via JWT (JSON Web Tokens)
- Interactive map integration using Google Maps API
- [ ] Add/view farmland boundaries or markers — describe if implemented
- [ ] Role-based access control (e.g., admin vs regular user)
- [ ] Farm details storage (area, crop type, owner, etc.)

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Security
- **Authentication:** JWT (JSON Web Tokens)
- **Maps/Location:** Google Maps API
- **Database:** MySQL
- **Frontend:** [ ] e.g. HTML-CSS-JS / React — fill in actual frontend used

## Architecture

```
Client (Browser + Google Maps UI)
      |
Spring Boot Controller
      |
JWT Filter (validates token on each request)
      |
Service Layer
      |
Repository Layer (JPA/Hibernate)
      |
MySQL Database
```

### Authentication Flow
1. User logs in with credentials → server validates and issues a JWT.
2. Client stores the JWT and sends it in the `Authorization` header on subsequent requests.
3. A Spring Security filter intercepts each request, validates the JWT, and grants/denies access.

## Getting Started

### Prerequisites
- Java JDK 17+
- Maven
- MySQL Server
- Google Maps API key ([Google Cloud Console](https://console.cloud.google.com))

### Installation

```bash
git clone https://github.com/<your-username>/farmmapper.git
cd farmmapper
```

1. Create a MySQL database:
   ```sql
   CREATE DATABASE farmmapper_db;
   ```
2. Update `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/farmmapper_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   jwt.secret=your_jwt_secret_key
   google.maps.api.key=your_google_maps_api_key
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Open `http://localhost:8080` in your browser.

## Project Structure

```
src/
 ├── main/
 │   ├── java/com/farmmapper/
 │   │   ├── config/        (Spring Security & JWT config)
 │   │   ├── controller/
 │   │   ├── service/
 │   │   ├── repository/
 │   │   └── model/
 │   └── resources/
 │       ├── static/         (map UI)
 │       └── application.properties
```

## What I Learned

- Implementing JWT-based authentication and authorization with Spring Security
- Securing REST endpoints with custom security filters
- Integrating the Google Maps API into a web application
- Understanding stateless authentication vs traditional session-based auth

PulseChat

A real-time chat application built with Spring Boot and WebSocket, enabling instant bi-directional communication between multiple users.

## Overview

PulseChat demonstrates real-time, event-driven communication using the WebSocket protocol (STOMP over SockJS), moving beyond traditional request-response HTTP to keep users connected and updated live.

## Features

- Real-time messaging between multiple connected users
- WebSocket-based instant message delivery (no page refresh needed)
- [ ] Multiple chat rooms / channels — describe if implemented
- [ ] Online/offline user status
- [ ] Message timestamps

## Tech Stack

- **Backend:** Java, Spring Boot
- **Real-time Communication:** WebSocket (STOMP + SockJS)
- **Frontend:** [ ] e.g. HTML-CSS-JS / React — fill in actual frontend used

## Architecture

```
Client A                     Client B
   |                             |
   └──── WebSocket (STOMP) ──────┘
                |
     Spring Boot WebSocket Config
                |
       Message Broker (in-memory
        or external, e.g. RabbitMQ)
                |
        Controller broadcasts
        message to subscribers
```

Unlike a typical REST flow, once a WebSocket handshake is established, the server can push messages to clients instantly without them polling for updates.

## Getting Started

### Prerequisites
- Java JDK 17+
- Maven

### Installation

```bash
git clone https://github.com/<your-username>/pulsechat.git
cd pulsechat
mvn spring-boot:run
```

Open `http://localhost:8080` in your browser (open multiple tabs to test real-time messaging between "users").

## Project Structure

```
src/
 ├── main/
 │   ├── java/com/pulsechat/
 │   │   ├── config/       (WebSocket configuration)
 │   │   ├── controller/   (message handling)
 │   │   └── model/
 │   └── resources/
 │       ├── static/       (frontend chat UI)
 │       └── application.properties
```

## What I Learned

- Setting up WebSocket communication in Spring Boot (STOMP over SockJS)
- Understanding the difference between request-response (REST) and persistent, event-driven connections (WebSocket)
- Broadcasting messages to multiple subscribed clients in real time
- Handling connection/disconnection events

## Author

**Sathiya** — B.Sc. Computer Science, K.S.R. College of Arts and Science for Women
Built during Java Full Stack Developer Internship, Nandha Infotech (May–June 2026)
