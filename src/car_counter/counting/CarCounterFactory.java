package car_counter.counting;

import car_counter.counting.noop.NoopCarCounter;
import car_counter.counting.opencv.OpencvCarCounter;
import org.ini4j.Wini;

/**
 * A factory for {@link CarCounter} implementations.
 */
public class CarCounterFactory
{
    /**
     * Creates a car counter instance.
     *
     * @param ini the configuration file.
     * @return the car counter.
     */
    public CarCounter create(Wini ini)
    {
        String implementation = ini.get("Counting", "implementation", String.class);

        if ("opencv".equals(implementation))
        {
            return new OpencvCarCounter();
        }
        else if ("noop".equals(implementation))
        {
            return new NoopCarCounter();
        }
        else
        {
            throw new IllegalStateException("Unknown counting backend: " + implementation);
        }
    }
}
