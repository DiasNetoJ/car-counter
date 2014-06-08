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

package car_counter;

import java.io.File;

import car_counter.processing.DefaultProcessor;
import com.beust.jcommander.JCommander;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.ini4j.Wini;

/**
 * The main entry point.
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            BasicConfigurator.configure(); // TODO: config from options

            // Parse the command line options
            CliOptions options = new CliOptions();
            new JCommander(options, args);

            if (StringUtils.isBlank(options.configFile))
            {
                options.configFile = "/etc/car-counter/car-counter.conf";
            }

            // Read in the configuration
            Wini ini = new Wini();
            ini.getConfig().setMultiOption(true);
            ini.load(new File(options.configFile));

            new DefaultProcessor(ini).process();
        }
        catch (Exception e)
        {
            Logger.getLogger(Main.class).fatal("Unhandled exception - quitting", e);
        }
    }
}
