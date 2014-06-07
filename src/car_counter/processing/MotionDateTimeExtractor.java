/*
 * Copyright 2014 Luke Quinane
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
