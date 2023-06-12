package com.buland.java.examples;

public class TwoThreads {

    public static void main(String[] args){
        System.out.println(
                Thread.currentThread().getId()
                        + " ----- " +
                        Thread.currentThread().getName()
                        + " ----- " +
                        Thread.currentThread().getThreadGroup() );

        //new MyCustomThread().run(); //worng way, its not creating a thread but simple execution

        new Thread(new MyCustomThread()).start(); //seperate thread for each iteration
    }
}

class MyCustomThread implements Runnable {

    @Override
    public void run() {
        System.out.println(
                "*******************************"
                        + " ----- " +
                        Thread.currentThread().getId()
                        + " ----- " +
                        Thread.currentThread().getName()
                        + " ----- " +
                        Thread.currentThread().getThreadGroup() );
    }
}
