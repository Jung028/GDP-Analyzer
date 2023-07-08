# GDP-Analyzer

This project is a Scala program that analyzes GDP data from a CSV file and provides insights such as the country with the highest GDP, the country with the lowest GDP, and the average GDP per capita for a specific country.

## Usage

To use the GDP Analyzer program, follow the steps below:

1. Clone the repository: `git clone https://github.com/your-username/gdp-analyzer.git`
2. Navigate to the project directory: `cd gdp-analyzer`
3. Ensure you have Scala and sbt installed on your system.
4. Modify the `filePath` variable in the `GDPAnalyzer` object to specify the path to the input CSV file containing GDP data.
5. Run the program: `sbt run`

The program will read the GDP data from the CSV file and display the following information:

- The country with the highest GDP
- The country with the lowest GDP
- The average GDP per capita for a specific country (in this case, Malaysia)
- The 5 countries with the lowest average GDP per capita

## Dependencies

The project depends on the following libraries:

- `com.opencsv:opencsv` version 5.5.2: Used for reading CSV files.
- `org.scala-lang:scala-library` version 2.13.11: Provides core Scala functionality.

These dependencies are managed using sbt (Simple Build Tool) and will be automatically downloaded when you run the program for the first time.

## File Structure

The main files and directories in this project are:

- `src/main/scala/`: Contains the source code files.
  - `GDPAnalyzer.scala`: The main entry point of the program.
  - `GDPAnalyzerHelper.scala`: Helper class for reading and analyzing GDP data.
- `src/main/scala/gdp.txt`: Sample input file containing GDP data.
- `src/main/scala/regions.txt`: Sample file containing excluded regions for analysis.

## Contributing

Contributions to this project are welcome. If you find any issues or have suggestions for improvements, please feel free to open a new issue or submit a pull request.

## License

This project is licensed under the MIT License.

## Enjoy ;)
