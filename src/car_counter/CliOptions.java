package car_counter;

import com.beust.jcommander.Parameter;

/**
 * Command line argument options
 */
public class CliOptions
{
    @Parameter(names = { "-c", "--config" }, description = "Sets the location of the configuration file.")
    public String configFile;
}
