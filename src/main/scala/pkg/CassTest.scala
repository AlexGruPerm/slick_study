package pkg

import pkg.SlickStudy.log

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

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

    //val userById :Future[Option[User]] = db.UserModel.getById(2L)
    val userById = for {
      res <- db.UserModel.getById(2L)
    } yield res

    userById.onComplete{
      case Success(u) => u match {
        case Some(su) => println(su)
        case None => println("Successful executed query, user = None")
      }
      case Failure(f) => println(s"Failure cause=${f.getCause} msg=${f.getMessage} ")
    }

    db.session.close()
  }
}