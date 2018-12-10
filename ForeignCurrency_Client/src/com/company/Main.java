package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static final int PORT = 4500;
    public static final String HOST = "10.0.0.8";
    public static void main(String[] args) {
        //showing UI to user
        Scanner scanner = new Scanner(System.in);
        String inputValue;
            while (true) {
                inputValue = "0";
                System.out.println("Welcome to our Foreign Currency Exchange :)");
                System.out.println("please enter your money amount in ILS");
                /*its not a mistake, its because of the problem of the NextLine with enters
                i had to write it twice here, so *only* for the first running you will need to write
                your Money amount twice*/
                inputValue = scanner.nextLine();
                inputValue = scanner.nextLine();

                //making sure that input only Numeric
                while (!inputValue.matches("[0-9]+")) {
                    System.out.println("enter numeric value only!!!");
                    System.out.println("please enter your money amount in ILS");
                    inputValue = scanner.nextLine();
                }
                double sum = Double.parseDouble(inputValue);
                System.out.println("please select the currency you want convert your money:");
                System.out.println("enter 1 for dollar");
                System.out.println("enter 2 for euro");
                System.out.println("enter 3 for Pound Sterling");
                System.out.println("enter 4 for Japanese Yen");
                System.out.println("enter 5 for Korean Won");
                System.out.println("enter 6 for Thailand Baht");
                System.out.println("enter 7 for Polish Zloty");
                String currencyAction = scanner.next();
                String currency = new String();
                switch (currencyAction) {
                    case "1":
                        currency = "dollar";
                        break;
                    case "2":
                        currency = "euro";
                        break;
                    case "3":
                        currency = "Pound Sterling";
                        break;
                    case "4":
                        currency = "Japanese Yen";
                        break;
                    case "5":
                        currency = "Korean Won";
                        break;
                    case "6":
                        currency = "Thailand Baht";
                        break;
                    case "7":
                        currency = "Polish Zloty";
                        break;
                    default:
                        System.out.println("you did not choose from the menu :(");
                        break;
                }
                if(!currency.equals("")) {
                    Money money = new Money(sum, currency);
                    double value = streamMoneyToServer(money);
                    System.out.println(sum + " ILS worth " + value + " " + currency);
                }

            }
    }
    //we sent a Money object to server and get him back after change in the sum
    public static double streamMoneyToServer(Money money){
        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try{
            socket = new Socket(HOST, PORT);
            outputStream = socket.getOutputStream();
            money.write(outputStream);
            inputStream = socket.getInputStream();
            money.read(inputStream);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return money.getSum();
    }

}
