package pkg

import drivers.PgSlickDriver
import models.{PgUser, RandomPgData}
import pkg.SlickStudy.log
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class PgTest {

  def run(cntRow :Int) ={
    val pgdb :slick.jdbc.PostgresProfile.backend.Database = Database.forConfig("pgdb")
    val defDuration :Duration = 10.seconds
    val slickDrv = new PgSlickDriver(pgdb)
    /*
    Await.result(slickDrv.getAllUsers, defDuration).foreach(println)
    */
    /*
    log.info("Get one user by Id=2")
    Await.result(slickDrv.getUser(2), defDuration).foreach(t => log.info(t.toString))
    */
    /*
    val resultInsertUser = slickDrv.insUsers(Seq(PgUser(name="UserName",email="email@mail.ru")))
    val rowCount = Await.result(resultInsertUser, defDuration)
    log.info(s" Into table users inserted $rowCount rows.")
    Await.result(slickDrv.getAllUsers, defDuration).foreach(println)
    */

    val rand = new RandomPgData
    val su :Seq[PgUser] = (1 to cntRow).map(_ => rand.getRandomUser).toSeq
    val t1 = System.currentTimeMillis
    val resultInsertUser = slickDrv.insUsers(su)

    resultInsertUser.onComplete {
      case Success(s) => s match {
        case Some(cnt) => {
            val t2 = System.currentTimeMillis
            log.info(s"Successful inserted into postgres table users $cnt rows with ${t2-t1} ms.")
          }
        case None => log.info(s"Successful inserted None rows.")
      }
      case Failure(f) => log.info(s"pg Failure cause=${f.getCause} msg=${f.getMessage} ")
    }
    //val rowCount = Await.result(resultInsertUser, 5.minutes) //!!!!!!!!!!!!!!!! не надо ждать

  }

}
