/**
 * Casos de prueba: ejemplos de uso de todas las funcionalidades.
 */
public class Main {

    public static void main(String[] args) {
        // ---------- Tarea 1: LinkedList ----------
        section("LINKEDLIST");
        LinkedList<Integer> list = new LinkedList<>();
        show("¿Vacía al inicio?", list.isEmpty());
        list.add(10);
        list.add(20);
        list.add(30);
        show("Lista", list + " (tamaño " + list.size() + ")");
        show("get(1)", list.get(1));
        show("contains(20) / contains(99)", list.contains(20) + " / " + list.contains(99));
        show("remove(20)", list.remove(20) + " -> " + list);
        show("remove(99) inexistente", list.remove(99));
        try {
            list.get(5);
        } catch (IndexOutOfBoundsException e) {
            show("get(5) fuera de rango", e.getMessage());
        }
        list.clear();
        show("Tras clear, ¿vacía?", list.isEmpty());

        // ---------- Stack ----------
        section("STACK (PILA)");
        Stack<String> stack = new Stack<>();
        stack.push("A");
        stack.push("B");
        stack.push("C");
        show("peek", stack.peek());
        show("pop x3 (LIFO)", stack.pop() + ", " + stack.pop() + ", " + stack.pop());
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            show("pop en pila vacía", e.getMessage());
        }

        // ---------- Queue ----------
        section("QUEUE (COLA)");
        Queue<String> queue = new Queue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        show("peek", queue.peek());
        show("dequeue x3 (FIFO)", queue.dequeue() + ", " + queue.dequeue() + ", " + queue.dequeue());
        try {
            queue.dequeue();
        } catch (IllegalStateException e) {
            show("dequeue en cola vacía", e.getMessage());
        }

        // ---------- Library: libros y búsquedas ----------
        section("LIBRARY");
        Library library = new Library("Biblioteca Central");
        Book quijote = new Book("El Quijote", "111", "Miguel de Cervantes", 1605);
        Book cien = new Book("Cien años de soledad", "222", "Gabriel García Márquez", 1967);
        Book orwell = new Book("1984", "333", "George Orwell", 1949);
        library.addBook(quijote);
        library.addBook(cien);
        library.addBook(orwell);
        show("addBook con ISBN duplicado", library.addBook(new Book("Copia", "111", "Alguien", 2000)));
        show("addBook(null)", library.addBook(null));
        show("findBookByIsbn(\"333\")", library.findBookByIsbn("333"));
        show("findBookByIsbn(\"000\")", library.findBookByIsbn("000"));
        show("findBooksByTitle(\"SOLEDAD\")", library.findBooksByTitle("SOLEDAD"));
        show("findBooksByAuthor(\"orwell\")", library.findBooksByAuthor("orwell"));
        show("findBooksByAuthor(\"nadie\")", library.findBooksByAuthor("nadie"));
        show("removeBook(orwell)", library.removeBook(orwell));
        System.out.println(library.getStatistics());

        // ---------- Tarea 2: préstamos y deshacer ----------
        section("TAREA 2: PRÉSTAMOS");
        show("lendBook a Ana", library.lendBook("111", "Ana"));
        show("lendBook a Bruno (ya prestado)", library.lendBook("111", "Bruno"));
        show("Último préstamo", library.getLastLoan("111"));
        show("Deshacer último préstamo", library.undoLastLoan("111"));
        show("¿Disponible tras deshacer?", quijote.isAvailable());
        show("Deshacer sin préstamo activo", library.undoLastLoan("111"));
        library.lendBook("111", "Bruno");
        show("Ahora el último préstamo", library.getLastLoan("111"));

        // ---------- Tarea 3: espera de libros ----------
        section("TAREA 3: ESPERA");
        show("Esperar libro disponible (333 ya no existe)", library.joinWaitlist("333", "Zoe"));
        show("Carla se anota", library.joinWaitlist("111", "Carla"));
        show("Diego se anota", library.joinWaitlist("111", "Diego"));
        show("Primero en espera", library.getFirstWaiting("111"));
        show("Devolución -> lo recibe", library.returnBook("111"));
        show("Devolución -> lo recibe", library.returnBook("111"));
        show("Devolución sin espera", library.returnBook("111"));
        try {
            library.returnBook("111");
        } catch (IllegalStateException e) {
            show("Devolver libro ya disponible", e.getMessage());
        }

        // ---------- Book: lend() y returnBook() directos ----------
        section("BOOK");
        show("lend()", cien.lend());
        show("lend() de nuevo", cien.lend());
        cien.returnBook();
        show("¿Disponible tras returnBook()?", cien.isAvailable());

        section("ESTADÍSTICAS FINALES");
        System.out.println(library.getStatistics());
    }

    private static void section(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    private static void show(String label, Object value) {
        System.out.println("  " + label + ": " + value);
    }
}