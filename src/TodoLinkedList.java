public class TodoLinkedList implements java.lang.Iterable<TheTask> {
    private Node head, tail;
    private int size = 0;

    public TodoLinkedList() {
    }

    public void add(TheTask e) {
       if (head == null) {
           head = new Node(e);
           tail = head;
           size++;
       } else {
           tail.next = new Node(e);
           tail = tail.next;
           size++;
       }
    }

    public void clean() {
        size = 0;
        head = tail = null;
    }

    public TheTask remove(int index) {
        if (index < 0 || index >= size)
            return null;
        else if (index == 0) {
            Node current = head;
            head = head.next;
            size--;
            return current.element;
        } else if (index == size - 1) {
            Node previous = head;
            for (int i = 2; i < size; i++)
                previous = previous.next;
            Node current = previous.next;
            previous.next = null;
            tail = previous;
            size--;
            return current.element;
        }

        Node previous = head;
        for (int i = 1; i < index; i++) {
            previous = previous.next;
        }

        Node current = previous.next;
        previous.next = current.next;
        size--;
        return current.element;
    }

    public boolean move(int source, int target) {
        Node targetCurrent = head;
        Node sourceCurrent = head;

        if (source < 0 || source >= size || target < 0 || target >= size)
            return false;

        if (source < target) {
            if (source == 0) {
                for (int i = 0; i < target; i++)
                    targetCurrent = targetCurrent.next;

                head = head.next;
                sourceCurrent.next = targetCurrent.next;
                targetCurrent.next = sourceCurrent;

                if (tail.next == null)
                    tail = sourceCurrent;

                return true;
            } else {
                Node sourcePrevious = head;
                for (int i = 1; i < source; i++)
                    sourcePrevious = sourcePrevious.next;
                sourceCurrent = sourcePrevious.next;
                for (int i = 0; i < target; i++)
                    targetCurrent = targetCurrent.next;

                sourcePrevious.next = sourceCurrent.next;
                sourceCurrent.next = targetCurrent.next;
                targetCurrent.next = sourceCurrent;

                if (tail.next == null)
                    tail = sourceCurrent;

                return true;
            }
        } else if (source > target) {
            if (target == 0) {
                Node sourcePrevious = head;
                for (int i = 1; i < source; i++)
                    sourcePrevious = sourcePrevious.next;
                sourceCurrent = sourcePrevious.next;

                if (tail == sourceCurrent)
                    tail = sourcePrevious;

                sourcePrevious.next = sourceCurrent.next;
                sourceCurrent.next = targetCurrent;
                head = sourceCurrent;

                return true;
            } else {
                Node sourcePrevious = head;
                Node targetPrevious = head;
                for (int i = 1; i < source; i++)
                    sourcePrevious = sourcePrevious.next;
                sourceCurrent = sourcePrevious.next;
                for (int i = 1; i < target; i++)
                    targetPrevious = targetPrevious.next;
                targetCurrent = targetPrevious.next;

                if (tail == sourceCurrent)
                    tail = sourcePrevious;

                sourcePrevious.next = sourceCurrent.next;
                targetPrevious.next = sourceCurrent;
                sourceCurrent.next = targetCurrent;

                return true;
            }
        } else
            return true;
    }

    public int getSize() {
        return size;
    }

    public java.util.Iterator<TheTask> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements java.util.Iterator<TheTask> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public TheTask next() {
            TheTask task = current.element;
            current = current.next;
            return task;
        }

        @Override
        public void remove() {
            System.out.println("This method is not implemented");
        }
    }

    private static class Node {
        TheTask element;
        Node next;

        public Node(TheTask element) {
            this.element = element;
        }
    }
}
