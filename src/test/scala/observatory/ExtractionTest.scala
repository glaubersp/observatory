package observatory

import java.time.LocalDate

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

trait ExtractionTest extends FunSuite {

  test("locateTemperatures should work as expected for 2015's data") {
    val resultTemp: Seq[(LocalDate, Location, Temperature)] = Seq(
      (LocalDate.of(2015, 1, 25), Location(43.272, -124.319), 7.5),
      (LocalDate.of(2015, 2, 21), Location(43.272, -124.319), 8.0),
      (LocalDate.of(2015, 6, 15), Location(43.272, -124.319), 12.0),
      (LocalDate.of(2015, 7, 18), Location(43.272, -124.319), 15.5)
    )
    val op: Iterable[(LocalDate, Location, Temperature)] =
      Extraction.locateTemperatures(2015, "/stations.csv", "/2015.csv")

    assert(op.count(p => resultTemp.contains(p)) == resultTemp.size)
  }

  test("locationYearlyAverageRecords should work as expected for 2015's data") {
    val resultAvgTemp: Seq[(Location, Temperature)] = Seq(
      (Location(13.067, 80.2), 26.6875),
      (Location(49.467, 20.05), 18.5),
      (Location(45.117, 7.617), 2.02)
    )

    val data: Iterable[(LocalDate, Location, Temperature)] = Extraction
      .locateTemperatures(2015, "/stations.csv", "/2015.csv")

    val op = Extraction.locationYearlyAverageRecords(data)

    assert(op.count(p => resultAvgTemp.contains(p)) == resultAvgTemp.size)
  }
}
