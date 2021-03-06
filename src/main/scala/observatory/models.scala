package observatory

import java.net.URI

import scala.math.{Pi, atan, sinh, toDegrees}

/**
  * Introduced in Week 1. Represents a location on the globe.
  * @param lat Degrees of latitude, -90 ≤ lat ≤ 90
  * @param lon Degrees of longitude, -180 ≤ lon ≤ 180
  */
case class Location(lat: Double, lon: Double) {
  override def canEqual(that: Any): Boolean =
    that match {
      case thatVal: Location =>
        (lat == thatVal.lat) && (lon == thatVal.lon)
    }

}
object Location {
  def empty: Location = new Location(0.0, 0.0)
}

/**
  * Introduced in Week 3. Represents a tiled web map tile.
  * See https://en.wikipedia.org/wiki/Tiled_web_map
  * Based on http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
  * @param x X coordinate of the tile
  * @param y Y coordinate of the tile
  * @param zoom Zoom level, 0 ≤ zoom ≤ 19
  */
case class Tile(x: Int, y: Int, zoom: Int) {

  /**
    * Get the Location corresponding to this Tile.
    *
    * @return the Location corresponding to this Tile.
    */
  def toLocation: Location = Location(
    toDegrees(atan(sinh(Pi * (1.0 - 2.0 * y.toDouble / (1 << zoom))))),
    x.toDouble / (1 << zoom) * 360.0 - 180.0
  )

  /**
    * Get OpenStreetMap URI for this Tile
    *
    * @return OpenStreetMap URI for this Tile
    */
  def getURI = new URI(s"http://tile.openstreetmap.org/$zoom/$x/$y.png")
}

/**
  * Introduced in Week 4. Represents a point on a grid composed of
  * circles of latitudes and lines of longitude.
  * @param lat Circle of latitude in degrees, -89 ≤ lat ≤ 90
  * @param lon Line of longitude in degrees, -180 ≤ lon ≤ 179
  */
case class GridLocation(lat: Int, lon: Int)

/**
  * Introduced in Week 5. Represents a point inside of a grid cell.
  * @param x X coordinate inside the cell, 0 ≤ x ≤ 1
  * @param y Y coordinate inside the cell, 0 ≤ y ≤ 1
  */
case class CellPoint(x: Double, y: Double)

/**
  * Introduced in Week 2. Represents an RGB color.
  * @param red Level of red, 0 ≤ red ≤ 255
  * @param green Level of green, 0 ≤ green ≤ 255
  * @param blue Level of blue, 0 ≤ blue ≤ 255
  */
case class Color(red: Int, green: Int, blue: Int)

case class Point(x: Double, y: Double) extends Ordered[Point] {
  import scala.math.Ordered.orderingToOrdered
  def compare(that: Point): Int = (this.x, this.y).compare((that.x, that.y))
}
