# Console-Based Note Taking Application
## CCPRGG2L - Final Project

---

## PROJECT OVERVIEW

This is a **menu-driven console application** for managing notes with various categories. The application allows users to create, view, edit, delete, search, and filter notes, with persistent storage through file handling.

---

### Console Application
- **100% console-based** using Scanner for input and System.out for output
- Menu-driven interface with numbered options

### Real-World Scenario
- **Note Taking / Personal Information Management System**
- Similar to the suggested "Student Information System" or "Employee Records"
- Practical application for organizing personal, work, or school notes

### Learning Exhibits 

#### 1. **ARRAYS** 
```java
private static Note[] notes = new Note[100];  // Array to store notes
private static int noteCount = 0;
```
- Used to store multiple Note objects
- Array manipulation in add, delete, and search operations
- Demonstrated in: createNote(), deleteNote(), viewAllNotes(), searchNotes()

#### 2. **FUNCTIONS/METHODS** 
```java
private static void createNote()
private static void viewAllNotes()
private static void editNote()
private static void deleteNote()
private static void searchNotes()
// ... and many more
```
- 15+ custom methods with clear purposes
- Methods with parameters and return values
- Proper method organization and naming

#### 3. **EXCEPTION HANDLING** 
```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
    // File operations
} catch (IOException e) {
    System.out.println("✗ Error saving notes: " + e.getMessage());
}
```
- try-catch blocks for file operations
- InputMismatchException handling for user input
- Multiple exception types handled appropriately
- Demonstrated in: saveNotesToFile(), loadNotesFromFile(), getIntInput()

#### 4. **FILE HANDLING** 
```java
private static void saveNotesToFile()    // Writes to notes_data.txt
private static void loadNotesFromFile()  // Reads from notes_data.txt
```
- Persistent data storage using text files
- BufferedReader and BufferedWriter usage
- Data serialization and deserialization
- Auto-save on exit

#### 5. **ENUMERATIONS (ENUMS)** 
```java
enum NoteCategory {
    PERSONAL("Personal"),
    WORK("Work"),
    SCHOOL("School"),
    IDEAS("Ideas"),
    REMINDERS("Reminders");
}
```
- Custom enum with constructor and methods
- Used throughout the application for note categorization
- Enum values() method for iteration
- Demonstrated in: createNote(), editNote(), filterByCategory()

#### 6. **REGULAR EXPRESSIONS** 
```java
Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
Matcher titleMatcher = pattern.matcher(note.getTitle());
Matcher contentMatcher = pattern.matcher(note.getContent());
```
- Used in search functionality
- Case-insensitive pattern matching
- Pattern and Matcher classes from java.util.regex
- Demonstrated in: searchNotes()

---

## FEATURES

### Main Menu Options:
1. **Create New Note** - Add a new note with title, content, and category
2. **View All Notes** - Display list of all notes with summary
3. **View Note Details** - Show complete note information
4. **Edit Note** - Modify title, content, or category
5. **Delete Note** - Remove a note with confirmation
6. **Search Notes** - Find notes by keyword (uses regex)
7. **Filter by Category** - View notes in specific category
8. **Save Notes** - Manually save to file
9. **Exit** - Save and quit application

### Data Persistence:
- Notes automatically load on startup
- Notes automatically save on exit
- Manual save option available
- Stored in `notes_data.txt`

### User Experience:
- **Screen clearing** - Only shows current context (no clutter)
- **Pause after each action** - Press Enter to continue
- Clean, professional interface
- Cross-platform compatible (Windows, Linux, Mac)

---

## HOW TO COMPILE AND RUN

### Compilation:
```bash
javac NoteTakingApp.java
```

### Execution:
```bash
java NoteTakingApp
```

---

## USAGE GUIDE

### Creating a Note:
1. Select option 1 from main menu
2. Enter a title
3. Type your content (type 'END' on a new line when finished)
4. Select a category (1-5)
5. Note is created and stored

### Searching Notes:
1. Select option 6 from main menu
2. Enter a keyword to search
3. System will find all notes with matching title or content
4. Uses regular expressions for flexible searching

### Editing a Note:
1. Select option 4 from main menu
2. Choose the note number to edit
3. Select what to edit (title/content/category)
4. Make your changes
5. Changes are saved in memory

### File Management:
- Notes are automatically loaded when program starts
- Notes are automatically saved when you exit (option 9)
- You can manually save at any time (option 8)
- Data file: `notes_data.txt`
---

## LEARNING EXHIBIT BREAKDOWN

| Topic | Points in Code | Method Examples |
|-------|----------------|-----------------|
| Arrays | Note[] array, array operations | createNote(), deleteNote(), all view methods |
| Methods | 15+ custom methods | All functionality broken into methods |
| Exception Handling | try-catch blocks, InputMismatchException | saveNotesToFile(), getIntInput() |
| File Handling | Read/write operations | loadNotesFromFile(), saveNotesToFile() |
| Enums | NoteCategory enum | Throughout note management |
| Regular Expressions | Pattern, Matcher | searchNotes() |

---

## SAMPLE OUTPUT

```
╔════════════════════════════════════════════════════╗
║     WELCOME TO NOTE TAKING APPLICATION            ║
╚════════════════════════════════════════════════════╝
✓ Loaded 3 note(s) from file.

==================================================
                    MAIN MENU
==================================================
  1. Create New Note
  2. View All Notes
  3. View Note Details
  4. Edit Note
  5. Delete Note
  6. Search Notes
  7. Filter by Category
  8. Save Notes
  9. Exit
==================================================
Total Notes: 3
==================================================
Enter your choice: 
```

---


## FILES INCLUDED

1. `NoteTakingApp.java` - Main source code
2. `README.md` - This documentation
3. `notes_data.txt` - Data file (created automatically)

---
