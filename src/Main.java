import java.util.Scanner;
public class Main {

    private static PatientBST patientBST = new PatientBST();
    private static EmergencyQueue emergencyQueue = new EmergencyQueue();
    private static TreatmentStack treatmentStack = new TreatmentStack();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData(); 
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> searchPatient();
                case 3 -> deletePatient();
                case 4 -> patientBST.displayInOrder();
                case 5 -> addToEmergencyQueue();
                case 6 -> treatNextPatient();
                case 7 -> emergencyQueue.displayQueue();
                case 8 -> treatmentStack.displayStack();
                case 9 -> undoLastTreatment();
                case 10 -> addVisitHistory();
                case 11 -> viewVisitHistory();
                case 12 -> searchVisit();
                case 13 -> removeVisit();
                case 0 -> {
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n===== MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM =====");
        System.out.println(" --- Patient Records (BST) ---");
        System.out.println(" 1. Register new patient");
        System.out.println(" 2. Search patient by ID");
        System.out.println(" 3. Delete patient");
        System.out.println(" 4. Display all patients (in-order by ID)");
        System.out.println(" --- Emergency Queue ---");
        System.out.println(" 5. Add patient to emergency queue");
        System.out.println(" 6. Treat next patient (dequeue)");
        System.out.println(" 7. Display waiting queue");
        System.out.println(" --- Treatment History (Stack) ---");
        System.out.println(" 8. Display treatment history");
        System.out.println(" 9. Undo last treatment record (pop)");
        System.out.println(" --- Patient Visit History (Linked List) ---");
        System.out.println("10. Add visit record to a patient");
        System.out.println("11. View a patient's visit history");
        System.out.println("12. Search a visit record");
        System.out.println("13. Remove a visit record");
        System.out.println(" 0. Exit");
    }

    private static void registerPatient() {
        int id = readInt("Enter Patient ID: ");
        if (patientBST.search(id) != null) {
            System.out.println("A patient with this ID already exists.");
            return;
        }
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        int age = readInt("Enter Age: ");
        System.out.print("Enter Contact Number: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine();

        Patient patient = new Patient(id, name, age, contact, condition);
        patientBST.insert(patient);
        System.out.println("Patient registered successfully.");
    }

    private static void searchPatient() {
        int id = readInt("Enter Patient ID to search: ");
        Patient found = patientBST.search(id);
        if (found == null) {
            System.out.println("No patient found with ID " + id);
        } else {
            System.out.println("Found: " + found);
        }
    }

    private static void deletePatient() {
        int id = readInt("Enter Patient ID to delete: ");
        boolean removed = patientBST.delete(id);
        System.out.println(removed ? "Patient deleted." : "No patient found with that ID.");
    }

    //QUEUE OPERATIONS

    private static void addToEmergencyQueue() {
        int id = readInt("Enter Patient ID to add to queue: ");
        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("Patient not found. Register the patient first (option 1).");
            return;
        }
        emergencyQueue.enqueue(patient);
    }

    private static void treatNextPatient() {
        Patient patient = emergencyQueue.dequeue();
        if (patient == null) {
            return; 
        }
        System.out.println("Now treating: " + patient);

        System.out.print("Enter treatment details: ");
        String details = scanner.nextLine();
        System.out.print("Enter completion date (e.g. 2026-08-31): ");
        String date = scanner.nextLine();

        TreatmentRecord record = new TreatmentRecord(patient.getPatientId(), patient.getName(), details, date);
        treatmentStack.push(record);
    }

    //  STACK OPERATIONS

    private static void undoLastTreatment() {
        TreatmentRecord removed = treatmentStack.pop();
        if (removed != null) {
            System.out.println("Removed most recent treatment record: " + removed);
        }
    }

    // LINKED LIST OPERATIONS

    private static void addVisitHistory() {
        int id = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        int visitId = readInt("Enter Visit ID: ");
        System.out.print("Enter Visit Date: ");
        String date = scanner.nextLine();
        System.out.print("Enter Doctor Name: ");
        String doctor = scanner.nextLine();
        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Enter Treatment: ");
        String treatment = scanner.nextLine();

        patient.getVisitHistory().addVisit(new Visit(visitId, date, doctor, diagnosis, treatment));
        System.out.println("Visit record added.");
    }

    private static void viewVisitHistory() {
        int id = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.println("Visit history for " + patient.getName() + ":");
        patient.getVisitHistory().displayVisits();
    }

    private static void searchVisit() {
        int id = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        int visitId = readInt("Enter Visit ID to search: ");
        Visit visit = patient.getVisitHistory().searchVisit(visitId);
        System.out.println(visit == null ? "Visit not found." : "Found: " + visit);
    }

    private static void removeVisit() {
        int id = readInt("Enter Patient ID: ");
        Patient patient = patientBST.search(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        int visitId = readInt("Enter Visit ID to remove: ");
        boolean removed = patient.getVisitHistory().removeVisit(visitId);
        System.out.println(removed ? "Visit removed." : "Visit not found.");
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); 
        return value;
    }

    private static void loadSampleData() {
        Patient p1 = new Patient(101, "Nimal Perera", 34, "0771234567", "Fractured arm");
        Patient p2 = new Patient(102, "Kamala Silva", 28, "0777654321", "High fever");
        Patient p3 = new Patient(103, "Ruwan Fernando", 45, "0712345678", "Chest pain");

        patientBST.insert(p1);
        patientBST.insert(p2);
        patientBST.insert(p3);

        p1.getVisitHistory().addVisit(new Visit(1, "2026-01-10", "Dr. Silva", "Sprain", "Rest and ice pack"));
        p2.getVisitHistory().addVisit(new Visit(1, "2026-02-15", "Dr. Perera", "Flu", "Paracetamol"));

        emergencyQueue.enqueue(p1);
        emergencyQueue.enqueue(p3);
    }
}
