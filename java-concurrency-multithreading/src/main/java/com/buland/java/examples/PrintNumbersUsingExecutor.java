package com.buland.java.examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintNumbersUsingExecutor {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
            System.out.println(
                    "Starting------------------" +
                            Thread.currentThread().getId()
                            + " ----- " +
                            Thread.currentThread().getName()
                            + " ----- " +
                            Thread.currentThread().getThreadGroup());

            for (int i = 0; i < 100; i++) {
                if (i == 49 )
                {
                    System.out.println(""); // can be used to debug and put break point
                }
                executorService.submit(new com.buland.java.examples.MyCustomThreadWithForEach1(i)); //seperate thread for each iteration
            }

            System.out.println(
                    "Wrapping------------------" +
                            Thread.currentThread().getId()
                            + " ----- " +
                            Thread.currentThread().getName()
                            + " ----- " +
                            Thread.currentThread().getThreadGroup());

        }
    }

    class MyCustomThreadWithForEach1 implements Runnable {

        int number = 0;

        public MyCustomThreadWithForEach1(int num) {
            super();
            number = num;
        }

        @Override
        public void run() {
            System.out.println(
                    "*******************************"
                            + " ----- " +
                            Thread.currentThread().getId()
                            + " ----- " +
                            Thread.currentThread().getName()
                            + " ----- " +
                            Thread.currentThread().getThreadGroup()
                            + " ----- " +
                            "Number:" + number);
        }
    }