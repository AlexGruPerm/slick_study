package pkg

import models.{RandomCassData, User}
import pkg.SlickStudy.log

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
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

  def calcGroup(groupSeq :Seq[User]) :Future[Long] =
    Future.sequence(groupSeq.map(u => db.UserModel.insUser(u))).map(v => v.size)

  def run(cntRow :Int) ={
    log.info("CassTest.run")

    Await.result(db.UserModel.createTableIfNex(), 5.seconds)
    //Await.result(db.UserModel.getAllUsers, 5.seconds).foreach(t => log.info(t.toString))
    //Await.result(db.UserModel.deleteById(1L), 3.seconds)
    //Await.result(db.UserModel.getAllUsers, 5.seconds).foreach(t => log.info(t.toString))

    //val rand = new RandomPgData
    //val su :IndexedSeq[Seq[Serializable]] = (1 to 100).map(_ => rand.getRandomCassUser)
    /*
    val userById = db.UserModel.getById(2L).map(r => r)
    */

    val groupRowCnt :Int = 1000
    val rand = new RandomCassData
    val seqUsers :Seq[User] = (1 to cntRow).map(thisUserId => rand.getRandomUser(thisUserId))
    log.info(s"Total users size ${seqUsers.size}")
    val t1 = System.currentTimeMillis

    val r :Future[Long] = seqUsers.grouped(groupRowCnt).foldLeft(Future.successful(0L)){
      (acc :Future[Long], num :Seq[User]) => {
        acc.flatMap{
          accInt => calcGroup(num).map(_ + accInt)
        }
      }
    }
      /*.onComplete{
      case Success(s) => log.info(s"Success inserted $s rows.")
      case Failure(f) => log.info(s"Failure cause=${f.getCause} msg=${f.getMessage} ")
    }
    */

    Await.result(r,5.minutes)

    val t2 = System.currentTimeMillis
    log.info(s" Into cassandra table users inserted XXX rows with ${(t2-t1)} ms.")

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