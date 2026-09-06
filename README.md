## MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM

## CIT300 – Data Structures and Algorithms Individual Mid Assignment Student ID: 23DA2-0428

## Description of Project

This is a console (text-based) Java application that simulates how a hospital's emergency unit might manage patients. You interact with it through a numbered menu — you type a number, press Enter, and the program does something: registers a patient, adds them to a waiting queue, records a completed treatment, or looks at their visit history.

It exists to demonstrate four data structures that this assignment specifically requires, each one built from scratch (not using Java's built-in ArrayList, Queue, Stack, or LinkedList classes) so the underlying mechanics are visible and explainable.

The key idea: there is only one copy of each Patient object, created when you register them. The BST stores it. When you "add to queue," the Queue doesn't get a copy — it gets a reference to that exact same object. So if you look up a patient's info through the queue or through the BST, you're always looking at the same real data.

## Project Structure
src/
├── Main.java             # Menu-driven console app (entry point)
├── PatientBST.java       # Patient model class + BST (insert/search/delete/traversal)
├── EmergencyQueue.java   # Queue (enqueue/dequeue/display)
├── TreatmentStack.java   # TreatmentRecord model class + Stack (push/pop/display)
└── VisitLinkedList.java  # Visit model class + Singly Linked List (add/remove/search/display)

## User Menu

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

## File-by-file explanation

## PatientBST.java — Patient model + Binary Search Tree

Patient (top of the file) is the simple data-holder ("model") class. Fields: patientId, name, age, contactNumber, medicalCondition, and one VisitLinkedList that belongs to this specific patient. Every Patient object automatically creates its own empty VisitLinkedList the moment it's constructed.

PatientBST stores all Patient objects, using patientId as the key that decides where each one sits in the tree.

How a BST works, in short: every node has at most two children. For any node, everything in its left subtree has a smaller ID, and everything in its right subtree has a larger ID. This is what makes searching fast — at each step you only need to go left or right, never check every single patient.

insert(patient) — walks down from the root, going left or right by comparing IDs, until it finds an empty spot to place the new patient.

search(patientId) — same left/right walk, but stops and returns the patient the moment the ID matches (or returns null if it walks off the tree without finding it).

delete(patientId) — the tricky one. Three cases:

Patient has no children → just remove the node.

Patient has one child → the child takes the deleted node's place.

Patient has two children → find the smallest ID in the right subtree (the "in-order successor"), copy that patient's data up into the node being deleted, then delete that successor node (which is now guaranteed to have at most one child, so it's simple).

displayInOrder() — visits left subtree, then the node itself, then right subtree, recursively. Because of how the BST is organized, this always prints patients from smallest ID to largest.

## EmergencyQueue.java — the waiting line

A Queue follows FIFO: First-In, First-Out — like a real physical queue at a hospital counter. The first patient who joins is the first one treated. It stores references to the same Patient objects that live in the BST — not copies.

Implemented with linked nodes and two pointers: front (the next patient to be treated) and rear (the last patient who joined).

enqueue(patient) — creates a new node, attaches it after rear, moves rear to point to it. If the queue was empty, front and rear both point to this new node.

dequeue() — takes the patient at front, then moves front forward to the next node. If that makes the queue empty, rear is reset to null too.

Both operations only touch the ends of the list, never loop through the whole queue, so they're fast regardless of how many patients are waiting.

## TreatmentStack.java — TreatmentRecord model + Stack

TreatmentRecord (top of the file) is the data-holder: patient ID, name, what was done, and the completion date.

TreatmentStack follows LIFO: Last-In, First-Out — like a stack of plates, you can only take from the top. Stores records in a plain array with a top index tracking the current top (-1 means empty).

push(record) — puts a new record at records[top + 1], then increments top. There are two ways a record gets pushed in this system: automatically, when a queued patient finishes treatment (option 6), and directly, through the standalone push menu option (option 9) which lets you record a completed treatment for any patient on demand.

pop() — reads the record at records[top], clears that slot, then decrements top. Exposed as "Undo last treatment record" (option 10).

If the array runs out of room, resize() doubles its capacity and copies everything over — so it never overflows no matter how many treatments are recorded.

## VisitLinkedList.java — Visit model + Singly Linked List

Visit (top of the file) is the data-holder: visit ID, date, doctor, diagnosis, treatment.

VisitLinkedList is a Singly Linked List: a chain of nodes where each node only knows about the next node (not the previous one — that's what "singly" means, as opposed to "doubly"). Each Patient owns exactly one VisitLinkedList, separate from every other patient's history.

addVisit(visit) — walks to the end of the chain and attaches a new node there.

removeVisit(visitId) — walks the chain looking for a matching ID. Special handling for removing the very first node (head); otherwise it "skips over" the found node by pointing the previous node directly at the one after it.

searchVisit(visitId) — walks the chain from the start until it finds a match or runs out of nodes.
displayVisits() — walks the whole chain, printing each visit.

## Main.java — the menu that ties everything together

## Walking through a typical session

Register a patient (option 1) — enter an ID, name, age, contact, condition. This creates a Patient object and inserts it into the BST.

Display all patients (option 4) — proves the BST insert worked and shows the in-order traversal (sorted by ID).

Add that patient to the emergency queue (option 5) — looks the patient up in the BST by ID, then enqueues the same object into the queue.

Treat the next patient (option 6) — dequeues the patient at the front of the line, asks for treatment details, and automatically pushes a new TreatmentRecord onto the stack.

Push a treatment record directly (option 9) — records a completed treatment for any patient on demand.
Display treatment history (option 8) — shows the stack, most recent treatment first (because of LIFO).

Undo the last treatment record (option 10) — pops the most recently pushed record off the stack.
Add a visit record (option 11) — looks the patient up by ID, then adds a Visit to that specific patient's own linked list.

View that patient's visit history (option 12) — walks their linked list and prints every visit they've ever had.

Search a visit record (option 13) — finds a specific visit by ID.

Remove a visit record (option 14) — deletes a specific visit from that patient's history.

Author

A.M.D.C Pilimathalawwa – 23DA2-0428 CIT300 – Data Structures and Algorithms Mid Assignment