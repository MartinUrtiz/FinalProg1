/**
 * Gestiona una colección de libros usando una LinkedList propia,
 * junto con préstamos (con deshacer) y listas de espera.
 */
public class Library {
    private String name;
    private final LinkedList<Book> books;

    public Library(String name) {
        this.name = name;
        this.books = new LinkedList<>();
    }

    // ---------- Getters / Setters ----------
    public String getName() {
        return name;
    }

    public LinkedList<Book> getBooks() {
        return books;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ---------- Gestión de libros ----------

    /**
     * Agrega un libro a la biblioteca.
     *
     * @return false si el libro es null o ya existe uno con el mismo ISBN
     */
    public boolean addBook(Book book) {
        if (book == null || findBookByIsbn(book.getIsbn()) != null) {
            return false;
        }
        books.add(book);
        return true;
    }

    /** Elimina un libro de la biblioteca. */
    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    // ---------- Búsquedas ----------

    /** Busca por ISBN exacto. Devuelve null si no existe. */
    public Book findBookByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }

    /** Busca por título (parcial, sin distinguir mayúsculas). */
    public LinkedList<Book> findBooksByTitle(String title) {
        LinkedList<Book> found = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (containsIgnoreCase(book.getTitle(), title)) {
                found.add(book);
            }
        }
        return found;
    }

    /** Busca por autor (parcial, sin distinguir mayúsculas). */
    public LinkedList<Book> findBooksByAuthor(String authorName) {
        LinkedList<Book> found = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (containsIgnoreCase(book.getAuthor(), authorName)) {
                found.add(book);
            }
        }
        return found;
    }

    private boolean containsIgnoreCase(String text, String query) {
        if (text == null || query == null) {
            return false;
        }
        return text.toLowerCase().contains(query.toLowerCase());
    }

    // ---------- Consultas ----------

    public LinkedList<Book> getAvailableBooks() {
        LinkedList<Book> available = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.isAvailable()) {
                available.add(book);
            }
        }
        return available;
    }

    public LinkedList<Book> getLoanedBooks() {
        LinkedList<Book> loaned = new LinkedList<>();
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (!book.isAvailable()) {
                loaned.add(book);
            }
        }
        return loaned;
    }

    public String getStatistics() {
        return String.format(
                "Estadísticas de la Biblioteca:\n" +
                        "- Total de Libros: %d\n" +
                        "- Libros Disponibles: %d\n" +
                        "- Libros Prestados: %d\n",
                books.size(), getAvailableBooks().size(), getLoanedBooks().size());
    }

    // ---------- Tarea 2: operaciones de préstamo (pila por libro) ----------

    /**
     * Presta un libro a un usuario y lo registra en el historial del libro.
     *
     * @return false si el libro no existe, ya está prestado o el usuario es inválido
     */
    public boolean lendBook(String isbn, String user) {
        if (user == null || user.trim().isEmpty()) {
            return false;
        }
        Book book = findBookByIsbn(isbn);
        return book != null && book.lend(user);
    }

    /**
     * Deshace el último préstamo de un libro.
     *
     * @return el usuario del préstamo deshecho, o null si no hay nada que deshacer
     */
    public String undoLastLoan(String isbn) {
        Book book = findBookByIsbn(isbn);
        return book == null ? null : book.undoLastLoan();
    }

    /** Devuelve el usuario del último préstamo del libro, o null si no hay. */
    public String getLastLoan(String isbn) {
        Book book = findBookByIsbn(isbn);
        return book == null ? null : book.getLastLoan();
    }

    // ---------- Tarea 3: espera de libros (cola por libro) ----------

    /**
     * Agrega un usuario a la espera de un libro que está prestado.
     *
     * @return false si el libro no existe, está disponible (conviene prestarlo
     *         directamente) o el usuario es inválido
     */
    public boolean joinWaitlist(String isbn, String user) {
        if (user == null || user.trim().isEmpty()) {
            return false;
        }
        Book book = findBookByIsbn(isbn);
        if (book == null || book.isAvailable()) {
            return false;
        }
        book.addToWaitlist(user);
        return true;
    }

    /** Devuelve el primer usuario en espera del libro, o null si no hay. */
    public String getFirstWaiting(String isbn) {
        Book book = findBookByIsbn(isbn);
        return book == null ? null : book.peekWaitlist();
    }

    /**
     * Registra la devolución de un libro. Si hay usuarios en espera, el primero
     * lo recibe en préstamo automáticamente.
     *
     * @return el usuario que recibió el libro, o null si nadie esperaba
     * @throws IllegalArgumentException si el ISBN no existe
     * @throws IllegalStateException    si el libro no estaba prestado
     */
    public String returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No existe un libro con ISBN: " + isbn);
        }
        if (book.isAvailable()) {
            throw new IllegalStateException("El libro ya está disponible: " + book.getTitle());
        }
        book.returnBook();
        String next = book.pollWaitlist();
        if (next != null) {
            book.lend(next);
        }
        return next;
    }

    @Override
    public String toString() {
        return "Library{name='" + name + "', totalBooks=" + books.size() + "}";
    }
}