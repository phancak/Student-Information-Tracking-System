# Student Information Tracking System
The system stores student information on a cloud based database from which the data can be remotely accessed or edited using a java based client.

## Project Description
A comprehensive solution for schools to track classes, instructor, and student information. Includes a Java platform independent application for the management of all critical facets of educational institutional operations, ensuring streamlined workflows and improved productivity. The main features are as follows:
- Create/Edit subjects with scheduled sections
- Add/Edit instructors and students
- Assign instructors to sections
- Instructors can create custom marked items for each section
- Enrol students in class sections
- Automatic performance reports are generated for sections, students, and instructors

### - Functional Diagram
![Functional_Diagram.png](Functional_Diagram.png)
The functional goal of the system is to allow users to access and edit student data at which point the data are validated and transferred to a secure storage medium. From there the data should be accessible for real-time analysis.

### - System Overview
![System Overview.png](System%20Overview.png)
The system consists of java based client running on a workstation, and a database used as data storage medium which is accessed through internet or LAN for editing or analysis purposes by various users with defined privileges.

### - System Architecture Diagram
![System_Architecture.png](System_Architecture.png)
Further detailed inner working of the java based client application are shown along with the database and the data analysis medium.

### - Database Diagram
![Screenshot 2024-03-12 at 4.00.04 PM.png](Screenshot%202024-03-12%20at%204.00.04%20PM.png)
Detailed diagram of the database tables used to store student data.

### - User Client Application Interface
Below are shown a series of sample screenshots of the java based client application with sample data included.
![Screenshot 2024-04-03 at 5.06.36 PM.png](Screenshot%202024-04-03%20at%205.06.36%20PM.png)
![Screenshot 2024-04-03 at 5.11.40 PM.png](Screenshot%202024-04-03%20at%205.11.40%20PM.png)
![Screenshot 2024-04-03 at 5.12.41 PM.png](Screenshot%202024-04-03%20at%205.12.41%20PM.png)
![Screenshot 2024-04-03 at 5.12.04 PM.png](Screenshot%202024-04-03%20at%205.12.04%20PM.png)

## Table of Contents
1. [Installation](#installation)
2. [Usage](#usage)
3. [Features](#features)
4. [Contributing](#contributing)
5. [License](#license)
6. [Authors and Acknowledgments](#authors-and-acknowledgments)
7. [Contact Information](#contact-information)
8. [Badges](#badges)
9. [Changelog](#changelog)
10. [FAQ](#faq)

## Installation
Follow these steps to set up and run the Java application with a MySQL database:

### Prerequisites

1. **Java Development Kit (JDK)**: Ensure you have JDK installed. You can download it from [here](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **MySQL Database**: Install MySQL Server. You can download it from [here](https://dev.mysql.com/downloads/mysql/).

3. **Maven**: Ensure you have Maven installed for managing the project dependencies. You can download it from [here](https://maven.apache.org/download.cgi).

### Step-by-Step Instructions

1. **Clone the Repository**:
    ```sh
    git clone https://github.com/phancak/Student-Information-Tracking-System-Java-Client.git
    cd repo
    ```

2. **Set Up MySQL Database**:
    - Start the MySQL server.
    - Create a new database:
        ```sql
        CREATE DATABASE School;
        ```
    - Create a new 'root' user with pass '12345678' and grant necessary privileges if it does not already exist:
        ```sql
        CREATE USER 'root'@'localhost' IDENTIFIED BY '12345678';
        GRANT ALL PRIVILEGES ON School.* TO 'root'@'localhost';
        FLUSH PRIVILEGES;
        ```
    - Run 'School_Init.sql' to setup database tables, procedures, and views
    - Run 'School_Init.sql' to initialize the database with sample data


3. **Configure the Application**:
    - Update the `application.properties` (or `application.yml` if you're using YAML) file with your MySQL database configuration:
        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/my_database
        spring.datasource.username=my_user
        spring.datasource.password=my_password
        spring.jpa.hibernate.ddl-auto=update
        ```

4. **Build the Project**:
    - Navigate to the project directory and build the project using Maven:
        ```sh
        mvn clean install
        ```

5. **Run the Application**:
    - Run the Java application:
        ```sh
        mvn spring-boot:run
        ```

## Usage


## Features

- **Instructor Management**
    - Create and manage instructor profiles including their personal information, specialization, and educational background.

- **Subject Management**
    - Create and manage subjects including subject names, numbers, and descriptions.

- **Student Management**
    - Add and manage student profiles including personal information and high school average.

- **Section Management**
    - Create and manage sections for subjects, including the start and end times, room, and days of the week.
    - Assign instructors to sections.

- **Enrollment Management**
    - Enroll students into sections.
    - Automatically enroll students in random sections based on specified criteria.

- **Graded Item Management**
    - Create and manage graded items for each section, including titles, descriptions, and dates.

- **Grade Book**
    - Generate and manage a grade book that combines graded items with student enrollments.
    - Populate grade books with randomly generated grades based on statistical models.

- **Procedures and Views**
    - Utilize stored procedures for complex operations like enrollment initialization, grade book population, and generating graded item averages.
    - Access detailed information on sections, instructors, and subjects through views.

- **Data Integrity and Constraints**
    - Ensure data integrity with primary keys, foreign keys, and check constraints.
    - Cascade updates and deletes to maintain consistent data relationships.


## Contributing
Guidelines for contributing to the project.

## License
This project is licensed under the MIT License.

## Authors and Acknowledgments
- **Author Name** - Initial work
- **Contributor Name** - Additional contributions

## Contact Information
For any inquiries, please contact [your-email@example.com](mailto:your-email@example.com).

## Badges
[![Build Status](https://img.shields.io/travis/username/repo.svg)](https://travis-ci.org/username/repo)
[![Coverage Status](https://coveralls.io/repos/github/username/repo/badge.svg?branch=master)](https://coveralls.io/github/username/repo?branch=master)

## Changelog
- v1.0.0 - Initial release

## FAQ
**Q:** How do I set up the project?  
**A:** Follow the installation instructions above.