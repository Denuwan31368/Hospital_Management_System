public class TreatmentStack {

    private TreatmentRecord[] records;
    private int top;      
    private int capacity;

    public TreatmentStack() {
        capacity = 50; 
        records = new TreatmentRecord[capacity];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(TreatmentRecord record) {
        if (top == capacity - 1) {
            resize();
        }
        records[++top] = record;
        System.out.println("Treatment record added to history.");
    }

    public TreatmentRecord pop() {
        if (isEmpty()) {
            System.out.println("Treatment history is empty. Nothing to remove.");
            return null;
        }
        TreatmentRecord removed = records[top];
        records[top] = null;
        top--;
        return removed;
    }

    public void displayStack() {
        if (isEmpty()) {
            System.out.println("   (No completed treatments yet)");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.println("   " + records[i]);
        }
    }

    private void resize() {
        capacity *= 2;
        TreatmentRecord[] newArray = new TreatmentRecord[capacity];
        for (int i = 0; i <= top; i++) {
            newArray[i] = records[i];
        }
        records = newArray;
    }

    public int size() {
        return top + 1;
    }
}
