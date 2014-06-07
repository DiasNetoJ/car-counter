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
