package nz.ac.auckland.se281;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  // intialise and declare fields
  // HashMap for country and it's infomations
  protected Map<String, String[]> map = new HashMap<>();
  // create a graph for BFS
  private Graph route = new Graph();

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
    // search vertically into each row
    for (int i = 0; i < countries.size(); i++) {
      // split the lines of countries
      String[] countryInfo = countries.get(i).split(",");
      // put it into map in the format of: (key)countryName, (value)name continent tax
      map.put(countryInfo[0], countryInfo);
      // split the lines of adjacencies
      String[] adjacenciesInfo = adjacencies.get(i).split(",");
      // for loop to add each adjacent country, from second element to last element
      // in column, horizontal direction
      for (int j = 1; j < adjacenciesInfo.length; j++) {
        route.addEdge(adjacenciesInfo[0], adjacenciesInfo[j]);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // declare fields
    // users input countryName for info
    String countryName;
    // if the input is valid
    boolean validInput = false;

    // add code here to ask for user's input
    MessageCli.INSERT_COUNTRY.printMessage();

    // while the input is not valid, do try catch until valid input
    while (!validInput) {
      try {
        // get users input
        countryName = Utils.scanner.nextLine();
        // check if country is in map, if not exception is thrown and catched
        doesCountryExist(Utils.capitalizeFirstLetterOfEachWord(countryName));
        // if no exception thrown, the input is valid
        validInput = true;
        // else successfully print country info
        MessageCli.COUNTRY_INFO.printMessage(
            Utils.capitalizeFirstLetterOfEachWord(countryName),
            map.get(Utils.capitalizeFirstLetterOfEachWord(countryName))[1],
            map.get(Utils.capitalizeFirstLetterOfEachWord(countryName))[2]);
      } catch (CountryDoesNotExistException e) {
        // print error message
        MessageCli.INVALID_COUNTRY.printMessage(Utils.capitalizeFirstLetterOfEachWord(countryName));
      }
    }
  }

  /**
   * method checks if country exist in the map, if not exception is thrown.
   *
   * @param countryName the name of the country
   * @throws CountryDoesNotExistException check if the country does not exist in the map
   */
  public void doesCountryExist(String countryName) throws CountryDoesNotExistException {
    // if it doesnt contain country
    if (!map.containsKey(countryName)) {
      throw new CountryDoesNotExistException();
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // declare fields
    String sourceCountry;
    String destinationCountry;
    // check if source and destination input is valid
    boolean sourceValid = false;
    boolean destinationValid = false;
    int tax;

    // ask user for source
    MessageCli.INSERT_SOURCE.printMessage();

    while (!sourceValid) {
      // check if source country from user's input are valid
      try {
        // get input and capitalise first letter of each word
        sourceCountry = Utils.scanner.nextLine();
        // if exists continue, if not exception thrown
        doesCountryExist(Utils.capitalizeFirstLetterOfEachWord(sourceCountry));
        // exception is not thrown, input is valid
        sourceValid = true;

      } catch (CountryDoesNotExistException e) {
        // print error message
        MessageCli.INVALID_COUNTRY.printMessage(
            Utils.capitalizeFirstLetterOfEachWord(sourceCountry));
      }
    }
    // ask user for destination
    MessageCli.INSERT_DESTINATION.printMessage();
    while (!destinationValid) {
      // check if destination country from user's input are valid
      try {
        destinationCountry = Utils.scanner.nextLine();
        doesCountryExist(Utils.capitalizeFirstLetterOfEachWord(destinationCountry));
        destinationValid = true;

      } catch (CountryDoesNotExistException e) {
        // print error message
        MessageCli.INVALID_COUNTRY.printMessage(
            Utils.capitalizeFirstLetterOfEachWord(destinationCountry));
      }
    }

    // get the shortest path
    List<String> countryPath =
        route.detectPathFromSource(
            Utils.capitalizeFirstLetterOfEachWord(sourceCountry),
            Utils.capitalizeFirstLetterOfEachWord(destinationCountry));

    // calculate tax
    tax = route.getTotalTax(map, countryPath);
    // if tax is 0, no crossborder and the rest of the code will not be run
    if (tax == 0) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }

    // display the fastest route of countries
    MessageCli.ROUTE_INFO.printMessage(countryPath.toString());

    // get the unique ordered continents for countries in shortest path
    Set<String> continentPath = route.getContinentInfo(map, countryPath);
    // ordered list of continents visited(source country included)
    MessageCli.CONTINENT_INFO.printMessage(continentPath.toString());

    // total cross border taxes to pay(source country excluded)
    MessageCli.TAX_INFO.printMessage(Integer.toString(tax));
  }
}
