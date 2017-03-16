package connection

import connections.DBProvider
import slick.jdbc.H2Profile
import java.util.UUID

/**
  * Created by knoldus on 3/16/17.
  */
trait H2DBComponent extends DBProvider{
  val driver = H2Profile

  import driver.api._

  val randomDB = "jdbc:h2:mem:test" +
    UUID.randomUUID().toString + ";"
  val h2Url = randomDB + "MODE=MySql;DATABASE_TO_UPPER=false;INIT=RUNSCRIPT FROM 'src/test/resources/schema.sql'\\;RUNSCRIPT FROM 'src/test/resources/initialdata.sql'"
  println(s"\n\n~~~~~~~~~~~~~~~~~~~~~             $h2Url         ~~~~~~~~~~~~~~~~~~~~~~~\n\n")
  val db: Database = Database.forURL(url = h2Url, driver = "org.h2.Driver")
}
