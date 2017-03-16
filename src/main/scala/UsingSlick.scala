import components.{DependentComponent, EmployeeComponent, ProjectComponent}
import models.{Dependent, Employee, Project}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object UsingSlick extends App {

  EmployeeComponent.create

  EmployeeComponent.insert(Employee(1001, "Kunal", 0.7))
  EmployeeComponent.insert(Employee(1002, "Ankit", 0.8))
  EmployeeComponent.insert(Employee(1003, "Himanshu", 0.6))
  val sortedListOfEmployee = EmployeeComponent.sortByName()
  val listOfEmployee = Await.result(EmployeeComponent.getAll, Duration.Inf)
  //insertRes.map(res1 => s"$res1 inserted")
  println(listOfEmployee)
  println(sortedListOfEmployee)
  Thread.sleep(10000)

  ProjectComponent.create

  ProjectComponent.insert(Project(1001, "IntranetChattingSystem", 12))
  ProjectComponent.insert(Project(1002, "BugTracker", 5))
  ProjectComponent.insert(Project(1003, "Scalageek", 10))
  val sortedListOfProjects = ProjectComponent.sortByProjectName()
  val listOfProjects = Await.result(EmployeeComponent.getAll, Duration.Inf)
  println(listOfProjects)
  println(sortedListOfProjects)
  Thread.sleep(10000)

  DependentComponent.create

  DependentComponent.insert(Dependent(1001, "kunal", "GrandFather", None))
  DependentComponent.insert(Dependent(1002, "anmol", "father", Some(28)))
  DependentComponent.insert(Dependent(1003, "vasu", "GrandSon", Some(18)))
  val sortedListOfDependents = DependentComponent.sortByDependentName
  val dependentList = Await.result(DependentComponent.getAll, Duration.Inf)

  println(dependentList)
  println(sortedListOfDependents)
  Thread.sleep(1000)
}