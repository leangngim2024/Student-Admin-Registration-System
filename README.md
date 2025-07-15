# Student & Admin Registration System

A Java-based desktop application developed using **NetBeans IDE** that allows both students and administrators to register, manage, and interact with a user-friendly interface. The system includes features like user authentication, data validation, form handling, and MySQL database integration.

## 📌 Features

- Student and admin registration forms
- Input validation
- Calendar/date picker using `jcalendar`
- MySQL database connectivity for storing registration data
- Clear, intuitive GUI interface using Swing

## 🛠️ Technologies Used

- **Java SE (JDK 17)**
- **NetBeans IDE**
- **Swing GUI**
- **MySQL**
- **External Libraries**:
  - `jcalendar-1.4.jar`
  - `mysql-connector-j-8.x.x.jar`

## 📦 Requirements

- Java Development Kit (JDK 17 or compatible)
- NetBeans IDE (or any IDE that supports Java projects)
- MySQL Server
- Add external libraries:
        Download:
                jcalendar-1.4.jar
                mysql-connector-j-8.x.x.jar
                Right-click the project > Properties > Libraries
                Click Add JAR/Folder and add both JARs


## 📚 Setup Instructions

1. **Clone the repository:**
   ```bash
   https://github.com/leangngim2024/Student-Admin-Registration-System.git
   
2. **Open in NetBeans:**

Go to File > Open Project
Select the project folder
Add external libraries:
Download:
        jcalendar-1.4.jar
        mysql-connector-j-8.x.x.jar
        Right-click the project > Properties > Libraries
        Click Add JAR/Folder and add both JARs

3. **Configure the Database:**

**- Create database**

   CREATE DATABASE db_02;
   
**- Create requirement tables**
   
**-- Table for Admins**

CREATE TABLE admin (
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);

**-- Table for normal users**

CREATE TABLE user_tb (
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);

**--Table for recycle bin**

CREATE TABLE deleting_student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sex VARCHAR(100) NOT NUL,
    birth DATE NOT NUL,
    course VARCHAR(100) NOT NUL,
    time VARCHAR(100) NOT NUL,
    price DOUBLE NOT NUL,
    day VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(100) NOT NULL,
    picture VARCHAR(100) NOT NULL,
    locationStudy VARCHAR(100) NOT NULL
);

**-- Table for List Students**

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    sex VARCHAR(100) NOT NUL,
    birth DATE NOT NUL,
    course VARCHAR(100) NOT NUL,
    time VARCHAR(100) NOT NUL,
    price DOUBLE NOT NUL,
    day VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(100) NOT NULL,
    picture VARCHAR(100) NOT NULL,
    locationStudy VARCHAR(100) NOT NULL
);

Update database connection credentials inside your Java code

**Run the Project:**
Click the Run button in NetBeans or press Shift + F6

