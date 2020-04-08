package com.example.bluetoothtest.dataStructures;

public class Vector<T> {
    final static private int INITIAL_SIZE = 10;
    final static private double GROWTH_RATE = (1 + Math.sqrt(5)) / 2;

    private Object[] arr;
    private int length;

    public Vector() {
        arr = new Object[INITIAL_SIZE];
        length = 0;
    }

    public Vector(Object[] arr) {
        this.arr = arr;
        length = arr.length;
    }

    public void add(T item) {
        if (length == arr.length) expand();

        arr[length++] = item;
    }

    public void add(int pos, T item) {
        if (length == arr.length) expand();

        System.arraycopy(arr, pos, arr, pos + 1, length - pos);

        arr[pos] = item;
        length++;
    }

    public void addAll(Vector<T> items) {
        while (arr.length < length + items.length) expand();

        System.arraycopy(items.arr, 0, arr, length, items.length);

        length += items.length;
    }

    public void addAll(int pos, Vector<T> items) {
        while (arr.length < length + items.length) expand();

        System.arraycopy(arr, pos, arr, pos + items.length, length - pos);
        System.arraycopy(items.arr, 0, arr, pos, items.length);

        length += items.length;
    }

    public void del(int index) {
        System.arraycopy(arr, index + 1, arr, index, length - index - 1);
        length--;
    }

    public T get(int index) {
        return (T) arr[index];
    }

    public Vector<T> splice(int start, int end) { //removes subsequence and returns it
        Object[] temp = new Object[end - start];

        System.arraycopy(arr, start, temp, 0, end - start);
        System.arraycopy(arr, end, arr, start, length - end);

        length -= end - start;
        return new Vector<>(temp);
    }

    public void clear() {
        length = 0;
    }

    public void swap(int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int size() {
        return length;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    private void expand() {
        Object[] temp = new Object[(int) (length * GROWTH_RATE)];

        System.arraycopy(arr, 0, temp, 0, length);
        arr = temp;
    }
}
