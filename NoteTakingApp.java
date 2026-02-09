import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Console-based Note Taking Application
 * Demonstrates: Arrays, Methods, Exception Handling, File Handling, Enums, Regular Expressions
 * 
 * @author Karl Bondoc, Sean Rhed Gaquit, Jhon Brenzo Libatique, Karl Rarang Akashi
 * @version 1.0
 */
public class NoteTakingApp {
    // Arrays to store notes
    private static Note[] notes = new Note[100];
    private static int noteCount = 0;
    
    private static Scanner scanner = new Scanner(System.in);
    private static final String DATA_FILE = "notes_data.txt";
    
    /**
     * Clear the console screen
     * Works on Windows, Linux, and Mac
     */
    private static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");
            
            if (os.contains("Windows")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Linux and Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print blank lines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    
    /**
     * Pause and wait for user to press Enter
     */
    private static void pauseScreen() {
        System.out.print("\nPress Enter to continue...");
        try {
            System.in.read();
            scanner.nextLine(); // Clear buffer
        } catch (Exception e) {
            // Ignore
        }
    }
    
    /**
     * Enum for Note Categories
     * Enumerations
     */
    enum NoteCategory {
        PERSONAL("Personal"),
        WORK("Work"),
        SCHOOL("School"),
        IDEAS("Ideas"),
        REMINDERS("Reminders");
        
        private final String displayName;
        
        NoteCategory(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Inner class to represent a Note
     */
    static class Note {
        private String title;
        private String content;
        private NoteCategory category;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;
        
        public Note(String title, String content, NoteCategory category) {
            this.title = title;
            this.content = content;
            this.category = category;
            this.createdDate = LocalDateTime.now();
            this.modifiedDate = LocalDateTime.now();
        }
        
        // Getters and setters
        public String getTitle() { return title; }
        public void setTitle(String title) { 
            this.title = title;
            this.modifiedDate = LocalDateTime.now();
        }
        
        public String getContent() { return content; }
        public void setContent(String content) { 
            this.content = content;
            this.modifiedDate = LocalDateTime.now();
        }
        
        public NoteCategory getCategory() { return category; }
        public void setCategory(NoteCategory category) { 
            this.category = category;
            this.modifiedDate = LocalDateTime.now();
        }
        
        public LocalDateTime getCreatedDate() { return createdDate; }
        public LocalDateTime getModifiedDate() { return modifiedDate; }
        
        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return String.format("[%s] %s - Created: %s", 
                category.getDisplayName(), 
                title, 
                createdDate.format(formatter));
        }
        
        public String toFileFormat() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return title + "|" + content.replace("\n", "\\n") + "|" + 
                   category.name() + "|" + createdDate.format(formatter) + "|" + 
                   modifiedDate.format(formatter);
        }
    }
    
    /**
     * Main method
     * Methods/Functions
     */
    public static void main(String[] args) {
        clearScreen();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║     WELCOME TO NOTE TAKING APPLICATION             ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        // Load existing notes from file
        loadNotesFromFile();
        pauseScreen();
        
        boolean running = true;
        while (running) {
            try {
                clearScreen();
                displayMainMenu();
                int choice = getIntInput("Enter your choice: ");
                clearScreen();
                
                switch (choice) {
                    case 1:
                        createNote();
                        pauseScreen();
                        break;
                    case 2:
                        viewAllNotes();
                        pauseScreen();
                        break;
                    case 3:
                        viewNoteDetails();
                        pauseScreen();
                        break;
                    case 4:
                        editNote();
                        pauseScreen();
                        break;
                    case 5:
                        deleteNote();
                        pauseScreen();
                        break;
                    case 6:
                        searchNotes();
                        pauseScreen();
                        break;
                    case 7:
                        filterByCategory();
                        pauseScreen();
                        break;
                    case 8:
                        saveNotesToFile();
                        System.out.println("\n✓ Notes saved successfully!");
                        pauseScreen();
                        break;
                    case 9:
                        clearScreen();
                        saveNotesToFile();
                        System.out.println("\n✓ Thank you for using Note Taking App!");
                        running = false;
                        break;
                    default:
                        System.out.println("\n✗ Invalid choice! Please try again.");
                        pauseScreen();
                }
            } catch (Exception e) {
                System.out.println("\n✗ Error: " + e.getMessage());
                pauseScreen();
                scanner.nextLine(); // Clear buffer
            }
        }
        
        scanner.close();
    }
    
    /**
     * Display the main menu
     * Methods/Functions
     */
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                    MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("  1. Create New Note");
        System.out.println("  2. View All Notes");
        System.out.println("  3. View Note Details");
        System.out.println("  4. Edit Note");
        System.out.println("  5. Delete Note");
        System.out.println("  6. Search Notes");
        System.out.println("  7. Filter by Category");
        System.out.println("  8. Save Notes");
        System.out.println("  9. Exit");
        System.out.println("=".repeat(50));
        System.out.printf("Total Notes: %d\n", noteCount);
        System.out.println("=".repeat(50));
    }
    
    /**
     * Create a new note
     * Arrays, Methods, Enums
     */
    private static void createNote() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              CREATE NEW NOTE");
        System.out.println("=".repeat(50));
        
        if (noteCount >= notes.length) {
            System.out.println("✗ Note storage is full! Cannot create more notes.");
            return;
        }
        
        scanner.nextLine(); // Clear buffer
        
        System.out.print("Enter note title: ");
        String title = scanner.nextLine();
        
        if (title.trim().isEmpty()) {
            System.out.println("✗ Title cannot be empty!");
            return;
        }
        
        System.out.println("\nEnter note content (type 'END' on a new line to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            content.append(line).append("\n");
        }
        
        if (content.toString().trim().isEmpty()) {
            System.out.println("✗ Content cannot be empty!");
            return;
        }
        
        // Display category options
        System.out.println("\nSelect Category:");
        NoteCategory[] categories = NoteCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("  %d. %s\n", i + 1, categories[i].getDisplayName());
        }
        
        int categoryChoice = getIntInput("Enter category number: ");
        if (categoryChoice < 1 || categoryChoice > categories.length) {
            System.out.println("✗ Invalid category! Defaulting to PERSONAL.");
            categoryChoice = 1;
        }
        
        NoteCategory selectedCategory = categories[categoryChoice - 1];
        Note newNote = new Note(title, content.toString(), selectedCategory);
        notes[noteCount++] = newNote;
        
        System.out.println("\n✓ Note created successfully!");
        System.out.println("  Title: " + title);
        System.out.println("  Category: " + selectedCategory.getDisplayName());
    }
    
    /**
     * View all notes in a list
     * Arrays, Methods
     */
    private static void viewAllNotes() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                ALL NOTES");
        System.out.println("=".repeat(50));
        
        if (noteCount == 0) {
            System.out.println("  No notes available.");
            return;
        }
        
        for (int i = 0; i < noteCount; i++) {
            System.out.printf("  %d. %s\n", i + 1, notes[i].toString());
        }
    }
    
    /**
     * View detailed information of a specific note
     * Arrays, Methods
     */
    private static void viewNoteDetails() {
        if (noteCount == 0) {
            System.out.println("\n✗ No notes available.");
            return;
        }
        
        viewAllNotes();
        int index = getIntInput("\nEnter note number to view: ") - 1;
        
        if (index < 0 || index >= noteCount) {
            System.out.println("✗ Invalid note number!");
            return;
        }
        
        Note note = notes[index];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("               NOTE DETAILS");
        System.out.println("=".repeat(50));
        System.out.println("Title: " + note.getTitle());
        System.out.println("Category: " + note.getCategory().getDisplayName());
        System.out.println("Created: " + note.getCreatedDate().format(formatter));
        System.out.println("Modified: " + note.getModifiedDate().format(formatter));
        System.out.println("-".repeat(50));
        System.out.println("Content:");
        System.out.println(note.getContent());
        System.out.println("=".repeat(50));
    }
    
    /**
     * Edit an existing note
     * Arrays, Methods, Enums
     */
    private static void editNote() {
        if (noteCount == 0) {
            System.out.println("\n✗ No notes available.");
            return;
        }
        
        viewAllNotes();
        int index = getIntInput("\nEnter note number to edit: ") - 1;
        
        if (index < 0 || index >= noteCount) {
            System.out.println("✗ Invalid note number!");
            return;
        }
        
        Note note = notes[index];
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("               EDIT NOTE");
        System.out.println("=".repeat(50));
        System.out.println("  1. Edit Title");
        System.out.println("  2. Edit Content");
        System.out.println("  3. Edit Category");
        System.out.println("  4. Cancel");
        System.out.println("=".repeat(50));
        
        int choice = getIntInput("Enter your choice: ");
        scanner.nextLine(); // Clear buffer
        
        switch (choice) {
            case 1:
                System.out.print("Enter new title: ");
                String newTitle = scanner.nextLine();
                if (!newTitle.trim().isEmpty()) {
                    note.setTitle(newTitle);
                    System.out.println("✓ Title updated successfully!");
                }
                break;
            case 2:
                System.out.println("Enter new content (type 'END' on a new line to finish):");
                StringBuilder newContent = new StringBuilder();
                String line;
                while (!(line = scanner.nextLine()).equals("END")) {
                    newContent.append(line).append("\n");
                }
                if (!newContent.toString().trim().isEmpty()) {
                    note.setContent(newContent.toString());
                    System.out.println("✓ Content updated successfully!");
                }
                break;
            case 3:
                System.out.println("Select new category:");
                NoteCategory[] categories = NoteCategory.values();
                for (int i = 0; i < categories.length; i++) {
                    System.out.printf("  %d. %s\n", i + 1, categories[i].getDisplayName());
                }
                int catChoice = getIntInput("Enter category number: ");
                if (catChoice >= 1 && catChoice <= categories.length) {
                    note.setCategory(categories[catChoice - 1]);
                    System.out.println("✓ Category updated successfully!");
                }
                break;
            case 4:
                System.out.println("Edit cancelled.");
                break;
            default:
                System.out.println("✗ Invalid choice!");
        }
    }
    
    /**
     * Delete a note
     * Arrays, Methods
     */
    private static void deleteNote() {
        if (noteCount == 0) {
            System.out.println("\n✗ No notes available.");
            return;
        }
        
        viewAllNotes();
        int index = getIntInput("\nEnter note number to delete: ") - 1;
        
        if (index < 0 || index >= noteCount) {
            System.out.println("✗ Invalid note number!");
            return;
        }
        
        System.out.print("Are you sure you want to delete this note? (yes/no): ");
        scanner.nextLine(); // Clear buffer
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            // Shift remaining notes left
            for (int i = index; i < noteCount - 1; i++) {
                notes[i] = notes[i + 1];
            }
            notes[noteCount - 1] = null;
            noteCount--;
            System.out.println("✓ Note deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    /**
     * Search notes by keyword using regular expressions
     * Arrays, Methods, Regular Expressions
     */
    private static void searchNotes() {
        if (noteCount == 0) {
            System.out.println("\n✗ No notes available.");
            return;
        }
        
        scanner.nextLine(); // Clear buffer
        System.out.print("\nEnter search keyword: ");
        String keyword = scanner.nextLine();
        
        if (keyword.trim().isEmpty()) {
            System.out.println("✗ Search keyword cannot be empty!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("             SEARCH RESULTS");
        System.out.println("=".repeat(50));
        
        // Create regex pattern (case-insensitive)
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        boolean found = false;
        
        for (int i = 0; i < noteCount; i++) {
            Note note = notes[i];
            Matcher titleMatcher = pattern.matcher(note.getTitle());
            Matcher contentMatcher = pattern.matcher(note.getContent());
            
            if (titleMatcher.find() || contentMatcher.find()) {
                System.out.printf("  %d. %s\n", i + 1, note.toString());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("  No notes found matching '" + keyword + "'");
        }
    }
    
    /**
     * Filter notes by category
     * Arrays, Methods, Enums
     */
    private static void filterByCategory() {
        if (noteCount == 0) {
            System.out.println("\n✗ No notes available.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           FILTER BY CATEGORY");
        System.out.println("=".repeat(50));
        
        NoteCategory[] categories = NoteCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("  %d. %s\n", i + 1, categories[i].getDisplayName());
        }
        
        int choice = getIntInput("Select category: ");
        
        if (choice < 1 || choice > categories.length) {
            System.out.println("✗ Invalid category!");
            return;
        }
        
        NoteCategory selectedCategory = categories[choice - 1];
        boolean found = false;
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  Notes in category: " + selectedCategory.getDisplayName());
        System.out.println("=".repeat(50));
        
        for (int i = 0; i < noteCount; i++) {
            if (notes[i].getCategory() == selectedCategory) {
                System.out.printf("  %d. %s\n", i + 1, notes[i].toString());
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("  No notes in this category.");
        }
    }
    
    /**
     * Save notes to file
     * File Handling, Exception Handling, Arrays
     */
    private static void saveNotesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            writer.write(noteCount + "\n");
            
            for (int i = 0; i < noteCount; i++) {
                writer.write(notes[i].toFileFormat() + "\n");
            }
        } catch (IOException e) {
            System.out.println("✗ Error saving notes: " + e.getMessage());
        }
    }
    
    /**
     * Load notes from file
     * File Handling, Exception Handling, Arrays, Enums
     */
    private static void loadNotesFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // No file to load
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String countLine = reader.readLine();
            if (countLine == null) return;
            
            int savedCount = Integer.parseInt(countLine);
            noteCount = 0;
            
            for (int i = 0; i < savedCount; i++) {
                String line = reader.readLine();
                if (line == null) break;
                
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    String title = parts[0];
                    String content = parts[1].replace("\\n", "\n");
                    NoteCategory category = NoteCategory.valueOf(parts[2]);
                    
                    Note note = new Note(title, content, category);
                    
                    // Parse dates
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    note.createdDate = LocalDateTime.parse(parts[3], formatter);
                    note.modifiedDate = LocalDateTime.parse(parts[4], formatter);
                    
                    notes[noteCount++] = note;
                }
            }
            
            System.out.println("✓ Loaded " + noteCount + " note(s) from file.");
        } catch (IOException e) {
            System.out.println("✗ Error loading notes: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error parsing notes file: " + e.getMessage());
        }
    }
    
    /**
     * Get integer input with exception handling
     * Exception Handling, Methods
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("✗ Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}
