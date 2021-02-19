package ru.geekbrains.java2.lesson5;

import java.util.Arrays;

public class ConcurrencyCalc implements Runnable {
    private static final int size = 10000000;
    private static final int h = size / 2;
    private float[] arr = new float[size];

    public void doFirstCase() {
        Arrays.fill(arr, 1);
        long start = System.currentTimeMillis();

        calcArray(arr);

        long end = System.currentTimeMillis() - start;
        System.out.println("Время вычисления метода №1: " + end);
    }

    public void doSecondCase() {
        Arrays.fill(arr, 1);
        long start = System.currentTimeMillis();

        float arr1[] = new float[h];
        float arr2[] = new float[h];
        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        Thread t1 = new Thread(() -> {
            calcArray(arr1);
            System.arraycopy(arr1, 0, arr, 0, h);
        });

        Thread t2 = new Thread(() -> {
            calcArray(arr2);
            System.arraycopy(arr2, 0, arr, h, h);
        });

        t1.start();
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis() - start;
        System.out.println("Время вычисления метода №2: " + end);
    }

    private void calcArray(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    @Override
    public void run() {

    }
}
