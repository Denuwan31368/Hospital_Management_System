# Hospital Emergency Management System

CIT300 – Data Structures and Algorithms
Individual Mid Assignment
Student ID: 23DA2-0428

## What this project does

This is a console (text-based) Java application that simulates how a
hospital's emergency unit might manage patients. You interact with it
through a numbered menu — you type a number, press Enter, and the program
does something: registers a patient, adds them to a waiting queue, records
a completed treatment, or looks at their visit history.

It exists to demonstrate four data structures that this assignment
specifically requires, each one built from scratch (not using Java's
built-in `ArrayList`, `Queue`, `Stack`, or `LinkedList` classes) so the
underlying mechanics are visible and explainable.

The key idea: there is only **one copy** of each `Patient` object, created
when you register them. The BST stores it. When you "add to queue," the
Queue doesn't get a copy — it gets a reference to that exact same object.
So if you look up a patient's info through the queue or through the BST,
you're always looking at the same real data.

## File-by-file explanation

### `Patient.java` — the patient record

A simple data-holder ("model") class. Fields: `patientId`, `name`, `age`,
`contactNumber`, `medicalCondition`, and one `VisitLinkedList` that belongs
to this specific patient. Every `Patient` object automatically creates its
own empty `VisitLinkedList` the moment it's constructed.

### `PatientBST.java` — the Binary Search Tree

Stores all `Patient` objects, using `patientId` as the key that decides
where each one sits in the tree.

**How a BST works, in short**: 

every node has at most two children. For
  any node, everything in its **left** subtree has a smaller ID, and
  everything in its **right** subtree has a larger ID. This is what makes
  searching fast — at each step you only need to go left or right, never
  check every single patient.

- `insert(patient)` — walks down from the root, going left or right by
  comparing IDs, until it finds an empty spot to place the new patient.

- `search(patientId)` — same left/right walk, but stops and returns the
  patient the moment the ID matches (or returns `null` if it walks off
  the tree without finding it).

- `delete(patientId)` — the tricky one. Three cases:

  1. Patient has no children → just remove the node.

  2. Patient has one child → the child takes the deleted node's place.

  3. Patient has two children → find the *smallest ID in the right
     subtree* (called the "in-order successor"), copy that patient's data
     up into the node being deleted, then delete that successor node
     (which is now guaranteed to have at most one child, so it's simple).

- `displayInOrder()` — visits left subtree, then the node itself, then
  right subtree, recursively. Because of how the BST is organized, this
  always prints patients from smallest ID to largest.

### `EmergencyQueue.java` — the waiting line

A **Queue** follows FIFO: First-In, First-Out — like a real physical
queue at a hospital counter. The first patient who joins is the first one
treated.

- Implemented with **linked nodes** and two pointers: `front` (the next
  patient to be treated) and `rear` (the last patient who joined).

- `enqueue(patient)` — creates a new node, attaches it after `rear`, moves
  `rear` to point to it. If the queue was empty, `front` and `rear` both
  point to this new node.

- `dequeue()` — takes the patient at `front`, then moves `front` forward
  to the next node. If that makes the queue empty, `rear` is reset to
  `null` too.

- Both operations only touch the ends of the list, never loop through
  the whole queue, so they're fast regardless of how many patients are
  waiting.

### `TreatmentStack.java` + `TreatmentRecord.java` — completed treatments

A **Stack** follows LIFO: Last-In, First-Out — like a stack of plates,
you can only take from the top.

- `TreatmentRecord.java` is the data-holder: patient ID, name, what was
  done, and the completion date.

- `TreatmentStack.java` stores these in a plain array with a `top` index
  tracking the current top of the stack (`-1` means empty).

- `push(record)` — puts a new record at `records[top + 1]`, then
  increments `top`.

- `pop()` — reads the record at `records[top]`, clears that slot, then
  decrements `top`.

- If the array runs out of room, `resize()` doubles its capacity and
  copies everything over — so it never overflows no matter how many
  treatments are recorded.

### `Visit.java` + `VisitLinkedList.java` — one patient's visit history

Each `Patient` owns exactly one `VisitLinkedList`, separate from every
other patient's history.

- `Visit.java` is the data-holder: visit ID, date, doctor, diagnosis,
  treatment.

- `VisitLinkedList.java` is a **Singly Linked List**: a chain of nodes
  where each node only knows about the *next* node (not the previous
  one — that's what "singly" means, as opposed to "doubly").

- `addVisit(visit)` — walks to the end of the chain and attaches a new
  node there.

- `removeVisit(visitId)` — walks the chain looking for a matching ID.
  Special handling for removing the very first node (`head`); otherwise
  it "skips over" the found node by pointing the previous node directly
  at the one after it.

- `searchVisit(visitId)` — walks the chain from the start until it finds
  a match or runs out of nodes.

- `displayVisits()` — walks the whole chain, printing each visit.

### `Main.java` — the menu that ties everything together

This is the file you actually run. It:

1. Creates one `PatientBST`, one `EmergencyQueue`, and one `TreatmentStack`
   when the program starts (these live for the whole session).

2. Repeatedly shows the menu and reads what number you type.

3. Calls the matching method — e.g. typing `1` calls `registerPatient()`,
   which asks you for details, builds a `Patient` object, and inserts it
   into the BST.

4. After each action, it prints "Press Enter to return to the menu..." so
   you can read the result at your own pace, then clears the screen with
   blank lines before showing the menu again — this keeps the terminal
   readable instead of an endless unreadable scroll.

5. Typing `0` exits the loop and ends the program.

`readInt()` is a small helper used everywhere a menu asks for a number
(like a patient ID) — it keeps re-asking until you type an actual valid
number, so the program can't crash from bad input like typing letters
where a number was expected.

## Walking through a typical session

1. **Register a patient** (option 1) — enter an ID, name, age, contact,
   condition. This creates a `Patient` object and inserts it into the BST.

2. **Display all patients** (option 4) — proves the BST insert worked and
   shows the in-order traversal (sorted by ID).

3. **Add that patient to the emergency queue** (option 5) — looks the
   patient up in the BST by ID, then enqueues the *same object* into the
   queue.

4. **Treat the next patient** (option 6) — dequeues the patient at the
   front of the line, asks for treatment details, and pushes a new
   `TreatmentRecord` onto the stack.

5. **Display treatment history** (option 8) — shows the stack, most recent
   treatment first (because of LIFO).

6. **Add a visit record** (option 10) — looks the patient up by ID, then
   adds a `Visit` to that specific patient's own linked list.

7. **View that patient's visit history** (option 11) — walks their linked
   list and prints every visit they've ever had.

## Author
A.M.D.C Pilimathalawwa – 23DA2-0428
CIT300 – Data Structures and Algorithms
Mid Assignment