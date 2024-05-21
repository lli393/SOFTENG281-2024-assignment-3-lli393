package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    // HashMap for country and it's adjacencies
    Map<CountryInfo, String[]> map = new HashMap<>();
    // create country informaltion object for all
    for (int i = 0; i < countries.size(); i++) {
      // split the lines of countries
      String[] countryInfo = countries.get(i).split(",");
      // the format is country name, continent, tax amount
      CountryInfo country =
          new CountryInfo(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2]));
      String[] adjacentCountry = adjacencies.get(i).split(",");
      // put it into map
      map.put(country, adjacentCountry);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
