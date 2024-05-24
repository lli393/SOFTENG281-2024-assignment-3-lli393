package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  // intialise and declare fields
  // HashMap for country and it's infomations
  Map<String, String[]> map = new HashMap<>();
  // create a graph for BFS
  Graph<String> route = new Graph<>();

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
      // split the lines of adjacencies
      String[] adjacenciesInfo = adjacencies.get(i).split(",");
      // for loop to add each adjacent country, from second element to last element
      for (int j = 1; j < adjacenciesInfo.length; j++) {
        route.addEdge(adjacenciesInfo[0], adjacenciesInfo[j]);
      }
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
        // else successfully print country info
        MessageCli.COUNTRY_INFO.printMessage(
            countryName, map.get(countryName)[1], map.get(countryName)[2]);
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
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // declare fields
    String sourceCountry = null;
    String destinationCountry = null;
    String countryName = null;
    boolean validInput = false;

    int tax = 0;

    while (!validInput) {
      // check if both country from user's input are valid
      try {
        // ask user for source and destination
        MessageCli.INSERT_SOURCE.printMessage();
        sourceCountry = Utils.scanner.nextLine();
        sourceCountry = Utils.capitalizeFirstLetterOfEachWord(sourceCountry);
        countryName = sourceCountry;
        doesCountryExist(sourceCountry);

        MessageCli.INSERT_DESTINATION.printMessage();
        destinationCountry = Utils.scanner.nextLine();
        destinationCountry = Utils.capitalizeFirstLetterOfEachWord(destinationCountry);
        countryName = destinationCountry;
        doesCountryExist(destinationCountry);
        validInput = true;
      } catch (CountryDoesNotExistException e) {
        // print error message
        MessageCli.INVALID_COUNTRY.printMessage(countryName);
      }
    }

    // get the shortest path
    List<String> countryPath = route.detectPathFromSource(sourceCountry, destinationCountry);
    // The fastest route of country
    MessageCli.ROUTE_INFO.printMessage(countryPath.toString());
    // ordered list of continents visited(starting one included)
    MessageCli.CONTINENT_INFO.printMessage(countryPath.toString());

    // total cross border taxes to pay(starting one excluded)
    MessageCli.TAX_INFO.printMessage(Integer.toString(tax)); // tax num inside
  }
}
