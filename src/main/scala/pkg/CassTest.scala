package pkg

import models.{RandomCassData, User}
import pkg.SlickStudy.log

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
/*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
*/

/**
 * In phantom-dsl "2.42.0"
 * using old driver version, com.datastax.driver.core - DataStax Java driver 3.6.0 for Apache Cassandra
 *
*/
class CassTest {
  val db = CassDatabase
  def run ={
    log.info("CassTest.run")

    Await.result(db.UserModel.getAllUsers, 5.seconds).foreach(println)

    Await.result(db.UserModel.deleteById(1L), 3.seconds)

    log.info("after deletion")
    Await.result(db.UserModel.getAllUsers, 5.seconds).foreach(println)

    //val rand = new RandomPgData
    //val su :IndexedSeq[Seq[Serializable]] = (1 to 100).map(_ => rand.getRandomCassUser)
    /*
    val userById = db.UserModel.getById(2L).map(r => r)
    */

    val rand = new RandomCassData
    val su :Seq[User] = (1 to 10000).map(gid => rand.getRandomUser(gid)).toSeq

    val t1 = System.currentTimeMillis
    Await.result(Future(db.UserModel.insUser(su)), 5.minutes)
    val t2 = System.currentTimeMillis

    log.info(s" Into cassandra table users inserted X rows with ${(t2-t1)} ms.")

    db.session.close()
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