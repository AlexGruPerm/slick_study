package pkg

import models.{RandomCassData, User}
import pkg.SlickStudy.log

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}
/*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
*/

/**
 * In phantom-dsl "2.42.0"
 * using old driver version, com.datastax.driver.core - DataStax Java driver 3.6.0 for Apache Cassandra
 *
*/
class CassTest {
  val db = CassDatabase
  def run(cntRow :Int) ={
    log.info("CassTest.run")

    Await.result(db.UserModel.createTableIfNex(), 5.seconds)
    Await.result(db.UserModel.getAllUsers, 5.seconds).foreach(t => log.info(t.toString))
    //Await.result(db.UserModel.deleteById(1L), 3.seconds)
    Await.result(db.UserModel.getAllUsers, 5.seconds).foreach(t => log.info(t.toString))


    //val rand = new RandomPgData
    //val su :IndexedSeq[Seq[Serializable]] = (1 to 100).map(_ => rand.getRandomCassUser)
    /*
    val userById = db.UserModel.getById(2L).map(r => r)
    */

    val groupRowCnt :Int = 200
    val rand = new RandomCassData
    val sequ :Seq[User] = (1 to cntRow).map(thisUserId => rand.getRandomUser(thisUserId)).sortBy(tu => tu.id)
    log.info(s"Total users size ${sequ.size}")
    val t1 = System.currentTimeMillis

      sequ.grouped(groupRowCnt).foreach{
      sugRp =>
        sugRp.foreach{
          u =>
            db.UserModel.insUser(u).onComplete {
              case Success(_) => Unit
              case Failure(f) => log.info(s"Failure cause=${f.getCause} msg=${f.getMessage} ")
            }
        }
    }

    val t2 = System.currentTimeMillis
    log.info(s" Into cassandra table users inserted X rows with ${(t2-t1)} ms.")

    Thread.sleep(25000)
    //db.session.close()
  }
}

/*
userById.onComplete{
  case Success(u) => u match {
    case Some(su) => println(su)
    case None => println("Successful executed query, user = None")
  }
  case Failure(f) => println(s"Failure cause=${f.getCause} msg=${f.getMessage} ")
}
*/