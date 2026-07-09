import java.util.InputMismatchException;
import java.util.Scanner;

public class LibraryManagementSystem {

    static Scanner scanner = new Scanner(System.in);
    static Library library = new Library();

    public static void main(String[] args) {
        printWelcome();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput("  Enter your choice: ");

            switch (choice) {
                case 1 -> addBook();
                case 2 -> library.viewAllBooks();
                case 3 -> searchBook();
                case 4 -> issueBook();
                case 5 -> returnBook();
                case 6 -> deleteBook();
                case 7 -> printStatistics();
                case 0 -> {
                    System.out.println("\n  Thank you for using Library Management System. Goodbye!");
                    running = false;
                }
                default -> System.out.println("\n  ✖ Invalid choice! Please enter a number between 0-7.");
            }
        }
        scanner.close();
    }

    // ─────────────────────────────────────────────
    // MENU & DISPLAY
    // ─────────────────────────────────────────────
    private static void printWelcome() {
        System.out.println("\n  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║        LIBRARY MANAGEMENT SYSTEM             ║");
        System.out.println("  ║         Console-Based Java Application        ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println("\n  Welcome! " + library.getTotalBooks() + " books loaded from the library.");
    }

    private static void printMenu() {
        System.out.println("\n  ┌────────────────────────────┐");
        System.out.println("  │         MAIN MENU          │");
        System.out.println("  ├────────────────────────────┤");
        System.out.println("  │  1. Add Book               │");
        System.out.println("  │  2. View All Books         │");
        System.out.println("  │  3. Search Book            │");
        System.out.println("  │  4. Issue Book             │");
        System.out.println("  │  5. Return Book            │");
        System.out.println("  │  6. Delete Book            │");
        System.out.println("  │  7. Statistics             │");
        System.out.println("  │  0. Exit                   │");
        System.out.println("  └────────────────────────────┘");
    }

    // ─────────────────────────────────────────────
    // FEATURE HANDLERS
    // ─────────────────────────────────────────────
    private static void addBook() {
        System.out.println("\n  ── ADD NEW BOOK ──────────────────────────────");
        try {
            int id = getIntInput("  Enter Book ID      : ");
            System.out.print("  Enter Book Name    : ");
            String name = scanner.nextLine().trim();
            System.out.print("  Enter Author Name  : ");
            String author = scanner.nextLine().trim();

            if (name.isEmpty() || author.isEmpty()) {
                throw new Exception("Book name and author cannot be empty!");
            }
            library.addBook(id, name, author);
        } catch (Exception e) {
            System.out.println("\n  ✖ Error: " + e.getMessage());
        }
    }

    private static void searchBook() {
        System.out.println("\n  ── SEARCH BOOK ───────────────────────────────");
        System.out.println("  1. Search by Book ID");
        System.out.println("  2. Search by Book Name");
        int choice = getIntInput("  Choose search type: ");

        try {
            if (choice == 1) {
                int id = getIntInput("  Enter Book ID: ");
                library.searchById(id);
            } else if (choice == 2) {
                System.out.print("  Enter Book Name (or part of it): ");
                String name = scanner.nextLine().trim();
                library.searchByName(name);
            } else {
                System.out.println("  ✖ Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("\n  ✖ Error: " + e.getMessage());
        }
    }

    private static void issueBook() {
        System.out.println("\n  ── ISSUE BOOK ────────────────────────────────");
        try {
            int id = getIntInput("  Enter Book ID       : ");
            System.out.print("  Enter Student Name  : ");
            String studentName = scanner.nextLine().trim();

            if (studentName.isEmpty()) {
                throw new Exception("Student name cannot be empty!");
            }
            library.issueBook(id, studentName);
        } catch (Exception e) {
            System.out.println("\n  ✖ Error: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.println("\n  ── RETURN BOOK ───────────────────────────────");
        try {
            int id = getIntInput("  Enter Book ID: ");
            library.returnBook(id);
        } catch (Exception e) {
            System.out.println("\n  ✖ Error: " + e.getMessage());
        }
    }

    private static void deleteBook() {
        System.out.println("\n  ── DELETE BOOK ───────────────────────────────");
        try {
            int id = getIntInput("  Enter Book ID: ");
            System.out.print("  Are you sure you want to delete this book? (yes/no): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("yes")) {
                library.deleteBook(id);
            } else {
                System.out.println("  Delete operation cancelled.");
            }
        } catch (Exception e) {
            System.out.println("\n  ✖ Error: " + e.getMessage());
        }
    }

    private static void printStatistics() {
        System.out.println("\n  ── LIBRARY STATISTICS ────────────────────────");
        System.out.println("  Total Books     : " + library.getTotalBooks());
        System.out.println("  Available Books : " + library.getAvailableCount());
        System.out.println("  Issued Books    : " + library.getIssuedCount());
    }

    // ─────────────────────────────────────────────
    // UTILITY
    // ─────────────────────────────────────────────
    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                return value;
            } catch (InputMismatchException e) {
                System.out.println("  ✖ Invalid input! Please enter a valid number.");
                scanner.nextLine(); // clear bad input
            }
        }
    }
}
