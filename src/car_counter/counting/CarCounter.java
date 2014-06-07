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

package car_counter.counting;

import java.nio.file.Path;
import java.util.Collection;

import org.joda.time.DateTime;

/**
 * The interface for the car counter.
 */
public interface CarCounter
{
    /**
     * Processes a video file looking for cars.
     *
     * @param video the path to the video to process.
     * @param startDateTime the video start date time.
     * @return a list of detected cars.
     */
    Collection<DetectedVehicle> processVideo(Path video, DateTime startDateTime);
}
