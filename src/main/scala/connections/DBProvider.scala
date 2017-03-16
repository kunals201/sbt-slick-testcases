package connections
import java.util.UUID

import slick.driver.JdbcProfile
import slick.jdbc.{H2Profile, PostgresProfile}

trait DBProvider {

  val driver: JdbcProfile

  import driver.api._

  val db: Database

}
