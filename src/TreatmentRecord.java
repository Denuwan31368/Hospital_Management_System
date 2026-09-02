public class TreatmentRecord {
    private int patientId;
    private String patientName;
    private String treatmentDetails;
    private String completedDate;

    public TreatmentRecord(int patientId, String patientName, String treatmentDetails, String completedDate) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.treatmentDetails = treatmentDetails;
        this.completedDate = completedDate;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId +
                " | Name: " + patientName +
                " | Treatment: " + treatmentDetails +
                " | Completed: " + completedDate;
    }
}
