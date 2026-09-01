public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String contactNumber;
    private String medicalCondition;
    private VisitLinkedList visitHistory; 

    public Patient(int patientId, String name, int age, String contactNumber, String medicalCondition) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
        this.medicalCondition = medicalCondition;
        this.visitHistory = new VisitLinkedList();
    }

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public VisitLinkedList getVisitHistory() {
        return visitHistory;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId +
                " | Name: " + name +
                " | Age: " + age +
                " | Contact: " + contactNumber +
                " | Condition: " + medicalCondition;
    }
}
