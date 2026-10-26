// Doctor.java - Represents one doctor available in the hospital
public class Doctor extends Person {
    private int doctorId;
    private String specialization;

    public Doctor(int doctorId, String name, int age, String gender,
                  String phone, String specialization) {
        super(name, age, gender, phone);
        this.doctorId = doctorId;
        this.specialization = specialization;
    }

    public int getDoctorId() { return doctorId; }
    public String getSpecialization() { return specialization; }

    public void displayDoctor() {
        System.out.printf("%-4d %-20s %-18s %s%n",
                doctorId, getName(), specialization, getPhone());
    }
}
