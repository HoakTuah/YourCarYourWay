# YourCarYourWay - POC

This POC is intended for experimentation, demonstration, and as a foundation for future features. At this stage, only chat functionality is included; additional features may be added in subsequent phases.

---

## Prerequisites

### Front-End
- Angular | Version: 17+
- Node.js and npm | Version: 18+
- sockjs-client | version 1.5.1

### Back-End
- Java | 17+
- Spring Boot | Version: 3.1+
- Maven | Version: 3.8+
- Spring Data JPA | Version: (Inherited from Spring Boot parent)
- WebSocket (STOMP)

### Database
- PostgreSQL | Version: 14+

---

## Project Structure

The project is divided into two main parts:
- `ChatFE/`: Angular frontend application (client and support chat UIs)
- `YourCarYourWay/`: Spring Boot backend application (WebSocket server, business logic, persistence)

---

## Database Setup

1. Create a PostgreSQL database.
2. Use the provided SQL script located at `YourCarYourWay/SQL/data_postgresql.sql` to initialize the schema and data:
3. Update your `application.properties` in `YourCarYourWay/src/main/resources/` with your database credentials if needed.

---

## Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd ChatFE
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   ng serve
   ```

- Access the **Client Chat** interface at: `http://localhost:4200/client`
- Access the **Support Chat** interface at: `http://localhost:4200/support`

---

## Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd YourCarYourWay
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
---

## How It Works

- Users (clients and support) connect via the Angular app.
- Messages are sent in real-time using WebSocket (STOMP protocol).
- The backend processes, enriches, and persists each message, then broadcasts it to all connected clients.
- The UI displays messages with sender, timestamp, and content, updating live.

---


## Additional Information

- The project uses Spring Security (configurable) for future authentication needs.
- You can extend the project with authentication, user management, or advanced chat features.

---
