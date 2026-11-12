# Hotel Reservation System

A web-based Hotel Reservation System developed as part of the CA-3 academic project. The application uses Java Spring Boot and MySQL to manage hotel reservation operations.

## Project Overview

The Hotel Reservation System provides a web application for handling hotel-related operations, including room management, booking details, booking confirmation, and payment processing.

## Technologies Used

- **Backend:** Java, Spring Boot
- **Frontend:** HTML, CSS, JavaScript, Thymeleaf
- **Database:** MySQL
- **Build Tool:** Maven
- **Version Control:** Git and GitHub

## Project Structure

```text
Hotel-Reservation-System/
│
├── 01-Requirements/
├── 02-UML-Diagrams/
├── 03-UI-Design/
├── 04-Database/
├── 05-Source-Code/
├── 06-Screenshots/
├── 07-Testing/
├── 08-Report/
├── 09-Presentation/
│
├── README.md
└── .gitignore
```

## Main Modules

- Room Management
- Booking Management
- Booking Confirmation
- Payment Processing
- Database Integration

## Prerequisites

Install the following before running the project:

1. Java JDK
2. MySQL Server
3. Git (optional)
4. A web browser

## Database Configuration

1. Start your MySQL server.
2. Create a database named:

```sql
CREATE DATABASE hotel_reservation;
```

3. Import the project's database backup if you have received it separately.
4. Configure your database username and password.

Set the `DB_PASSWORD` environment variable to your MySQL password before starting the application.

For PowerShell:

```powershell
$env:DB_PASSWORD="your_mysql_password"
```

## How to Run the Project

1. Clone the repository:

```bash
git clone https://github.com/shashankshashi1344-dev/Hotel-Reservation-System.git
```

2. Open the project folder.

3. Navigate to the source code folder:

```bash
cd Hotel-Reservation-System/05-Source-Code
```

4. Set the database password in your environment.

5. Run the Spring Boot application:

**Windows:**

```powershell
.\mvnw.cmd spring-boot:run
```

6. Open your browser and visit:

```text
http://localhost:8080
```

## Project Documentation

The repository includes separate folders for:

- Requirements and specifications
- UML diagrams
- UI design
- Database documentation
- Source code
- Application screenshots
- Testing documentation
- Final project report
- Presentation

## Academic Project

**Project:** Hotel Reservation System  
**Course:** CA-3 Project

## Repository

[Hotel Reservation System – GitHub](https://github.com/shashankshashi1344-dev/Hotel-Reservation-System)

---

*This project was developed for academic purposes.*