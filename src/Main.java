/**
 * Casos de prueba de todas las funcionalidades del sistema.
 */
public class Main {
    private static int passed = 0;
    private static int failed = 0;

    private static final String QUIJOTE = "978-84-376-0494-7";
    private static final String CIEN_ANIOS = "978-84-376-0495-4";
    private static final String ORWELL = "978-84-376-0496-1";
    private static final String COLERA = "978-84-376-0497-8";

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN DE BIBLIOTECA ===");

        testLinkedList();
        testStack();
        testQueue();

        Library library = new Library("Biblioteca Central");
        testLibraryBasics(library);
        testLoans(library);
        testWaitlist(library);
        testBookDirect();

        section("ESTADÍSTICAS FINALES");
        System.out.println(library.getStatistics());

        System.out.println("Pruebas OK: " + passed + " | Fallidas: " + failed);
        System.out.println(failed == 0 ? "Todo funciona como se espera." : "Hay pruebas fallidas, revisar.");
    }

    // ---------- Tarea 1: LinkedList ----------
    private static void testLinkedList() {
        section("LINKEDLIST");
        LinkedList<Integer> list = new LinkedList<>();
        check("lista nueva está vacía", list.isEmpty() && list.size() == 0);

        list.add(10);
        list.add(20);
        list.add(30);
        check("size es 3 tras agregar 3 elementos", list.size() == 3);
        check("get(1) devuelve 20", list.get(1) == 20);
        check("contains(20) es true", list.contains(20));
        check("contains(99) es false", !list.contains(99));

        check("remove(20) del medio devuelve true", list.remove(20));
        check("tras remove, size es 2 y get(1) es 30", list.size() == 2 && list.get(1) == 30);
        check("remove(99) inexistente devuelve false", !list.remove(99));
        check("remove(10) de la cabeza devuelve true", list.remove(10));
        check("remove(30) del último deja la lista vacía", list.remove(30) && list.isEmpty());
        check("remove en lista vacía devuelve false", !list.remove(1));

        boolean thrown = false;
        try {
            list.get(0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check("get en lista vacía lanza IndexOutOfBoundsException", thrown);

        list.add(1);
        list.add(2);
        list.clear();
        check("clear deja la lista vacía", list.isEmpty() && list.size() == 0);
    }

    // ---------- Stack ----------
    private static void testStack() {
        section("STACK (PILA)");
        Stack<String> stack = new Stack<>();
        check("pila nueva está vacía", stack.isEmpty());

        stack.push("A");
        stack.push("B");
        stack.push("C");
        check("peek devuelve el tope (C) sin quitarlo", "C".equals(stack.peek()) && stack.size() == 3);
        check("pop devuelve en orden LIFO (C, B, A)",
                "C".equals(stack.pop()) && "B".equals(stack.pop()) && "A".equals(stack.pop()));
        check("pila queda vacía", stack.isEmpty());

        boolean thrownPop = false;
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            thrownPop = true;
        }
        boolean thrownPeek = false;
        try {
            stack.peek();
        } catch (IllegalStateException e) {
            thrownPeek = true;
        }
        check("pop y peek en pila vacía lanzan IllegalStateException", thrownPop && thrownPeek);
    }

    // ---------- Queue ----------
    private static void testQueue() {
        section("QUEUE (COLA)");
        Queue<String> queue = new Queue<>();
        check("cola nueva está vacía", queue.isEmpty());

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        check("peek devuelve el frente (A) sin quitarlo", "A".equals(queue.peek()) && queue.size() == 3);
        check("dequeue devuelve en orden FIFO (A, B, C)",
                "A".equals(queue.dequeue()) && "B".equals(queue.dequeue()) && "C".equals(queue.dequeue()));
        check("cola queda vacía", queue.isEmpty());

        queue.enqueue("X");
        check("la cola funciona tras vaciarse y reutilizarse", "X".equals(queue.dequeue()));

        boolean thrownDequeue = false;
        try {
            queue.dequeue();
        } catch (IllegalStateException e) {
            thrownDequeue = true;
        }
        boolean thrownPeek = false;
        try {
            queue.peek();
        } catch (IllegalStateException e) {
            thrownPeek = true;
        }
        check("dequeue y peek en cola vacía lanzan IllegalStateException", thrownDequeue && thrownPeek);
    }

    // ---------- Library: libros, búsquedas, estadísticas ----------
    private static void testLibraryBasics(Library library) {
        section("LIBRARY: LIBROS Y BÚSQUEDAS");
        Book quijote = new Book("El Quijote", QUIJOTE, "Miguel de Cervantes", 1605);
        Book cien = new Book("Cien años de soledad", CIEN_ANIOS, "Gabriel García Márquez", 1967);
        Book orwell = new Book("1984", ORWELL, "George Orwell", 1949);
        Book colera = new Book("El amor en los tiempos del cólera", COLERA, "Gabriel García Márquez", 1985);

        check("addBook agrega 4 libros",
                library.addBook(quijote) && library.addBook(cien) && library.addBook(orwell) && library.addBook(colera));
        check("addBook(null) devuelve false", !library.addBook(null));
        check("addBook con ISBN duplicado devuelve false",
                !library.addBook(new Book("Copia", QUIJOTE, "Alguien", 2000)));
        check("la biblioteca tiene 4 libros", library.getBooks().size() == 4);
        System.out.println(library);
        System.out.println(library.getStatistics());

        check("findBookByIsbn encuentra el libro", library.findBookByIsbn(ORWELL) == orwell);
        check("findBookByIsbn inexistente devuelve null", library.findBookByIsbn("000") == null);
        check("findBookByIsbn(null) devuelve null", library.findBookByIsbn(null) == null);

        LinkedList<Book> byTitle = library.findBooksByTitle("AMOR");
        check("búsqueda por título parcial e insensible a mayúsculas ('AMOR')", byTitle.size() == 1);
        printList("Resultado por título 'AMOR':", byTitle);
        check("búsqueda por título sin resultados devuelve lista vacía",
                library.findBooksByTitle("xyz").isEmpty());
        check("búsqueda por título null devuelve lista vacía", library.findBooksByTitle(null).isEmpty());

        LinkedList<Book> byAuthor = library.findBooksByAuthor("garcía márquez");
        check("búsqueda por autor devuelve 2 libros", byAuthor.size() == 2);
        printList("Resultado por autor 'garcía márquez':", byAuthor);
        check("búsqueda por autor sin resultados devuelve lista vacía",
                library.findBooksByAuthor("nadie").isEmpty());

        Book temp = new Book("Libro temporal", "TEMP-1", "Autor Temp", 2024);
        library.addBook(temp);
        check("removeBook elimina un libro existente", library.removeBook(temp));
        check("removeBook de un libro ya eliminado devuelve false", !library.removeBook(temp));
        check("tras remove, el libro ya no se encuentra", library.findBookByIsbn("TEMP-1") == null);

        check("al inicio todos están disponibles",
                library.getAvailableBooks().size() == 4 && library.getLoanedBooks().isEmpty());
    }

    // ---------- Tarea 2: préstamos y deshacer ----------
    private static void testLoans(Library library) {
        section("TAREA 2: PRÉSTAMOS Y DESHACER");
        Book quijote = library.findBookByIsbn(QUIJOTE);

        check("lendBook presta el libro a Ana", library.lendBook(QUIJOTE, "Ana"));
        check("lendBook de un libro ya prestado devuelve false", !library.lendBook(QUIJOTE, "Bruno"));
        check("lendBook de ISBN inexistente devuelve false", !library.lendBook("000", "Ana"));
        check("lendBook con usuario vacío devuelve false", !library.lendBook(CIEN_ANIOS, " "));
        check("ver último préstamo devuelve 'Ana'", "Ana".equals(library.getLastLoan(QUIJOTE)));
        check("el libro figura como prestado", library.getLoanedBooks().size() == 1);

        check("deshacer último préstamo devuelve 'Ana'", "Ana".equals(library.undoLastLoan(QUIJOTE)));
        check("tras deshacer, el libro está disponible y timesLoaned vuelve a 0",
                quijote.isAvailable() && quijote.getTimesLoaned() == 0);
        check("deshacer sin préstamo activo devuelve null", library.undoLastLoan(QUIJOTE) == null);
        check("ver último préstamo sin historial devuelve null", library.getLastLoan(QUIJOTE) == null);
        check("deshacer con ISBN inexistente devuelve null", library.undoLastLoan("000") == null);

        check("se presta correctamente a Bruno tras el error", library.lendBook(QUIJOTE, "Bruno"));
        check("ver último préstamo devuelve 'Bruno'", "Bruno".equals(library.getLastLoan(QUIJOTE)));
        System.out.println(library.getStatistics());
    }

    // ---------- Tarea 3: espera de libros ----------
    private static void testWaitlist(Library library) {
        section("TAREA 3: ESPERA DE LIBROS");
        Book quijote = library.findBookByIsbn(QUIJOTE); // prestado a Bruno

        check("no se puede esperar un libro disponible", !library.joinWaitlist(ORWELL, "Zoe"));
        check("no se puede esperar un ISBN inexistente", !library.joinWaitlist("000", "Zoe"));
        check("no se agrega un usuario vacío", !library.joinWaitlist(QUIJOTE, ""));
        check("Carla se agrega a la espera", library.joinWaitlist(QUIJOTE, "Carla"));
        check("Diego se agrega a la espera", library.joinWaitlist(QUIJOTE, "Diego"));
        check("el primero en espera es Carla", "Carla".equals(library.getFirstWaiting(QUIJOTE)));
        check("hay 2 usuarios en espera", quijote.getWaitlistSize() == 2);

        check("al devolver, el libro pasa a Carla", "Carla".equals(library.returnBook(QUIJOTE)));
        check("el libro sigue prestado (ahora a Carla)",
                !quijote.isAvailable() && "Carla".equals(library.getLastLoan(QUIJOTE)));
        check("el primero en espera ahora es Diego", "Diego".equals(library.getFirstWaiting(QUIJOTE)));

        check("al devolver, el libro pasa a Diego", "Diego".equals(library.returnBook(QUIJOTE)));
        check("la espera queda vacía", library.getFirstWaiting(QUIJOTE) == null);

        check("al devolver sin espera, nadie lo recibe (null)", library.returnBook(QUIJOTE) == null);
        check("el libro queda disponible", quijote.isAvailable());

        boolean notLoaned = false;
        try {
            library.returnBook(QUIJOTE);
        } catch (IllegalStateException e) {
            notLoaned = true;
        }
        check("devolver un libro disponible lanza IllegalStateException", notLoaned);

        boolean notFound = false;
        try {
            library.returnBook("000");
        } catch (IllegalArgumentException e) {
            notFound = true;
        }
        check("devolver un ISBN inexistente lanza IllegalArgumentException", notFound);
        check("timesLoaned de El Quijote es 3 (Bruno, Carla, Diego)", quijote.getTimesLoaned() == 3);
    }

    // ---------- Book: lend() / returnBook() directos ----------
    private static void testBookDirect() {
        section("BOOK: lend() Y returnBook()");
        Book book = new Book("Libro de prueba", "TEST-1", "Autor", 2000);
        check("libro nuevo está disponible con 0 préstamos", book.isAvailable() && book.getTimesLoaned() == 0);
        check("lend() presta el libro", book.lend() && !book.isAvailable());
        check("lend() sobre libro prestado devuelve false", !book.lend());
        check("timesLoaned es 1", book.getTimesLoaned() == 1);
        book.returnBook();
        check("returnBook() lo deja disponible", book.isAvailable());
        book.setTitle("Otro título");
        check("los setters modifican los datos", "Otro título".equals(book.getTitle()));
    }

    // ---------- Utilidades de prueba ----------
    private static void section(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  [OK]   " + description);
        } else {
            failed++;
            System.out.println("  [FAIL] " + description);
        }
    }

    private static void printList(String header, LinkedList<Book> list) {
        System.out.println("  " + header);
        if (list.isEmpty()) {
            System.out.println("    (sin resultados)");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println("    - " + list.get(i));
        }
    }
}