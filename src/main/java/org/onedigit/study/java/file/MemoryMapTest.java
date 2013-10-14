package org.onedigit.study.java.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

public class MemoryMapTest
{
    private static String fileName = "/Library/Java/JavaVirtualMachines/jdk1.7.0_21.jdk/Contents/Home/jre/lib/rt.jar";
    
    public long checksumMemoryMapped()
    {
        CRC32 crc = new CRC32();
        try (FileInputStream fis = new FileInputStream(fileName); FileChannel fc = fis.getChannel()) {
            long len = fc.size();
            System.out.println("len = " + len + " bytes");
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int p = 0; p < (int)len; p++) {
                int c = buffer.get(p);
                crc.update(c);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return crc.getValue();
    }
    
    public long checksumBuffered()
    {
        CRC32 crc = new CRC32();
        try (InputStream fis = new BufferedInputStream(new FileInputStream(fileName))) {
            int c;
            while ((c = fis.read()) != -1) {
                crc.update(c);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return crc.getValue();
    }
    
    public static void main(String[] args) 
    {
        MemoryMapTest test = new MemoryMapTest();
        long start = System.nanoTime();
        long crc = test.checksumMemoryMapped();
        long end = System.nanoTime();
        long elapsed = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
        System.out.println("CRC = " + crc);
        System.out.println("Memory Mapped Elapsed = " + elapsed);
        
        start = System.nanoTime();
        crc = test.checksumBuffered();
        end = System.nanoTime();
        elapsed = TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS);
        System.out.println("CRC = " + crc);
        System.out.println("Buffered Elapsed = " + elapsed);        
    }
}
