object GDPAnalyzer {
  def main(args: Array[String]): Unit = {
    val filePath = "src/main/scala/gdp.txt"  // Replace with the actual file path

    val helper = new GDPAnalyzerHelper(filePath)
    val gdpData = helper.readGDPData()
    val gdpDataMalaysia = helper.readGDPDataMalaysia()

    val excludedRegions = helper.getExcludedRegions()

    // Exclude regions when finding the countries with the highest and lowest GDP
    val filteredData = gdpData.filter { case (country, _) =>
      !excludedRegions.contains(country)
    }

    // Check if filteredData is not empty
    if (filteredData.nonEmpty) {
      val maxGDP = helper.findCountryWithHighestGDP(filteredData)

      maxGDP match {
        case Some(country) => println(s"The country with the highest GDP is: $country")
        case None => println("No country found with the highest GDP.")
      }


    } else {
      println("No valid GDP data found.")
    }

    val malaysiaAverageGDP = helper.calculateAverageGDPPerCapita(gdpDataMalaysia, "Malaysia")
    malaysiaAverageGDP match {
      case Some(averageGDP) => println(f"The average GDP per capita for Malaysia is: $averageGDP%.2f")
      case None => println("No GDP data found for Malaysia.")
    }


    val numCountriesWithLowestGDP = 5
    val countriesWithLowestGDP = helper.findCountriesWithLowestAverageGDP(gdpData, numCountriesWithLowestGDP)
    println(s"The $numCountriesWithLowestGDP countries with the lowest average GDP per capita:")
    countriesWithLowestGDP.foreach(println)
  }
}
