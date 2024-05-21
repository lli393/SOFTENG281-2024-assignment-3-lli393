package nz.ac.auckland.se281;

/** Creates an object contains infomation for individual country. (name, continent, tax) */
public class CountryInfo {
  // fields
  String name;
  String continent;
  int tax;

  public CountryInfo(String name, String continent, int tax) {
    this.name = name;
    this.continent = continent;
    this.tax = tax;
  }
}
