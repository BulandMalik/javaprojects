package com.buland.java.examples;

public class MulThreadsWIthForEach {

    public static void main(String[] args){
        System.out.println(
                "Starting------------------"+
                Thread.currentThread().getId()
                        + " ----- " +
                        Thread.currentThread().getName()
                        + " ----- " +
                        Thread.currentThread().getThreadGroup() );

        for (int i=0; i<100; i++){
            new Thread(new MyCustomThreadWithForEach(i)).start(); //seperate thread for each iteration
        }

        System.out.println(
                "Wrapping------------------"+
                        Thread.currentThread().getId()
                        + " ----- " +
                        Thread.currentThread().getName()
                        + " ----- " +
                        Thread.currentThread().getThreadGroup() );

    }
}

class MyCustomThreadWithForEach implements Runnable {

    int number = 0;

    public MyCustomThreadWithForEach(int num){
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
                        "Number:"+number);
    }
}
