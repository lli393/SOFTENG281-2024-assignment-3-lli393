package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  // intialise and declare fields
  // HashMap for country and it's infomations
  Map<String, String[]> map = new HashMap<>();

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
      // put it into map in the format of: (key)countryName, (value)name continent tax
      map.put(countryInfo[0], countryInfo);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // declare fields
    // users input countryName for info
    String countryName = null;
    // if the input is valid
    boolean validInput = false;

    // add code here to ask for user's input
    MessageCli.INSERT_COUNTRY.printMessage();

    // while the input is not valid, do try catch until valid input
    while (!validInput) {
      try {
        // get users input
        countryName = Utils.scanner.nextLine();
        // capitalise the first letter
        countryName = Utils.capitalizeFirstLetterOfEachWord(countryName);
        // check if country is in map, if not exception is thrown and catched
        doesCountryExist(countryName);
        // if no exception thrown, the input is valid
        validInput = true;
      } catch (CountryDoesNotExistException e) {
        // print error message
        MessageCli.INVALID_COUNTRY.printMessage(countryName);
      }
    }
  }

  /** method checks if country exist in the map, if not exception is thrown */
  public void doesCountryExist(String countryName) throws CountryDoesNotExistException {
    // if it doesnt contain country
    if (!map.containsKey(countryName)) {
      throw new CountryDoesNotExistException();
    }
    // else successfully print country info
    MessageCli.COUNTRY_INFO.printMessage(
        countryName, map.get(countryName)[1], map.get(countryName)[2]);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
