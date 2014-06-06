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
            Wini ini = new Wini(new File(options.configFile));

            new DefaultProcessor(ini).process();
        }
        catch (Exception e)
        {
            Logger.getLogger(Main.class).fatal("Unhandled exception - quitting", e);
        }
    }
}
