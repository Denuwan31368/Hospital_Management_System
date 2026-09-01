public class PatientBST {

    private static class Node {
        Patient patient;
        Node left, right;

        Node(Patient patient) {
            this.patient = patient;
        }
    }

    private Node root;

    public PatientBST() {
        root = null;
    }

    public void insert(Patient patient) {
        root = insertRec(root, patient);
    }

    private Node insertRec(Node node, Patient patient) {
        if (node == null) {
            return new Node(patient);
        }
        if (patient.getPatientId() < node.patient.getPatientId()) {
            node.left = insertRec(node.left, patient);
        } else if (patient.getPatientId() > node.patient.getPatientId()) {
            node.right = insertRec(node.right, patient);
        } else {
            System.out.println("Patient ID " + patient.getPatientId() + " already exists. Insert skipped.");
        }
        return node;
    }

    public Patient search(int patientId) {
        Node result = searchRec(root, patientId);
        return (result == null) ? null : result.patient;
    }

    private Node searchRec(Node node, int patientId) {
        if (node == null || node.patient.getPatientId() == patientId) {
            return node;
        }
        if (patientId < node.patient.getPatientId()) {
            return searchRec(node.left, patientId);
        }
        return searchRec(node.right, patientId);
    }