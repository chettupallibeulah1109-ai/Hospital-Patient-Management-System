// Appointment.java - Represents one appointment booked for a patient
public class Appointment {
    private int appointmentId;
    private int patientId;
    private String doctorName;
    private String date;
    private String time;

    public Appointment(int appointmentId, int patientId,
                       String doctorName, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }

    public int getAppointmentId() { return appointmentId; }
    public int getPatientId() { return patientId; }
    public String getDoctorName() { return doctorName; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    public void displayAppointment(String patientName) {
        System.out.println("Appointment ID : " + appointmentId);
        System.out.println("Patient        : " + patientName
                + " (ID " + patientId + ")");
        System.out.println("Doctor         : " + doctorName);
        System.out.println("Date           : " + date);
        System.out.println("Time           : " + time);
        System.out.println("----------------------------------------");
    }
}
