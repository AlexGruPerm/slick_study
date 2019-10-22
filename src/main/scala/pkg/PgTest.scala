package pkg

import drivers.PgSlickDriver
import models.{PgUser, RandomPgData}
import pkg.SlickStudy.log
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration._

class PgTest {

  def run ={
    val pgdb :slick.jdbc.PostgresProfile.backend.Database = Database.forConfig("pgdb")
    val defDuration :Duration = 10.seconds
    val slickDrv = new PgSlickDriver(pgdb)
    /*
    Await.result(slickDrv.getAllUsers, defDuration).foreach(println)
    */
    log.info("Get one user by Id=2")
    Await.result(slickDrv.getUser(2), defDuration).foreach(println)
    /*
    val resultInsertUser = slickDrv.insUsers(Seq(PgUser(name="UserName",email="email@mail.ru")))
    val rowCount = Await.result(resultInsertUser, defDuration)
    log.info(s" Into table users inserted $rowCount rows.")
    Await.result(slickDrv.getAllUsers, defDuration).foreach(println)
    */

    val rand = new RandomPgData
    val su :Seq[PgUser] = (1 to 10000).map(_ => rand.getRandomUser).toSeq
    val resultInsertUser = slickDrv.insUsers(su)
    val t1 = System.currentTimeMillis
    val rowCount = Await.result(resultInsertUser, 5.minutes)
    val t2 = System.currentTimeMillis
    log.info(s" Into postgres table users inserted $rowCount rows with ${(t2-t1)} ms.")
  }

}
