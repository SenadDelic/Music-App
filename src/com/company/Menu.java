package com.company;

public class Menu {

    public static void welcome() {
        System.out.println("*******************************************************");
        System.out.println("*********        WELCOME TO Music app         *********");
        System.out.println("*******************************************************");
    }

    public static void menu() {
        System.out.println("Select below"
                + "\n\t 1 --> For creating Album" +
                "\n\t 2 --> For creating Artist " +
                "\n\t 3 --> For creating Song " +
                "\n\t 4 --> For update Album" +
                "\n\t 5 --> For update Artist" +
                "\n\t 6 --> For update Song" +
                "\n\t 7 --> For printing Album" +
                "\n\t 8 --> For printing Artist" +
                "\n\t 9 --> For printing Song" +
                "\n\t 10 --> To delete Album" +
                "\n\t 11 --> To delete Artist" +
                "\n\t 12 --> To delete Song" +
                "\n\t 0 --> For Exit");
        System.out.print("\nEnter the transaction type: ");
    }

    public static void exit() {
        System.out.println("\n------------------------------------------------");
        System.out.println("********* Thank you. Have a nice day. **********");
        System.out.println("------------------------------------------------");
    }

}
