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
