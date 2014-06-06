package car_counter.util;

/**
 * Thread utils.
 */
public class ThreadUtils
{
    public static void sleepMillis(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted sleeping", e);
        }
    }
}
