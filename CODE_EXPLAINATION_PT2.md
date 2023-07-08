#Code Explaination : GDPAnalyzerHelper

Certainly! Here are the code snippets with explanations for each method in the `GDPAnalyzerHelper` class:

1. `readGDPData`:
```scala
def readGDPData(): List[(String, Double)] = {
  val reader = new CSVReader(new FileReader(filePath))
  val lines = reader.readAll().asScala.toList
  reader.close()

  val gdpData: List[(String, Double)] = lines.tail.flatMap { line =>
    val country = line(1)
    val gdp = line(4).replaceAll(",", "").toDoubleOption
    gdp.map(country -> _)
  }

  val filteredData = gdpData.filterNot { case (country, _) =>
    country == "Total, all countries or areas" || isRegion(country)
  }

  filteredData
}
```
Explanation: This method reads GDP data from a CSV file. It creates a `CSVReader` object to read the lines from the file. The lines are converted to a Scala list using the `asScala` method, and the CSV reader is closed. The method then uses `flatMap` to transform each line into a tuple `(country, gdp)` by extracting the country name (`line(1)`) and GDP value (`line(4)`). The GDP value is cleaned by removing commas and converting it to a `Double` using `toDoubleOption`. The resulting list of tuples is filtered to exclude lines with country names equal to "Total, all countries or areas" or regions determined by the `isRegion` method. The filtered GDP data is returned as a list of tuples `(String, Double)`.

2. `readGDPDataMalaysia`:
```scala
def readGDPDataMalaysia(): List[(String, Double)] = {
  val reader = new CSVReader(new FileReader(filePath))
  val lines = reader.readAll().asScala.toList
  reader.close()

  val gdpData: List[(String, Double)] = lines.flatMap { line =>
    val country = line(1)
    val series = line(3)
    val gdp = line(4).replaceAll(",", "").toDoubleOption
    if (country == "Malaysia" && series == "GDP per capita (US dollars)") gdp.map(country -> _) else None
  }

  gdpData
}
```
Explanation: This method reads GDP data specific to Malaysia from the CSV file. It follows a similar process as `readGDPData`, but in this case, it only includes lines where the country is "Malaysia" and the series is "GDP per capita (US dollars)". The GDP data for Malaysia is extracted and returned as a list of tuples `(String, Double)`.

3. `getExcludedRegions`:
```scala
def getExcludedRegions(): Set[String] = {
  val reader = new CSVReader(new FileReader(regionsFilePath))
  val lines = reader.readAll().asScala.map(_.head).toSet
  reader.close()
  lines
}
```
Explanation: This method reads excluded regions from a file specified by `regionsFilePath`. It creates a `CSVReader` object to read the lines from the file. The lines are converted to a Scala collection using the `asScala` method, and only the first element of each line is extracted using `.head`. The resulting strings are converted to a `Set` and returned as the set of excluded regions.

4. `findCountryWithHighestGDP`:
```scala
def findCountryWithHighestGDP(data: List[(String, Double)]): Option[String] = {
  if (data.nonEmpty) {
    val maxGDP = data.maxBy(_._2)
    Some(maxGDP._1)
  } else {
    None
  }
}
```
Explanation: This method finds the country with the highest GDP from the given list of tuples `(String, Double)`. It first checks if the input data list is not empty. If not empty, it uses the `maxBy` method to find the tuple with the maximum GDP value based on the second element of each tuple (`_._2`). It returns the country name as an `Option[String]`. If the input list is empty, it returns `None`.

5. `isRegion`:
```scala
private def isRegion(country: String): Boolean = {
  val regions = List("Africa", "Asia", "Europe", "North America", "South America", "Oceania",
    "Middle East", "Caribbean", "Central America", "Sub-Saharan Africa", "Eastern Europe",
    "Western Europe", "Northern Europe", "Southern Europe", "Central Asia", "Southeast Asia",
    "South Asia", "East Asia", "North Africa", "West Africa", "Central Africa", "East Africa",
    "Southern Africa", "Caribbean Islands", "Polynesia", "Micronesia", "Melanesia", "Nordic Countries")

  regions.contains(country)
}
```
Explanation: This is a private helper method that checks if a given country name corresponds to a region. It creates a list of predefined regions and uses the `contains` method to check if the given country name is present in the list. It returns `true` if the country name matches any of the regions, indicating that the country is a region.

6. `calculateAverageGDPPerCapita`:
```scala
def calculateAverageGDPPerCapita(data: List[(String, Double)], country: String): Option[Double] = {
  val countryData = data.filter(_._1 == country)
  val gdpList = countryData.map(_._2)

  if (gdpList.nonEmpty) {
    val averageGDP = gdpList.sum / gdpList.length
    Some(averageGDP)
  } else {
    None
  }
}
```
Explanation: This method calculates the average GDP per capita for a given country using the data in the list of tuples `(String, Double)`. It first filters the tuples based on the country name (`_._1 == country`) to obtain the GDP data for the specified country. The GDP values are extracted using `.map(_._2)`, and the method checks if the resulting `gdpList` is not empty. If not empty, it calculates the average GDP per capita by summing the GDP values and dividing by the number of values. The result is returned as an `Option[Double]`. If the filtered list is empty, indicating no GDP data for the country, it returns `None`.

7. `findCountriesWithLowestAverageGDP`:
```scala
def findCountriesWithLowestAverageGDP(data: List[(String, Double)], numCountries: Int): List[String] = {
  val countryAverages = data
    .groupBy(_._1)
    .mapValues(list => list.map(_._2).sum / list.length)
    .toList

  val sortedCountries = countryAverages.sortBy(_._2)
  val lowestCountries = sortedCountries.take(numCountries).map(_._1)

  lowestCountries
}
```
Explanation: This method finds the specified number of countries with the lowest average GDP per capita from the given list of tuples `(String, Double)`. It first groups the tuples by country using `groupBy(_._1)`. Then, it calculates the average GDP per capita for each country by summing the GDP values (`list.map(_._2).sum`) and dividing by the

number of values (`list.length`). The results are stored in `countryAverages` as a list of tuples `(String, Double)`. The `countryAverages` list is then sorted based on the average GDP values using `sortBy(_._2)`. Finally, the method selects the specified number of countries with the lowest average GDP values using `take(numCountries)` and extracts the country names (`_._1`). The resulting country names are returned as a list.

These methods provide various functionalities for reading and processing GDP data, including extracting specific data, filtering based on conditions, calculating averages, finding countries with extreme values, and more.
