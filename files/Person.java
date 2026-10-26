// Person.java - Base class holding details common to patients and doctors
public class Person {
    private String name;
    private int age;
    private String gender;
    private String phone;

    public Person(String name, int age, String gender, String phone) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public void displayBasicDetails() {
        System.out.println("Name   : " + name);
        System.out.println("Age    : " + age);
        System.out.println("Gender : " + gender);
        System.out.println("Phone  : " + phone);
    }
}
