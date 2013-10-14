import java.text.SimpleDateFormat;
import java.util.Date;

class ThreadLocalTest
{
    ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() 
    {
        public SimpleDateFormat initialValue()
        {
            return new SimpleDateFormat("YYYY-MM-dd");
        }
    };

    public static void main(String[] args)
    {
        ThreadLocalTest test = new ThreadLocalTest();
        Date now = new Date();
        System.out.println(test.sdf.get().format(now));
    }
}
