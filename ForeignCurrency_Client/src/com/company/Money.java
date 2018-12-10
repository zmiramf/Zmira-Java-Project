package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Money implements Writable{
    private double sum;
    private String type;

    public Money(double sum, String type) {
        this.sum = sum;
        this.type = type;
    }
    public Money(InputStream inputStream) throws IOException{
        read(inputStream);
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        byte[] sumBytes = new byte[8];
        ByteBuffer.wrap(sumBytes).putDouble(sum);
        outputStream.write(sumBytes);
        byte[] typeBytes = type.getBytes();
        outputStream.write(type.length());
        outputStream.write(typeBytes);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        byte[] sumBytes = new byte[8];
        int actuallyRead = inputStream.read(sumBytes);
        if(actuallyRead != 8)
            throw new IOException("error reading sum from stream, no eight bytes ");
        sum = ByteBuffer.wrap(sumBytes).getDouble();
        int typeLength = inputStream.read();
        if(typeLength == -1)
            return;
        byte[] typeBytes = new byte[typeLength];
        actuallyRead = inputStream.read(typeBytes);
        if(actuallyRead != typeLength)
            throw new IOException("more bytes needed");
        type = new String(typeBytes);

    }
}
