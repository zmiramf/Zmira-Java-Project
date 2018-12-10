package com.company;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ClientThread extends Thread{

    public static final double EURO = 4.22;
    public static final double DOLLAR = 3.73;
    public static final double POUND_STERLING = 4.7;
    public static final double JAPANESE_YEN = 3.2;
    public static final double KOREAN_WON = 300.382;
    public static final double THAILAND_BAHT = 8.824;
    public static final double POLISH_ZLOTY = 1.016;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    public static Map<Money, String> history = new HashMap<>();

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //Doing the Calculate and get the object from client
            Money money = new Money(inputStream);

            //add what we get to a currency conversion history and send it to a file
            history.put(money, String.valueOf(money.getSum()));
            File file = new File("C:\\Users\\zmira\\Documents\\money.txt");
            writeMoneyHistoryToFile(history, file);


            double sum = money.getSum();
            String type = money.getType();
            double calculateConversion = 0;
            switch (type){
                case "euro":
                    calculateConversion = sum / EURO;
                    break;
                case "dollar":
                    calculateConversion = sum / DOLLAR;
                    break;
                case "Pound Sterling":
                    calculateConversion = sum / POUND_STERLING;
                    break;
                case "Japanese Yen":
                    calculateConversion = sum * JAPANESE_YEN;
                    break;
                case "Korean Won":
                    calculateConversion = sum * KOREAN_WON;
                    break;
                case "Thailand Baht":
                    calculateConversion = sum * THAILAND_BAHT;
                    break;
                case "Polish Zloty":
                    calculateConversion = sum * POLISH_ZLOTY;
                    break;
            }
            //printing what the server get
            System.out.println(money.getType());
            System.out.println(money.getSum());
            //changing the sum and sent the object back to client
            money.setSum(calculateConversion);
            money.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    }
    //writing our money objects to a file
    public static void writeMoneyHistoryToFile(Map<Money,String> history, File file){
        OutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(file);
            for ( Money key : history.keySet() ) {
                key.write(outputStream);
            }
        } catch (FileNotFoundException e) {
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
        }
    }


}


