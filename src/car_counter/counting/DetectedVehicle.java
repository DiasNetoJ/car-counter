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

import java.awt.Color;

import org.joda.time.DateTime;

/**
 * A vehicle or person detected while processing the video.
 */
public class DetectedVehicle
{
    /**
     * The date / time the vehicle was detected.
     */
    private final DateTime dateTime;

    /**
     * The initial location of the vehicle.
     */
    private final Direction initialLocation;

    /**
     * The end location of the vehicle.
     */
    private final Direction endLocation;

    /**
     * The average speed the vehicle is estimated to be travelling at (in km/h), or {@code null} if no estimate is
     * available.
     */
    private final Float speed;

    /**
     * The detected colour of the vehicle, or {@code null} if no detection was made.
     */
    private final Color colour;

    public DetectedVehicle(DateTime dateTime, Direction initialLocation, Direction endLocation, Float speed,
                           Color colour) {
        this.dateTime = dateTime;
        this.initialLocation = initialLocation;
        this.endLocation = endLocation;
        this.speed = speed;
        this.colour = colour;
    }
}
