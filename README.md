# Mini Hospital Emergency Management System

**Course:** CIT300 – Data Structures and Algorithms
**Assignment:** Individual Mid Assignment
**Student ID:** 23DA2-0428
**Author:** A.M.D.C Pilimathalawwa

---

## Description

This is a console (text-based) Java application that simulates how a
hospital's emergency unit might manage patients. You interact with it
through a numbered menu — you type a number, press Enter, and the
program does something: registers a patient, adds them to a waiting
queue, records a completed treatment, or looks at their visit history.

It exists to demonstrate four data structures that this assignment
specifically requires, each one built **from scratch** (not using
Java's built-in `ArrayList`, `Queue`, `Stack`, or `LinkedList` classes)
so the underlying mechanics are visible and explainable.

> **Key idea:** there is only one copy of each `Patient` object, created
> when you register them. The BST stores it. When you "add to queue,"
> the Queue doesn't get a copy — it gets a reference to that exact same
> object. So whether you look up a patient through the queue or the
> BST, you're always looking at the same real data.

---

## Project Structure

```
src/
├── Main.java             # Menu-driven console app (entry point)
├── PatientBST.java        # Patient model class + BST
├── EmergencyQueue.java    # Queue (enqueue / dequeue / display)
├── TreatmentStack.java    # TreatmentRecord model class + Stack
└── VisitLinkedList.java   # Visit model class + Singly Linked List
```

## User Menu

```
     MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM
=====================================================

--- Patient Records (BST) ---
 1. Register new patient
 2. Search patient by ID
 3. Delete patient
 4. Display all patients (in-order by ID)

--- Emergency Queue ---
 5. Add patient to emergency queue
 6. Treat next patient (dequeue)
 7. Display waiting queue

--- Treatment History (Stack) ---
 8. Display treatment history
 9. Add a completed treatment record (push)
10. Undo last treatment record (pop)

--- Patient Visit History (Linked List) ---
11. Add visit record to a patient
12. View a patient's visit history
13. Search a visit record
14. Remove a visit record

 0. Exit

```

---

## File-by-File Explanation

### `PatientBST.java` — Patient model + Binary Search Tree

**`Patient`** (top of the file) is the simple data-holder class.
Fields: `patientId`, `name`, `age`, `contactNumber`,
`medicalCondition`, and one `VisitLinkedList` that belongs to this
specific patient. Every `Patient` automatically creates its own empty
`VisitLinkedList` the moment it's constructed.

**`PatientBST`** stores all `Patient` objects, keyed by `patientId`.

> **How a BST works, in short:** every node has at most two children.
> For any node, everything in its left subtree has a smaller ID, and
> everything in its right subtree has a larger ID — this is what makes
> searching fast, since you only need to go left or right at each step.

| Method | What it does |
|---|---|
| `insert(patient)` | Walks down from the root, comparing IDs, until it finds an empty spot |
| `search(patientId)` | Same left/right walk, returns the match or `null` |
| `delete(patientId)` | Handles 3 cases: no children, one child, two children (uses the in-order successor for the hardest case) |
| `displayInOrder()` | Recursively visits left → node → right, always prints patients sorted by ID |

### `EmergencyQueue.java` — the waiting line

A Queue follows **FIFO**: First-In, First-Out — like a real physical
queue. Stores references to the same `Patient` objects that live in
the BST, not copies.

| Method | What it does |
|---|---|
| `enqueue(patient)` | Attaches a new node after `rear`, moves `rear` to it |
| `dequeue()` | Removes and returns the patient at `front`, advances `front` |

Both operations only touch the ends of the list — never loop through
the whole queue — so they're fast no matter how many patients are
waiting.

### `TreatmentStack.java` — TreatmentRecord model + Stack

**`TreatmentRecord`** (top of the file) is the data-holder: patient ID,
name, what was done, and the completion date.

**`TreatmentStack`** follows **LIFO**: Last-In, First-Out — like a
stack of plates, you can only take from the top. Stores records in a
resizable array with a `top` index (`-1` means empty).

| Method | What it does |
|---|---|
| `push(record)` | Adds a record above `top`. Happens automatically when a queued patient finishes treatment (option 6), **and** directly through the standalone push option (option 9) |
| `pop()` | Removes and returns the record at `top` — "Undo last treatment record" (option 10) |

### `VisitLinkedList.java` — Visit model + Singly Linked List

**`Visit`** (top of the file) is the data-holder: visit ID, date,
doctor, diagnosis, treatment.

**`VisitLinkedList`** is a Singly Linked List: a chain of nodes where
each node only points to the *next* one. Each `Patient` owns exactly
one instance, separate from every other patient's history.

| Method | What it does |
|---|---|
| `addVisit(visit)` | Walks to the end of the chain and attaches a new node |
| `removeVisit(visitId)` | Finds a matching ID and skips over it by re-linking the previous node |
| `searchVisit(visitId)` | Walks the chain until it finds a match |
| `displayVisits()` | Walks and prints the whole chain |

### `Main.java` — the menu that ties everything together

- Creates one `PatientBST`, one `EmergencyQueue`, and one
  `TreatmentStack` when the program starts.
- Repeatedly shows the menu and reads what number you type, calling the
  matching method.
- After each action, prints **"Press Enter to return to the menu..."**
  so results stay visible, then clears the screen with blank lines
  before showing a fresh menu.
- Typing `0` exits the program.
- `readInt()` is a helper that keeps re-asking until valid numeric
  input is given, so bad input (like letters) can't crash the program.

---
## Author

**A.M.D.C Pilimathalawwa** – 23DA2-0428
CIT300 – Data Structures and Algorithms, Mid Assignment