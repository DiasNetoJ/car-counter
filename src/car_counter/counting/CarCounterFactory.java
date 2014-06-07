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
