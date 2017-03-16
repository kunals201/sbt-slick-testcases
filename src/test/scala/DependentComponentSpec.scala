import components.DependentComponent
import connection.H2DBComponent
import models.Dependent
import org.scalatest.AsyncFunSuite

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object testObject extends DependentComponent with H2DBComponent


class DependentComponentSpec extends AsyncFunSuite {


    test("Add new Employee ") {
      testObject.insert(Dependent(184, "akhil", "friend", Some(28))).map(x => assert(x == 1))
    }
    test("update Employee") {
      testObject.updateName(181, "AkhilDhanjani").map(x => assert(x == 1))
    }
  test("update Employee Name") {
    testObject.updateName(181, "brother").map(x => assert(x == 1))
  }
  test("delete Employee") {
      testObject.delete(181).map( x => assert(x == 1))
    }

    test("get all employees") {
      testObject.getAll.map( x => assert( x.size == 1))
    }
  test("sort by  Name") {

    val x=testObject.sortByDependentName.map(x =>x)
    assert(x==List(Dependent(181,"kunal","brother",Some(27))))

  }
  test("adding or insert by upsert") {
    testObject.upsertRecord(Dependent(181, "bharat", "friend", Some(38))).map(x =>assert(x==1))
  }
  test("adding multiple dependents") {
    testObject.addMultipleDependents(Dependent(181,"Himanshu","mate",Some(45)),Dependent(184,"Himanshurajput","mate",Some(45))).map(x => assert(x == 1))
  }
  test("max age ") {
    testObject.maxAge.flatMap(x => assert(x == Some(27)))
  }
  test("join ") {
    testObject.getdependentByEmployeeName.map(x => assert(x.size === 1))
  }
  test("testing Plain sql"){
    testObject.insertPlainSql.map(x=>assert(x==1))
  }

}

