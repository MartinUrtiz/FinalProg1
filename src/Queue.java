/**
 * Cola (FIFO) implementada desde cero con nodos enlazados.
 *
 * @param <T> tipo de los elementos almacenados
 */
public class Queue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public Queue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    /** Agrega un elemento al final de la cola. */
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (rear != null) {
            rear.setNext(newNode);
        }
        rear = newNode;
        if (front == null) {
            front = newNode;
        }
        size++;
    }

    /**
     * Quita y devuelve el primer elemento de la cola.
     *
     * @throws IllegalStateException si la cola está vacía
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T data = front.getData();
        front = front.getNext();
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    /**
     * Devuelve el primer elemento sin quitarlo.
     *
     * @throws IllegalStateException si la cola está vacía
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return front.getData();
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "Queue{size=" + size + ", front=" + (isEmpty() ? "null" : peek()) + "}";
    }
}