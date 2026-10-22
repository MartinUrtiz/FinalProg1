/**
 * Pila (LIFO) implementada desde cero con nodos enlazados.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    /** Agrega un elemento en el tope de la pila. */
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(top);
        top = newNode;
        size++;
    }

    /**
     * Quita y devuelve el elemento del tope.
     *
     * @throws IllegalStateException si la pila está vacía
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T data = top.getData();
        top = top.getNext();
        size--;
        return data;
    }

    /**
     * Devuelve el elemento del tope sin quitarlo.
     *
     * @throws IllegalStateException si la pila está vacía
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return top.getData();
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "Stack{size=" + size + ", top=" + (isEmpty() ? "null" : peek()) + "}";
    }
}