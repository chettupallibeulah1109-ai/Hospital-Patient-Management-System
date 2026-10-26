// HospitalManagementSystem.java - Main class: menu, operations and file handling
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HospitalManagementSystem {

    static final String PATIENT_FILE = "patients.txt";
    static final String APPOINTMENT_FILE = "appointments.txt";

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();

    static int nextPatientId = 101;
    static int nextRoomNo = 201;
    static int nextAppointmentId = 1;
    static int totalRevenue = 0;

    public static void main(String[] args) {
        loadSampleDoctors();
        loadData();
        int choice = 0;
        do {
            displayMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine();            // clear the pending newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            } catch (NoSuchElementException e) {
                break;                         // input ended (e.g. scripted input)
            }
            switch (choice) {
                case 1: registerPatient(); break;
                case 2: displayAllPatients(); break;
                case 3: searchPatientById(); break;
                case 4: searchPatientByName(); break;
                case 5: updatePatient(); break;
                case 6: deletePatient(); break;
                case 7: displayAllDoctors(); break;
                case 8: assignDoctor(); break;
                case 9: bookAppointment(); break;
                case 10: viewAppointments(); break;
                case 11: admitPatient(); break;
                case 12: dischargePatient(); break;
                case 13: viewSummary(); break;
                case 14: System.out.println("Thank you. Exiting the system."); break;
                default: System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 14);
    }

    static void displayMenu() {
        System.out.println("\n===== HOSPITAL PATIENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Register New Patient");
        System.out.println("2. Display All Patients");
        System.out.println("3. Search Patient by ID");
        System.out.println("4. Search Patient by Name");
        System.out.println("5. Update Patient Details");
        System.out.println("6. Delete Patient Record");
        System.out.println("7. Display All Doctors");
        System.out.println("8. Assign Doctor to Patient");
        System.out.println("9. Book Appointment");
        System.out.println("10. View All Appointments");
        System.out.println("11. Admit Patient");
        System.out.println("12. Discharge Patient and Generate Bill");
        System.out.println("13. View Hospital Summary");
        System.out.println("14. Exit");
        System.out.print("Enter your choice: ");
    }

    // Reads one integer safely. Returns -1 when the input is not a
    // number, so the calling method can reject it and return to the menu.
    static int readInt() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine();                // clear the pending newline
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine();                // discard the wrong input
            return -1;
        }
    }

    static void loadSampleDoctors() {
        doctors.add(new Doctor(1, "Dr. Ananya Rao", 45, "Female", "9000000001", "Cardiology"));
        doctors.add(new Doctor(2, "Dr. Vikram Reddy", 50, "Male", "9000000002", "General Medicine"));
        doctors.add(new Doctor(3, "Dr. Sneha Patel", 38, "Female", "9000000003", "Pediatrics"));
        doctors.add(new Doctor(4, "Dr. Arjun Mehta", 42, "Male", "9000000004", "Orthopedics"));
    }

    // -------------------- Helper search methods --------------------

    static Patient findPatient(int id) {
        for (Patient p : patients) {
            if (p.getPatientId() == id) {
                return p;
            }
        }
        return null;
    }

    static Doctor findDoctor(int id) {
        for (Doctor d : doctors) {
            if (d.getDoctorId() == id) {
                return d;
            }
        }
        return null;
    }

    // -------------------- Patient management --------------------

    static void registerPatient() {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter age: ");
        String ageText = scanner.nextLine().trim();
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine().trim();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter disease / problem: ");
        String disease = scanner.nextLine().trim();

        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Age must be a number. Patient not registered.");
            return;
        }
        if (age < 1 || age > 120) {
            System.out.println("Invalid age! Patient not registered.");
            return;
        }

        Patient p = new Patient(nextPatientId, name, age, gender, phone, disease);
        patients.add(p);
        System.out.println("Patient registered successfully. Patient ID = " + nextPatientId);
        nextPatientId++;
        saveAll();
    }

    static void displayAllPatients() {
        if (patients.isEmpty()) {
            System.out.println("No patients registered yet.");
            return;
        }
        System.out.println("\n---------- LIST OF PATIENTS ----------");
        for (Patient p : patients) {
            p.displayPatient();
        }
    }

    static void searchPatientById() {
        System.out.print("Enter patient ID to search: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
        } else {
            System.out.println();
            p.displayPatient();
        }
    }

    static void searchPatientByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();
        boolean found = false;
        for (Patient p : patients) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                if (!found) {
                    System.out.println();
                }
                p.displayPatient();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No patient found with name " + name + ".");
        }
    }

    static void updatePatient() {
        System.out.print("Enter patient ID to update: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }
        System.out.print("Enter new phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter new disease / problem: ");
        String disease = scanner.nextLine().trim();
        p.setPhone(phone);
        p.setDisease(disease);
        saveAll();
        System.out.println("Patient details updated successfully.");
    }

    static void deletePatient() {
        System.out.print("Enter patient ID to delete: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }
        if (p.isAdmitted()) {
            System.out.println("Cannot delete an admitted patient. Discharge the patient first.");
            return;
        }
        patients.remove(p);
        // remove the appointments of the deleted patient
        for (int i = appointments.size() - 1; i >= 0; i--) {
            if (appointments.get(i).getPatientId() == id) {
                appointments.remove(i);
            }
        }
        saveAll();
        System.out.println("Patient record deleted successfully.");
    }

    // -------------------- Doctor management --------------------

    static void displayAllDoctors() {
        System.out.println("\n---------- LIST OF DOCTORS ----------");
        System.out.printf("%-4s %-20s %-18s %s%n", "ID", "Name", "Specialization", "Phone");
        for (Doctor d : doctors) {
            d.displayDoctor();
        }
    }

    static void assignDoctor() {
        System.out.print("Enter patient ID: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }
        displayAllDoctors();
        System.out.print("Enter doctor ID to assign: ");
        int docId = readInt();
        if (docId == -1) {
            System.out.println("Invalid input! Doctor ID must be a number.");
            return;
        }
        Doctor d = findDoctor(docId);
        if (d == null) {
            System.out.println("Doctor with ID " + docId + " not found.");
            return;
        }
        p.setDoctorName(d.getName());
        saveAll();
        System.out.println("Doctor " + d.getName() + " assigned to patient " + p.getName() + ".");
    }

    // -------------------- Appointments --------------------

    static void bookAppointment() {
        System.out.print("Enter patient ID: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }
        displayAllDoctors();
        System.out.print("Enter doctor ID: ");
        int docId = readInt();
        if (docId == -1) {
            System.out.println("Invalid input! Doctor ID must be a number.");
            return;
        }
        Doctor d = findDoctor(docId);
        if (d == null) {
            System.out.println("Doctor with ID " + docId + " not found.");
            return;
        }
        System.out.print("Enter appointment date (DD-MM-YYYY): ");
        String date = scanner.nextLine().trim();
        System.out.print("Enter appointment time (e.g. 10:30 AM): ");
        String time = scanner.nextLine().trim();

        Appointment a = new Appointment(nextAppointmentId, id, d.getName(), date, time);
        appointments.add(a);
        System.out.println("Appointment booked successfully. Appointment ID = " + nextAppointmentId);
        nextAppointmentId++;
        saveAll();
    }

    static void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments booked yet.");
            return;
        }
        System.out.println("\n---------- LIST OF APPOINTMENTS ----------");
        for (Appointment a : appointments) {
            Patient p = findPatient(a.getPatientId());
            String patientName = (p == null) ? "Unknown" : p.getName();
            a.displayAppointment(patientName);
        }
    }

    // -------------------- Admission, discharge and billing --------------------

    static void admitPatient() {
        System.out.print("Enter patient ID to admit: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }
        if (p.isAdmitted()) {
            System.out.println("Patient is already admitted.");
            return;
        }
        System.out.println("Select room type: 1. General   2. Private   3. ICU");
        System.out.print("Enter room type choice: ");
        int roomChoice = readInt();
        String roomType;
        if (roomChoice == 1) {
            roomType = "General";
        } else if (roomChoice == 2) {
            roomType = "Private";
        } else if (roomChoice == 3) {
            roomType = "ICU";
        } else {
            System.out.println("Invalid room type choice! Admission cancelled.");
            return;
        }
        p.admit(roomType, nextRoomNo);
        System.out.println("Patient admitted. Room No. = " + nextRoomNo + " (" + roomType + ")");
        nextRoomNo++;
        saveAll();
    }

    static void dischargePatient() {
        System.out.print("Enter patient ID to discharge: ");
        int id = readInt();
        if (id == -1) {
            System.out.println("Invalid input! Patient ID must be a number.");
            return;
        }
        Patient p = findPatient(id);
        if (p == null) {
            System.out.println("Patient with ID " + id + " not found.");
            return;
        }
        if (!p.isAdmitted()) {
            System.out.println("Patient is not admitted, so discharge is not possible.");
            return;
        }
        System.out.print("Enter number of days stayed: ");
        int days = readInt();
        if (days == -1) {
            System.out.println("Invalid input! Number of days must be a number. Discharge cancelled.");
            return;
        }
        if (days <= 0) {
            System.out.println("Invalid number of days! Days must be at least 1. Discharge cancelled.");
            return;
        }
        int roomCharges = p.roomChargePerDay() * days;
        int total = p.calculateBill(days);
        System.out.println("\n---------- DISCHARGE BILL ----------");
        System.out.println("Patient ID       : " + p.getPatientId());
        System.out.println("Name             : " + p.getName());
        System.out.println("Room Type        : " + p.getRoomType() + " (Room No. " + p.getRoomNo() + ")");
        System.out.println("Days Stayed      : " + days);
        System.out.println("Consultation Fee : Rs. 500");
        System.out.println("Room Charges (" + days + " x " + p.roomChargePerDay() + ") : Rs. " + roomCharges);
        System.out.println("Total Bill       : Rs. " + total);
        System.out.println("--------------------------------------");
        p.discharge();
        totalRevenue = totalRevenue + total;
        saveAll();
        System.out.println("Patient discharged successfully.");
    }

    static void viewSummary() {
        int admittedCount = 0;
        for (Patient p : patients) {
            if (p.isAdmitted()) {
                admittedCount++;
            }
        }
        System.out.println("\n---------- HOSPITAL SUMMARY ----------");
        System.out.println("Total Registered Patients : " + patients.size());
        System.out.println("Patients Admitted Now     : " + admittedCount);
        System.out.println("Total Appointments Booked : " + appointments.size());
        System.out.println("Total Revenue Collected   : Rs. " + totalRevenue);
        System.out.println("----------------------------------------");
    }

    // -------------------- File handling --------------------
    // Records are saved in simple text files, one record per line,
    // with the fields separated by the '|' character. The first line
    // of patients.txt stores the counters and the total revenue.

    static void saveAll() {
        savePatients();
        saveAppointments();
    }

    static void savePatients() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENT_FILE))) {
            bw.write("# META | " + nextPatientId + " | " + nextRoomNo + " | "
                    + nextAppointmentId + " | " + totalRevenue);
            bw.newLine();
            for (Patient p : patients) {
                bw.write(p.getPatientId() + " | " + p.getName() + " | " + p.getAge()
                        + " | " + p.getGender() + " | " + p.getPhone() + " | " + p.getDisease()
                        + " | " + p.getDoctorName() + " | " + p.isAdmitted()
                        + " | " + p.getRoomType() + " | " + p.getRoomNo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save patient data.");
        }
    }

    static void saveAppointments() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(APPOINTMENT_FILE))) {
            for (Appointment a : appointments) {
                bw.write(a.getAppointmentId() + " | " + a.getPatientId() + " | "
                        + a.getDoctorName() + " | " + a.getDate() + " | " + a.getTime());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save appointment data.");
        }
    }

    static void loadData() {
        File pf = new File(PATIENT_FILE);
        if (pf.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(pf))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] f = line.split("\\|");
                    for (int i = 0; i < f.length; i++) {
                        f[i] = f[i].trim();
                    }
                    if (f[0].equals("# META")) {
                        nextPatientId = Integer.parseInt(f[1]);
                        nextRoomNo = Integer.parseInt(f[2]);
                        nextAppointmentId = Integer.parseInt(f[3]);
                        totalRevenue = Integer.parseInt(f[4]);
                    } else if (f.length >= 10) {
                        Patient p = new Patient(Integer.parseInt(f[0]), f[1],
                                Integer.parseInt(f[2]), f[3], f[4], f[5]);
                        p.setDoctorName(f[6]);
                        if (Boolean.parseBoolean(f[7])) {
                            p.admit(f[8], Integer.parseInt(f[9]));
                        }
                        patients.add(p);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Warning: could not read patient data file.");
            }
        }

        File af = new File(APPOINTMENT_FILE);
        if (af.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(af))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }
                    String[] f = line.split("\\|");
                    for (int i = 0; i < f.length; i++) {
                        f[i] = f[i].trim();
                    }
                    if (f.length >= 5) {
                        appointments.add(new Appointment(Integer.parseInt(f[0]),
                                Integer.parseInt(f[1]), f[2], f[3], f[4]));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Warning: could not read appointment data file.");
            }
        }
    }
}
