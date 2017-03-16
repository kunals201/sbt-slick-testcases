package components
import connections.{DBProvider, MySqlConnector, PostgresConnector}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import models._

trait DependentTable extends EmployeeTable  {

  this: DBProvider =>

  import driver.api._

  class DependentTable(tag: Tag) extends Table[Dependent](tag, "emp_dependent"){
    val empId = column[Int]("empId")
    val name = column[String]("name",O.PrimaryKey)
    val relation = column[String]("relation")
    val age = column[Option[Int]]("age")
    def dependentEmployeeFK = foreignKey("dependent_employee",empId,employeeTableQuery)(_.id)
    def * =(empId,name,relation,age) <> (Dependent.tupled, Dependent.unapply)
  }

  val dependentTableQuery = TableQuery[DependentTable]


}

trait DependentComponent extends DependentTable{

  this: DBProvider =>


  import driver.api._

  def create = db.run(dependentTableQuery.schema.create)

  def insert(dep: Dependent) = db.run {
    dependentTableQuery += dep
  }

  def delete(id:Int):Future[Int] = {
    val query = dependentTableQuery.filter(_.empId === id).delete
    db.run(query)
  }


  def update(id: Int,relation: String): Future[Int] = {
    val query = dependentTableQuery.filter(x => x.empId === id).map(_.relation).update(relation)
    db.run(query)
  }
  def updateName(id: Int, name: String): Future[Int] = {
    val query = dependentTableQuery.filter(_.empId === id).map(_.name).update(name)
    db.run(query)
  }
  def getAll : Future[List[Dependent]] = db run {
    dependentTableQuery.to[List].result
  }

  def upsertRecord(dependent: Dependent) ={
    db.run {
    dependentTableQuery.insertOrUpdate(dependent)
    }
  }
  def sortByDependentName=  {
    val list=Await.result(getAll,Duration.Inf).map(x=>x).sortBy(z=>z.name).toList
  list
  }
  def addMultipleDependents(dependent1: Dependent , dependent2 : Dependent) =
  {
    val insert1 = dependentTableQuery += dependent1

    val insert2 = dependentTableQuery += dependent2
    val res = insert1 andThen insert2
    //res.map(println)
    db.run(res)
  }
  def maxAge: Future[Option[Int]] = db.run{

    val a = dependentTableQuery.map(_.age)
    a.max.result
  }
  def getdependentByEmployeeName = {
    val action = {
      for {
        (e, p) <- employeeTableQuery join dependentTableQuery on (_.id === _.empId)
      } yield (e.name, p.name)
    }.to[List].result
    db.run(action)
  }
  def insertPlainSql = {
    val action = sqlu"insert into emp_dependent values(181,'prashant','Brother',37);"
    db.run(action)

  }

}
object DependentComponent extends DependentComponent  with  MySqlConnector
