import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
        // Pre-loaded sample books
        books.add(new Book(101, "Java Programming", "James Gosling"));
        books.add(new Book(102, "Data Structures", "Mark Allen Weiss"));
        books.add(new Book(103, "Clean Code", "Robert C. Martin"));
        books.add(new Book(104, "Design Patterns", "Gang of Four"));
        books.add(new Book(105, "The Pragmatic Programmer", "Andrew Hunt"));
    }

    // ─────────────────────────────────────────────
    // 1. ADD BOOK
    // ─────────────────────────────────────────────
    public void addBook(int id, String name, String author) throws Exception {
        for (Book b : books) {
            if (b.getBookId() == id) {
                throw new Exception("Book ID " + id + " already exists!");
            }
        }
        books.add(new Book(id, name, author));
        System.out.println("\n  ✔ Book \"" + name + "\" added successfully!");
    }

    // ─────────────────────────────────────────────
    // 2. VIEW ALL BOOKS
    // ─────────────────────────────────────────────
    public void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("\n  No books available in the library.");
            return;
        }
        printHeader();
        for (Book b : books) {
            System.out.println(b.toString());
        }
        printFooter();
        System.out.println("  Total Books: " + books.size());
    }

    // ─────────────────────────────────────────────
    // 3. SEARCH BOOK
    // ─────────────────────────────────────────────
    public void searchById(int id) throws Exception {
        Book found = findById(id);
        System.out.println("\n  Book Found:");
        printHeader();
        System.out.println(found.toString());
        printFooter();
    }

    public void searchByName(String name) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.getBookName().toLowerCase().contains(name.toLowerCase())) {
                results.add(b);
            }
        }
        if (results.isEmpty()) {
            System.out.println("\n  No books found with name containing: \"" + name + "\"");
        } else {
            System.out.println("\n  Search Results (" + results.size() + " found):");
            printHeader();
            for (Book b : results) {
                System.out.println(b.toString());
            }
            printFooter();
        }
    }

    // ─────────────────────────────────────────────
    // 4. ISSUE BOOK
    // ─────────────────────────────────────────────
    public void issueBook(int id, String studentName) throws Exception {
        Book book = findById(id);
        if (book.getAvailabilityStatus().equals("Issued")) {
            throw new Exception("Book is already issued to: " + book.getIssuedTo());
        }
        book.setAvailabilityStatus("Issued");
        book.setIssuedTo(studentName);
        System.out.println("\n  ✔ Book \"" + book.getBookName() + "\" issued to " + studentName + " successfully!");
    }

    // ─────────────────────────────────────────────
    // 5. RETURN BOOK
    // ─────────────────────────────────────────────
    public void returnBook(int id) throws Exception {
        Book book = findById(id);
        if (book.getAvailabilityStatus().equals("Available")) {
            throw new Exception("Book is already available. It was not issued.");
        }
        System.out.println("\n  ✔ Book \"" + book.getBookName() + "\" returned by " + book.getIssuedTo() + " successfully!");
        book.setAvailabilityStatus("Available");
        book.setIssuedTo(null);
    }

    // ─────────────────────────────────────────────
    // 6. DELETE BOOK
    // ─────────────────────────────────────────────
    public void deleteBook(int id) throws Exception {
        Book book = findById(id);
        if (book.getAvailabilityStatus().equals("Issued")) {
            throw new Exception("Cannot delete! Book is currently issued to: " + book.getIssuedTo());
        }
        books.remove(book);
        System.out.println("\n  ✔ Book \"" + book.getBookName() + "\" deleted successfully!");
    }

    // ─────────────────────────────────────────────
    // HELPER METHODS
    // ─────────────────────────────────────────────
    private Book findById(int id) throws Exception {
        for (Book b : books) {
            if (b.getBookId() == id) {
                return b;
            }
        }
        throw new Exception("Book with ID " + id + " not found!");
    }

    private void printHeader() {
        System.out.println("\n  +" + "-".repeat(8) + "+" + "-".repeat(27) + "+" + "-".repeat(22) + "+" + "-".repeat(14) + "+" + "-".repeat(17) + "+");
        System.out.printf("  | %-6s | %-25s | %-20s | %-12s | %-15s |%n",
                "ID", "Book Name", "Author", "Status", "Issued To");
        System.out.println("  +" + "-".repeat(8) + "+" + "-".repeat(27) + "+" + "-".repeat(22) + "+" + "-".repeat(14) + "+" + "-".repeat(17) + "+");
    }

    private void printFooter() {
        System.out.println("  +" + "-".repeat(8) + "+" + "-".repeat(27) + "+" + "-".repeat(22) + "+" + "-".repeat(14) + "+" + "-".repeat(17) + "+");
    }

    public int getTotalBooks() {
        return books.size();
    }

    public long getAvailableCount() {
        return books.stream().filter(b -> b.getAvailabilityStatus().equals("Available")).count();
    }

    public long getIssuedCount() {
        return books.stream().filter(b -> b.getAvailabilityStatus().equals("Issued")).count();
    }
}
