package resources;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class takes the YAML information of the different Enigma machines (currently stored into WiringInformation.yaml
 * and converts it to a form capable of being used by the rest of the program.
 * <br>
 * @author Eliezer Meth
 * @version 1<br>
 * Start Date: 2024-08-28<br>
 * Last Modified: 2024-08-30
 */
public class VersionInformationCompiler
{
    // Data held in WiringInformation
    Map<String, Map<String, Object>> rawData = new HashMap<>();

    List<String> versionMenu = new ArrayList<>(); // list of versions

    /**
     * Constructor; reads in raw version information from file.
     */
    public VersionInformationCompiler()
    {
        // Create new Yaml instance
        Yaml yaml = new Yaml();

        // Load Yaml file as Map
        InputStream inputStream =
                VersionInformationCompiler.class.getClassLoader().getResourceAsStream("WiringInformation.yaml");
        Map<String, Object> yamlMap = yaml.load(inputStream);

        // Get list of all versions
        List<Map<String, Object>> versions = (List<Map<String, Object>>) yamlMap.get("versions");

        // Scrape order of keys into versionMenu
        for (Map<String, Object> version : versions)
            versionMenu.add((String) version.get("name"));

        // Created flattened map of versions
        for (Map<String, Object> version : versions)
            rawData.put((String) version.get("name"), version);
    }

    /**
     * Get a list of versions available for Enigma.
     * @return List.
     */
    public ArrayList getVersionMenu()
    {
        return new ArrayList<>(versionMenu);
    }

    public String getVersion(String version)
    {
        // test that version is available
        if (!versionMenu.contains(version))
            return null;

        // TODO when classes are modified to accept correct alphabet; etc.
        return null;
    }
}
