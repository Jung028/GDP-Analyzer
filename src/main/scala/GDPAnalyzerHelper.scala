import com.opencsv.CSVReader
import java.io.FileReader
import scala.collection.JavaConverters._

class GDPAnalyzerHelper(filePath: String) {
  def readGDPData(): List[(String, Double)] = {

    val reader = new CSVReader(new FileReader(filePath))
    val lines = reader.readAll().asScala.toList
    reader.close()

    val gdpData: List[(String, Double)] = lines.tail.flatMap { line =>
      val country = line(1)
      val gdp = line(4).replaceAll(",", "").toDoubleOption
      gdp.map(country -> _)
    }

    // Exclude "Total, all countries or areas" and regions from the analysis
    val filteredData = gdpData.filterNot { case (country, _) =>
      country == "Total, all countries or areas" || isRegion(country)
    }

    filteredData
  }

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


  val regionsFilePath = "src/main/scala/regions.txt"

  def getExcludedRegions(): Set[String] = {
    val reader = new CSVReader(new FileReader(regionsFilePath))
    val lines = reader.readAll().asScala.map(_.head).toSet
    reader.close()
    lines
  }



  def findCountryWithHighestGDP(data: List[(String, Double)]): Option[String] = {
    if (data.nonEmpty) {
      val maxGDP = data.maxBy(_._2)
      Some(maxGDP._1)
    } else {
      None
    }
  }



  private def isRegion(country: String): Boolean = {
    val regions = List("Africa", "Asia", "Europe", "North America", "South America", "Oceania",
      "Middle East", "Caribbean", "Central America", "Sub-Saharan Africa", "Eastern Europe",
      "Western Europe", "Northern Europe", "Southern Europe", "Central Asia", "Southeast Asia",
      "South Asia", "East Asia", "North Africa", "West Africa", "Central Africa", "East Africa",
      "Southern Africa", "Caribbean Islands", "Polynesia", "Micronesia", "Melanesia", "Nordic Countries")

    regions.contains(country)
  }

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

  def findCountriesWithLowestAverageGDP(data: List[(String, Double)], numCountries: Int): List[String] = {
    val countryAverages = data
      .groupBy(_._1)
      .mapValues(list => list.map(_._2).sum / list.length)
      .toList

    val sortedCountries = countryAverages.sortBy(_._2)
    val lowestCountries = sortedCountries.take(numCountries).map(_._1)

    lowestCountries
  }
}
