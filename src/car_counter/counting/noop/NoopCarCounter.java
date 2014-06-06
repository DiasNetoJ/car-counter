package car_counter.counting.noop;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

import car_counter.counting.CarCounter;
import car_counter.counting.DetectedVehicle;
import car_counter.counting.Direction;
import org.joda.time.DateTime;

/**
 * A car counter which does no processing of the video file.
 */
public class NoopCarCounter implements CarCounter
{
    @Override
    public Collection<DetectedVehicle> processVideo(Path video, DateTime startDateTime)
    {
        return Arrays.asList(new DetectedVehicle(startDateTime, Direction.UNKNOWN, Direction.UNKNOWN, null, null));
    }
}
