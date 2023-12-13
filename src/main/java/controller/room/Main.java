package controller.room;
public class Main {
    public static void main(String[] args) {
        // Create instances of the classes containing the methods you want to run
        MethodRunner methodRunner1 = new MethodRunner();
        MethodRunner methodRunner2 = new MethodRunner();
        MethodRunner methodRunner3 = new MethodRunner();

        // Create threads for each method and start them
        Thread thread1 = new Thread(methodRunner1::method1);
        Thread thread2 = new Thread(methodRunner2::method2);
        Thread thread3 = new Thread(methodRunner3::method3);

        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static class MethodRunner {
        public void method1() {
            System.out.println(System.currentTimeMillis());
            // Method 1 logic here
            System.out.println("Method 1 is running");
        }

        public void method2() {
            System.out.println(System.currentTimeMillis());
            // Method 2 logic here
            System.out.println("Method 2 is running");
        }

        public void method3() {
            System.out.println(System.currentTimeMillis());
            // Method 3 logic here
            System.out.println("Method 3 is running");
        }
    }
}