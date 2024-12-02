# COMP2130 Human Resource Management and Payroll System

## Group 93

- **Fellipe C.T.C** - 101497831
- **Ayesha Akbar** - 100949840
- **Claire Lee** - 100882058
- **Suthan Sureshkumar** - 101511337

---
## Overview

The HRM and Payroll System is developed to simplify HR operations such as managing employee records, processing payrolls, and generating detailed reports. It offers a user-friendly interface backed by robust database integration for secure and efficient data handling. The system is structured into several key components:

- The **Main.java** file serves as the entry point of the application, initializing and launching the graphical user interface.
- Navigation between different application pages is managed by **PageController.java**, ensuring a seamless user experience.
- **LoginController.java** handles user authentication, including secure login functionality and role-based access control.
- Employee management operations, such as adding, updating, and deleting records, are managed by **EmployeeController.java**.
- Payroll calculations and the saving of payroll records are handled by **PayrollController.java**.


---

## Features

- **Secure Login**: Ensures data security with hashed passwords and role-based access control.
- **Employee Management**: Provides functionality to add, edit, delete, and view employee records with input validation.
- **Payroll Processing**: Automates salary calculations, including overtime, bonuses, and tax deductions.
- **Reporting**: Generates detailed payroll reports for employees and departments.
- **Database Integration**: Stores all data in a structured SQLite database for reliability and persistence.

---

## Our Approach

### 1. **Design**
The system was structured using a layered architecture to ensure scalability and modularity:
- **Presentation Layer**: JavaFX GUIs for intuitive user interaction (e.g., `login.fxml`, `payroll.fxml`).
- **Business Logic Layer**: Centralized utility classes for validation, hashing, and payroll calculations.
- **Data Access Layer**: Repositories to manage database interactions (`EmployeeRepository`, `PayrollRepository`).
- **Data Models**: Classes like `User`, `Employee`, and `Payroll` represent core entities.

### 2. **Implementation**
- **Authentication**:
    - Implemented a secure login system with password hashing and role-based access for admins and employees.
- **Employee Management**:
    - Developed input forms with validation for fields like email and phone numbers.
    - Ensured consistent handling of employee data through the use of utility methods.
- **Payroll Processing**:
    - Built logic for calculating total pay, including overtime and tax deductions, encapsulated in the `Payroll` class.
    - Integrated payroll records into the database for future reference.
- **Reporting**:
    - Provided functionality to fetch and display payroll and employee data in the reporting section.

### 3. **Testing**
- Conducted extensive testing to validate all features, including edge cases for employee and payroll operations.
- Debugged database connectivity issues and ensured smooth navigation between different application pages.

### 4. **Database Integration**
- Used SQLite as the database backend to store employee, payroll, and user authentication data.
- Designed structured tables to maintain consistency and ensure efficient data retrieval.

---

## Database Structure

### `users` Table:
| Field         | Type     | Description            |
|---------------|----------|------------------------|
| user_id       | INTEGER  | Primary Key            |
| username      | TEXT     | Unique username        |
| password      | TEXT     | Hashed password        |
| role          | TEXT     | User role (Admin/Employee) |

### `employees` Table:
| Field         | Type     | Description            |
|---------------|----------|------------------------|
| employee_id   | INTEGER  | Primary Key            |
| first_name    | TEXT     | First name             |
| last_name     | TEXT     | Last name              |
| email         | TEXT     | Email address          |
| phone         | TEXT     | Phone number           |
| salary        | REAL     | Base salary            |

### `payroll` Table:
| Field         | Type     | Description            |
|---------------|----------|------------------------|
| payroll_id    | INTEGER  | Primary Key            |
| employee_id   | INTEGER  | Foreign Key (employees)|
| base_salary   | REAL     | Base salary            |
| hours_worked  | REAL     | Hours worked           |
| overtime_hours| REAL     | Overtime hours         |
| bonus         | REAL     | Bonus amount           |
| tax           | REAL     | Tax deductions         |
| total_pay     | REAL     | Final calculated pay   |
| pay_date      | TEXT     | Date of payroll        |

---


## System Workflow

1. **Login**: The user logs in with valid credentials. Admins have access to all features; employees have restricted access.
2. **Employee Management**: Admins can add, update, or delete employee records as needed.
3. **Payroll Processing**: Payrolls are calculated based on user inputs like base salary, hours worked, and overtime.
4. **Reporting**: Payroll and employee data can be reviewed and exported as reports.



