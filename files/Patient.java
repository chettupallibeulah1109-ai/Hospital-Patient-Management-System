// Patient.java - Represents one patient registered in the hospital
public class Patient extends Person {
    private int patientId;
    private String disease;
    private String doctorName;
    private boolean admitted;
    private String roomType;
    private int roomNo;

    public Patient(int patientId, String name, int age, String gender,
                   String phone, String disease) {
        super(name, age, gender, phone);   // call the Person constructor
        this.patientId = patientId;
        this.disease = disease;
        this.doctorName = "Not Assigned";
        this.admitted = false;
        this.roomType = "-";
        this.roomNo = 0;
    }

    public int getPatientId() { return patientId; }
    public String getDisease() { return disease; }
    public String getDoctorName() { return doctorName; }
    public boolean isAdmitted() { return admitted; }
    public String getRoomType() { return roomType; }
    public int getRoomNo() { return roomNo; }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void admit(String roomType, int roomNo) {
        this.roomType = roomType;
        this.roomNo = roomNo;
        this.admitted = true;
    }

    public void discharge() {
        this.admitted = false;
    }

    // Room charge for one day depends on the type of room
    public int roomChargePerDay() {
        if (roomType.equals("Private")) {
            return 2500;
        } else if (roomType.equals("ICU")) {
            return 5000;
        } else {                           // General room
            return 1000;
        }
    }

    // Total bill = consultation fee + (room charge per day x days stayed)
    public int calculateBill(int days) {
        int consultationFee = 500;
        return consultationFee + (roomChargePerDay() * days);
    }

    public void displayPatient() {
        System.out.println("Patient ID : " + patientId);
        displayBasicDetails();
        System.out.println("Disease    : " + disease);
        System.out.println("Doctor     : " + doctorName);
        if (admitted) {
            System.out.println("Status     : Admitted (" + roomType
                    + ", Room No. " + roomNo + ")");
        } else {
            System.out.println("Status     : Not Admitted");
        }
        System.out.println("----------------------------------------");
    }
}
