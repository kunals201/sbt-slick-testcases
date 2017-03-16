import components.{DependentComponent}
import connection.H2DBComponent
import models.Dependent
import org.scalatest.AsyncFunSuite

object testObject extends DependentComponent with H2DBComponent

  class DependentComponentSpec extends AsyncFunSuite {


    test("Add new Employee ") {
      testObject.insert(Dependent(12, "akhil", "friend", Some(28))).map(x => assert(x == 1))
    }
    test("update Employee") {
      testObject.updateName(17, "Akhil").map(x => assert(x == 1))
    }
    test("delete Employee ") {
      testObject.delete("mate").map(x=>assert(x == 1))
    }

    test("get all employees") {
      testObject.getAll.map( x => assert( x.size == 3))
    }
  }

