/**
 * Representa un libro de la biblioteca. Cada libro lleva su propio
 * historial de préstamos (pila) y su lista de espera (cola).
 */
public class Book {
    private String title;
    private String isbn;
    private String author;
    private int publicationYear;
    private boolean available;
    private int timesLoaned;

    /** Historial de usuarios a quienes se prestó este libro (el último, arriba). */
    private final Stack<String> loanHistory;
    /** Usuarios esperando este libro, en orden de llegada. */
    private final Queue<String> waitlist;

    public Book(String title, String isbn, String author, int publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publicationYear = publicationYear;
        this.available = true;
        this.timesLoaned = 0;
        this.loanHistory = new Stack<>();
        this.waitlist = new Queue<>();
    }

    // ---------- Getters ----------
    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getTimesLoaned() {
        return timesLoaned;
    }

    public boolean isAvailable() {
        return available;
    }

    // ---------- Setters ----------
    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setTimesLoaned(int timesLoaned) {
        this.timesLoaned = timesLoaned;
    }

    // ---------- Préstamos ----------

    /**
     * Presta el libro sin registrar usuario.
     *
     * @return true si se prestó, false si ya estaba prestado
     */
    public boolean lend() {
        return lend("sin registrar");
    }

    /**
     * Presta el libro a un usuario y registra la operación en el historial.
     *
     * @return true si se prestó, false si ya estaba prestado
     */
    public boolean lend(String user) {
        if (!available) {
            return false;
        }
        available = false;
        timesLoaned++;
        loanHistory.push(user);
        return true;
    }

    /** Marca el libro como disponible (devolución normal; no toca el historial). */
    public void returnBook() {
        available = true;
    }

    /**
     * Deshace el último préstamo (por ejemplo, si se prestó al usuario equivocado).
     * Solo aplica si ese préstamo sigue activo.
     *
     * @return el usuario del préstamo deshecho, o null si no hay nada que deshacer
     */
    public String undoLastLoan() {
        if (available || loanHistory.isEmpty()) {
            return null;
        }
        String user = loanHistory.pop();
        available = true;
        timesLoaned--;
        return user;
    }

    /** Devuelve el usuario del último préstamo registrado, o null si no hay. */
    public String getLastLoan() {
        return loanHistory.isEmpty() ? null : loanHistory.peek();
    }

    // ---------- Lista de espera ----------

    /** Agrega un usuario al final de la lista de espera. */
    public void addToWaitlist(String user) {
        waitlist.enqueue(user);
    }

    /** Devuelve el primer usuario en espera sin quitarlo, o null si no hay. */
    public String peekWaitlist() {
        return waitlist.isEmpty() ? null : waitlist.peek();
    }

    /** Quita y devuelve el primer usuario en espera, o null si no hay. */
    public String pollWaitlist() {
        return waitlist.isEmpty() ? null : waitlist.dequeue();
    }

    public int getWaitlistSize() {
        return waitlist.size();
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", publicationYear=" + publicationYear +
                ", available=" + available +
                ", timesLoaned=" + timesLoaned +
                '}';
    }
}