package org.onedigit.study.ikm;

import java.util.ArrayList;
import java.util.List;

class ParentOld
{
    
    static class Processor
    {
        List<String> seq;
        public void setUp()
        {
            try {
                establish();
            } finally {
                cleanup();
                System.out.println("cleaned up");
            }
        }
        
        private void cleanup()
        {
            if (seq.size() > 0) {
                System.out.println("size > 0");
            }
        }
        
        private void establish()
        {
            if (true) {
                throw new IllegalArgumentException();
            }
            seq = new ArrayList<>();
        }
    }
    
    static class Superclass
    {
        Superclass()
        {
            this(0);
            System.out.println("super");
            System.out.println("1");
        }
        
        Superclass(int x) {
            System.out.println("super int x = " + x);
            System.out.println("2" + x);
        }
    }
    
    static class Subclass extends Superclass
    {
        Subclass(int x) {
            // super(x);
            System.out.println("sub int");
            System.out.println("3" + x);
        }
        
        Subclass(int x, int y) {
            this(x);
            System.out.println("4" + x + y);
        }
    }
    
    public static void main(String... args)
    {
        // new Subclass(2, 3);
        Processor p = new Processor();
        p.setUp();
    }
}