/**
 * Lista enlazada simple implementada desde cero (sin java.util).
 *
 * @param <T> tipo de los elementos almacenados
 */
public class LinkedList<T> {
    private Node<T> head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /** Agrega un elemento al final de la lista. */
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    /**
     * Devuelve el elemento en la posición indicada.
     *
     * @throws IndexOutOfBoundsException si el índice es inválido
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Elimina la primera aparición del valor indicado.
     *
     * @return true si se eliminó, false si no estaba en la lista
     */
    public boolean remove(T data) {
        if (head == null) {
            return false;
        }
        if (sameValue(head.getData(), data)) {
            head = head.getNext();
            size--;
            return true;
        }
        Node<T> current = head;
        while (current.getNext() != null) {
            if (sameValue(current.getNext().getData(), data)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /** Indica si el valor existe en la lista. */
    public boolean contains(T data) {
        Node<T> current = head;
        while (current != null) {
            if (sameValue(current.getData(), data)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Elimina todos los elementos de la lista. */
    public void clear() {
        head = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    /** Compara dos valores tolerando null (sin usar java.util.Objects). */
    private boolean sameValue(T a, T b) {
        return a == null ? b == null : a.equals(b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            sb.append(current.getData());
            if (current.getNext() != null) {
                sb.append(", ");
            }
            current = current.getNext();
        }
        return sb.append("]").toString();
    }
}