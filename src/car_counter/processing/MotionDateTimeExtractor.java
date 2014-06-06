package car_counter.processing;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * A date/time extractor for motion named files.
 */
public class MotionDateTimeExtractor implements DateTimeExtractor
{
    private Pattern fileRegex = Pattern.compile("\\d+\\-(\\d+)\\.avi");

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    @Override
    public DateTime getDateTime(Path videoFile)
    {
        Matcher matcher = fileRegex.matcher(videoFile.getFileName().toString());

        if (!matcher.matches())
        {
            throw new IllegalStateException("Match failed: " + videoFile);
        }

        // 445-20140522221556.avi
        return dateTimeFormatter.parseDateTime(matcher.group(1));
    }
}
