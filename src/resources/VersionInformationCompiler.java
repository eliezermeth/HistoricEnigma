package resources;

import machine_pieces.EntryWheel;
import machine_pieces.Plugboard;
import machine_pieces.Reflector;
import machine_pieces.Rotor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
 * Last Modified: 2024-09-16
 */
public class VersionInformationCompiler
{
    // Data held in WiringInformation
    Map<String, Map<String, Object>> rawData = new HashMap<>();

    List<String> versionMenu = new ArrayList<>(); // list of versions

    AlphabetConverter ac;

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

        Map<String, Object> versionData = rawData.get(version);

        // create AlphabetConverter
        AlphabetConverter.createAlphabetConverter((String) versionData.get("alphabet"));
        ac = AlphabetConverter.getAlphabetConverter();

        Map<String, Object> components = (Map<String, Object>) versionData.get("components");

        // create EntryWheel
        // TODO make sure wiring is set correctly between keyboard and rotor
        EntryWheel ew = new EntryWheel(EntryWheel.ETWsequence.CUSTOM);
        ew.set(ac.getAlphabetString(), (String) components.get("ETW"));

        // create Plugboard
        Plugboard plugboard = new Plugboard(ac.getAlphabet());
        Map<String, Object> plugboardDetails = (Map<String, Object>) components.get("plugboard");
        // plugboard.setMaxConnections((Integer) plugboardDetails.get("max-connections"); // set max connection
        // if (!(Boolean) plugboardDetail.get("installed")) plugboard.lock(); // if plugboard not installed, lock to prevent modification

        // create Rotors selection
        Map<String, Rotor> rotorList = new HashMap<>();
        // access Rotors list
        List<Map<String, Object>> rotorsYAML = (List<Map<String, Object>>) components.get("rotors");
        // TODO modify how Rotors can be implemented
        for (Map<String, Object> rotor : rotorsYAML)
        {
            String name = (String) rotor.get("name");
            String wiring = (String) rotor.get("wiring");
            String turnover = (String) rotor.get("turnover");
            String notch = (String) rotor.get("notch");
            boolean stepping = (Boolean) rotor.get("stepping");
            boolean rotatable = (Boolean) rotor.get("rotatable");

            // TODO
        }

        // create Reflector selection
        Map<String, Reflector> reflectorList = new HashMap<>();
        // access Reflectors list
        List<Map<String, Object>> reflectorsYAML = (List<Map<String, Object>>) components.get("reflectors");
        // TODO modify how Reflector can be implemented
        for (Map<String, Object> reflector : reflectorsYAML)
        {
            String name = (String) reflector.get("name");
            String wiring = (String) reflector.get("wiring");
            boolean rotatable = (Boolean) reflector.get("rotatable");
            boolean stepping = (Boolean) reflector.get("stepping");
            boolean rewiring = (Boolean) reflector.get("rewiring");
        }

        int numRotorSlots = (Integer) versionData.get("rotor-slots");
        String comments = (String) versionData.get("comments");

        return null;
    }
}
