# Hospital Patient Management System

A menu-driven Java console application for basic hospital record management.
Course End Project - A9602 Object Oriented Programming through Java Laboratory,
Vardhaman College of Engineering, Hyderabad.

**Author:** CH. Beulah (25881A05CK) | **Facilitator:** Mr. D. Ganesh

## Features
- Register, display, search (by ID / name), update and delete patients
- View doctors and assign a doctor to a patient
- Book and view appointments
- Admit patients to General / Private / ICU rooms
- Discharge with automatic bill: Rs. 500 consultation + room charge x days
  (General Rs. 1000, Private Rs. 2500, ICU Rs. 5000 per day)
- Hospital summary (patients, admitted, appointments, revenue)
- Data saved to `patients.txt` and `appointments.txt` after every change

## OOP concepts used
Inheritance (`Person` -> `Patient`, `Doctor`), encapsulation, `ArrayList`
collections, file handling (`BufferedReader` / `BufferedWriter`) and exception handling.

## Project structure
```
src/
  Person.java
  Patient.java
  Doctor.java
  Appointment.java
  HospitalManagementSystem.java   (main class)
```

## How to run
Requires JDK 8 or newer (tested on OpenJDK 17). No external libraries.
```bash
cd src
javac *.java
java HospitalManagementSystem
```
Data files are created in the folder you run the program from.
