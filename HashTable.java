import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashSet;

public class HashTable
{
    public static void segments()
    {
        int segmentShift = 28;
        int segmentMask = 15;
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = Integer.MIN_VALUE; i <= Integer.MAX_VALUE; i++) {
            int j = (i >>> segmentShift) & segmentMask;
            set.add(j);
        }
        System.out.println(set);
    }

    public static void main(String[] args)
    {
        ConcurrentMap<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();

        int segmentShift = 28;
        int segmentMask = 15;
        int hash = -1;
        int j = (hash >>> segmentShift) & segmentMask;
        System.out.println("segmentMask = " + Integer.toBinaryString(segmentMask));
        System.out.println("segmentShift = " + Integer.toBinaryString(segmentShift));
        System.out.println("j = " + j);

        // Int.MAX_VALUE = 01111111 11111111 11111111 11111111
        //            -1 = 11111111 11111111 11111111 11111111
        System.out.println("hash = " + Integer.toBinaryString(hash));
        int i = hash >>> segmentShift;
        System.out.println("hash >>> segmentShift = " + Integer.toBinaryString(i));

        segments();
    }
}
