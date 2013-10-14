import java.util.concurrent.atomic.AtomicInteger;

public class NumberRange
{
    private final AtomicInteger lower = new AtomicInteger(0);
    private final AtomicInteger upper = new AtomicInteger(0);

    public void setLower(int i)
    {
        if (i <= upper.get()) {
            int old = lower.get();
            lower.compareAndSet(old, i);
        }
    }

    public void setUpper(int i)
    {
        if (i >= lower.get()) {
            int old = upper.get();
            upper.compareAndSet(old, i);
        }
    }

    @Override
    public String toString()
    {
        return "(" + lower + "," + upper + ")";
    }

    public static void main(String[] args)
    {
        final NumberRange nr = new NumberRange();
        nr.setLower(0);
        nr.setUpper(10);
        nr.setLower(4);
        System.out.println(nr);
    }
}
