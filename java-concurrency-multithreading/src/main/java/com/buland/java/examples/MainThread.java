package com.buland.java.examples;

public class MainThread {

    public static void main(String[] args){
        System.out.println(
                Thread.currentThread().getId()
                        + " ----- " +
                        Thread.currentThread().getName()
                        + " ----- " +
                        Thread.currentThread().getThreadGroup() );

    }
}
