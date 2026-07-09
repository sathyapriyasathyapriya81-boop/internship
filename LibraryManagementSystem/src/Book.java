public class Book {
    private int bookId;
    private String bookName;
    private String authorName;
    private String availabilityStatus;
    private String issuedTo; // Student name who borrowed the book

    // Constructor
    public Book(int bookId, String bookName, String authorName) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.availabilityStatus = "Available";
        this.issuedTo = null;
    }

    // Getters
    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    // Setters
    public void setAvailabilityStatus(String status) {
        this.availabilityStatus = status;
    }

    public void setIssuedTo(String studentName) {
        this.issuedTo = studentName;
    }

    // Display book info
    @Override
    public String toString() {
        String issued = (issuedTo != null) ? issuedTo : "N/A";
        return String.format("| %-6d | %-25s | %-20s | %-12s | %-15s |",
                bookId, bookName, authorName, availabilityStatus, issued);
    }
}
