package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  // intialise and declare fields
  // HashMap for country and it's adjacencies
  Map<CountryInfo, String[]> map = new HashMap<>();
  // List for countries
  List<CountryInfo> countryList = new ArrayList<>();

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create data structures
    // create country informaltion object for all
    for (int i = 0; i < countries.size(); i++) {
      // split the lines of countries
      String[] countryInfo = countries.get(i).split(",");
      // the format is country name, continent, tax amount
      CountryInfo country =
          new CountryInfo(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2]));
      // add it to country array
      countryList.add(country);
      String[] adjacentCountry = adjacencies.get(i).split(",");
      // put it into map
      map.put(country, adjacentCountry);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // declare fields
    String countryName;
    // add code here to ask for user's input
    MessageCli.INSERT_COUNTRY.printMessage();
    // fetch user input
    countryName = Utils.scanner.nextLine();
    // search for the countryInfo in countryList
    for (CountryInfo countryInfo : countryList) {
      if (countryInfo.getName().equals(countryName)) {
        // successfully print country info
        MessageCli.COUNTRY_INFO.printMessage(
            countryInfo.getName(), countryInfo.getContinent(), countryInfo.getTax());
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
