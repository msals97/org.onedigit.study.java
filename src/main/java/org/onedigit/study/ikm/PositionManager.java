package org.onedigit.study.ikm;


public class PositionManager
{
    int hp = 0;
    int vp = 0;
    
    class Inner
    {
        public Inner() {
            PositionManager.this.hp = 0;
        }
    }
    
    public PositionManager init()
    {
        hp = vp = 1;
        return this;
    }
    
    public PositionManager moveRight(int h)
    {
        hp += h;
        System.out.println("hp = " + hp);
        return this;
    }
    
    public PositionManager moveUp(int v)
    {
        vp += v;
        return this;
    }
    
    public PositionManager print()
    {
        switch(hp % 2) {
        case 0:
            System.out.println("Zone A;");
            break;
        case 1:
            System.out.println("Zone B;");
            break;
        }
        
        switch(vp % 2) {
        case 0:
            System.out.println("Zone C;");
            break;
        case 1:
            System.out.println("Zone D;");
            break;
        }
        return this;
    }
    
    static char[] getCharArray(byte[] arr)
    {
        char[] carr = new char[4];
        int i = 0;
        for (byte c : arr) {
            carr[i] = (char)c++;
            System.out.println("carr[i] = " + (int)carr[i]);
            i++;
        }
        return carr;
    }
    
    public static void main(String... args)
    {
        // new PositionManager().init().moveRight(-3).moveUp(9).print();
        
        byte arr[] = new byte[] {2, 3, 4, 5};
        for (final int i : getCharArray(arr)) {
            System.out.println(i + " ");
        }
    }
}
