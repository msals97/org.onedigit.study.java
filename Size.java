import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

public class Size
{
    public static Unsafe getUnsafe()
    {
        Unsafe unsafe = null;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
            // Ignore
        }
        return unsafe;
    } 

    /**
      * This is not accurate.
      * User java.lang.instrument package for more accurate information.
      */
    public static long sizeof(Object o) {
        Unsafe u = getUnsafe();
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = u.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize/8) + 1) * 8;   // padding
    }

    /**
      * This only works on a 32 bit JVM
      */
    /*
    public static long sizeof(Object o)
    {
        return getUnsafe().getAddress(normalise(getUnsafe().getInt(o, 4L)) + 12l);
    }
    */
    
    private static long normalise(int value)
    {
        if (value >= 0) return value;
        return (~0L >>> 32) & value;
    }    

    public static void main(String[] args)
    {
        Integer[] a = new Integer[8];
        System.out.println("sizeof a = " + Size.sizeof(a));
    }
}
