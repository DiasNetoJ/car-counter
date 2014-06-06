package car_counter.processing;

import java.nio.file.Path;

import org.joda.time.DateTime;

/**
 * A date/time extractor.
 */
public interface DateTimeExtractor
{
    /**
     * Gets the start date/time for the given video file.
     *
     * @param videoFile the video file.
     * @return the start date/time.
     */
    DateTime getDateTime(Path videoFile);
}
