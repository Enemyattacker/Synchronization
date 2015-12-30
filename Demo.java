package ru.stetsenko;

public class Demo extends Thread {
    private static boolean ready = false;
    private final Object waitObject;
    public static int count;
    public String name;

    public Demo(Object waitObject, String name){
        this.waitObject=waitObject;
        setName(name);
    }
    public static void threadIncrement(Object waitObject, String name){
        synchronized (waitObject) {
            count++;
            System.out.println(name + count);
            ready = true;
            waitObject.notifyAll();
            while (ready) {
                try {
                    waitObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void anotherThreadIncrement(Object waitObject, String name){
        synchronized (waitObject) {
            while (!ready) {
                try {
                    waitObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count++;
            System.out.println(name + count);
            ready = false;
            waitObject.notifyAll();
        }
    }

    public void run(){
        for(int j=0; j < 100; j++) {
            name=getName();
            if(name.compareTo("FIRST ") == 0 ) {
                threadIncrement(waitObject, getName());
            }
            if(name.compareTo("SECOND ") == 0 ){
                anotherThreadIncrement(waitObject, getName());
            }
        }
    }
}