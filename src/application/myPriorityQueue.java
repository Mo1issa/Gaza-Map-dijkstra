package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class myPriorityQueue<T> {
    private List<T> heap;
    private Comparator<T> comparator;

    public myPriorityQueue(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void add(T element) {
        heap.add(element);
        heapifyUp();
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }

        T root = heap.get(0);
        int lastIdx = heap.size() - 1;
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);
        heapifyDown();

        return root;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void heapifyUp() {
        int currentIdx = heap.size() - 1;

        while (currentIdx > 0) {
            int parentIdx = (currentIdx - 1) / 2;
            if (comparator.compare(heap.get(currentIdx), heap.get(parentIdx)) >= 0) {
                break;  // Heap property is satisfied
            }

            swap(currentIdx, parentIdx);
            currentIdx = parentIdx;
        }
    }

    private void heapifyDown() {
        int currentIdx = 0;

        while (true) {
            int leftChildIdx = 2 * currentIdx + 1;
            int rightChildIdx = 2 * currentIdx + 2;
            int smallest = currentIdx;

            if (leftChildIdx < heap.size() && comparator.compare(heap.get(leftChildIdx), heap.get(smallest)) < 0) {
                smallest = leftChildIdx;
            }

            if (rightChildIdx < heap.size() && comparator.compare(heap.get(rightChildIdx), heap.get(smallest)) < 0) {
                smallest = rightChildIdx;
            }

            if (smallest == currentIdx) {
                break;  // Heap property is satisfied
            }

            swap(currentIdx, smallest);
            currentIdx = smallest;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
