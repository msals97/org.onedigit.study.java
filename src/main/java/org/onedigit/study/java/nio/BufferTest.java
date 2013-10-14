package org.onedigit.study.java.nio;

import java.nio.ByteBuffer;

public class BufferTest
{
    public static void main(String[] args)
    {
        ByteBuffer buffer = ByteBuffer.allocate(16);      
        buffer.putInt(3);
        buffer.putInt(4);
        buffer.putInt(5);
        // buffer.putInt(6);
        // In write mode, the capacity and limit are both the same.
        System.out.println("buffer capacity = " + buffer.capacity());
        System.out.println("buffer limit = " + buffer.limit());
        System.out.println("buffer position = " + buffer.position());

        buffer.flip();
        // After flip to read mode, position is set to 0.
        // In read mode, the capacity is the same as in write mode
        // but limit is the actual amount of data that can be read.
        // i.e. limit is set to the position in write mode.
        System.out.println("buffer capacity = " + buffer.capacity());
        System.out.println("buffer limit = " + buffer.limit());
        System.out.println("buffer position = " + buffer.position());
        for (int i = 0; i < 2; i++) {
            System.out.println(buffer.getInt());
        }
        
        System.out.println("---------------------------");
        // put position back to 0.
        buffer.rewind();
        // while (buffer.hasRemaining()) {
        for (int i = 0; i < 2; i++) {
            System.out.println(buffer.getInt());
        }
        
        // call compact.  This copies all unread data to the beginning
        // of the buffer. Then sets position right after the last element.
        // Limit is set to capacity.
        buffer.compact();
        buffer.putInt(10);
        buffer.putInt(12);
        
        System.out.println("---------------------------");
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.getInt());
        }
        
        // call clear. This sets position to 0 and limit to capacity.
        // We can now start writing from the beginning.
        buffer.clear();
    }
}
