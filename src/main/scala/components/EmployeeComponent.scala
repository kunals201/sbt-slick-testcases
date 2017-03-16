package components
import scala.concurrent.ExecutionContext.Implicits.global
import connections.{DBProvider, MySqlConnector}
import models._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration


trait EmployeeTable  extends  MySqlConnector {
  this: DBProvider =>

  import driver.api._


  class EmployeeTable(tag: Tag) extends Table[Employee](tag, "employees") {
    val id = column[Int]("id", O.PrimaryKey)
    val name = column[String]("name")
    val experience = column[Double]("experience")

    def * = (id, name, experience) <> (Employee.tupled, Employee.unapply)
  }
  val employeeTableQuery = TableQuery[EmployeeTable]

}

trait EmployeeComponent extends EmployeeTable {
  this: DBProvider =>

  import driver.api._

  //val db = Database.forConfig("myPostgresDB")
  def create = db.run(employeeTableQuery.schema.create)

  def insert(emp: Employee) = db.run {
    employeeTableQuery += emp
  }

  def delete(exp: Double) = {
    val query = employeeTableQuery.filter(x => x.experience === exp)
    val action = query.delete
    db.run(action)
  }

  def updateName(id: Int, name: String) = {
    val query = employeeTableQuery.filter(_.id === id).map(_.name).update(name)
    db.run(query)
  }

  def getAll: Future[List[Employee]] = {
    db.run {
      employeeTableQuery.to[List].result
    }
  }

  def sortByName() = {
    val list=Await.result(getAll,Duration.Inf).map(x=>x).sortBy(z=>z.name)
    list
  }
}


  object EmployeeComponent extends EmployeeComponent
