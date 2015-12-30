package ru.stetsenko;

public class Main {

    public static void main (String[] args) {

        Object waitObject = new Object();
        Demo thread = new Demo(waitObject, "FIRST ");
        Demo anotherThread = new Demo(waitObject, "SECOND ");

        thread.start();
        anotherThread.start();

        try{
            thread.join();
            anotherThread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Финальное значение " + Demo.count);

    }
}
