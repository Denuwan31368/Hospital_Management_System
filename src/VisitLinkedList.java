public class VisitLinkedList {

    private static class Node {
        Visit data;
        Node next;

        Node(Visit data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;   
    private int size;    

    public VisitLinkedList() {
        head = null;
        size = 0;
    }

    public void addVisit(Visit visit) {
        Node newNode = new Node(visit);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public boolean removeVisit(int visitId) {
        if (head == null) {
            return false;
        }

        if (head.data.getVisitId() == visitId) {
            head = head.next;
            size--;
            return true;
        }

        Node prev = head;
        Node current = head.next;
        while (current != null) {
            if (current.data.getVisitId() == visitId) {
                prev.next = current.next; 
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false; 
    }

    public Visit searchVisit(int visitId) {
        Node current = head;
        while (current != null) {
            if (current.data.getVisitId() == visitId) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public void displayVisits() {
        if (head == null) {
            System.out.println("   (No visit history yet)");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.println("   " + current.data);
            current = current.next;
        }
    }

    public int size() {
        return size;
    }
}
