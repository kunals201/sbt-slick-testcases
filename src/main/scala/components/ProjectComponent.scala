package components

import connections.{DBProvider, MySqlConnector}
import models.Project

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

trait ProjectTable extends EmployeeTable with MySqlConnector {
  this: DBProvider =>

  import driver.api._


  class ProjectTable(tag: Tag) extends Table[Project](tag, "projects_table") {
    val empId = column[Int]("empId")
    val projName = column[String]("projName")
    val teamMembers = column[Int]("teamMembers")

    def employeeProjectFk = foreignKey(
      "employee_project_fk", empId, employeeTableQuery)(_.id)

    def * = (empId, projName, teamMembers) <> (Project.tupled, Project.unapply)
  }

  val projectTableQuery = TableQuery[ProjectTable]
}

trait ProjectComponent extends ProjectTable {

  this: DBProvider =>

  import driver.api._

  //val db = Database.forConfig("myPostgresDB")
  def create = db.run(projectTableQuery.schema.create)

  def insert(proj: Project) = db.run {
    projectTableQuery += proj
  }

  def delete(proName: String) = {
    val query = projectTableQuery.filter(x => x.projName === proName)
    val action = query.delete
    db.run(action)
  }

  def updateName(id: Int, name: String): Future[Int] = {
    val query = projectTableQuery.filter(_.empId === id).map(_.projName).update(name)
    db.run(query)
  }

  def getAll: Future[List[Project]] = {
    db.run {
      projectTableQuery.to[List].result
    }
  }

  def sortByProjectName() = {
    val list=Await.result(getAll,Duration.Inf).map(x=>x).sortBy(z=>z.projName)
  list
  }
}

object ProjectComponent extends ProjectComponent