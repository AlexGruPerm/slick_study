package pkg

import models.{RandomData, PgSlickDriver, PgUser}
import slick.jdbc.PostgresProfile.api._
import pkg.SlickStudy.log
import scala.concurrent.duration._
import scala.concurrent.Await

class PgTest {

  def run ={
    val pgdb = Database.forConfig("pgdb")
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

    val rand = new RandomData
    val su :Seq[PgUser] = (1 to 100).map(_ => rand.getRandomUser)
    val resultInsertUser = slickDrv.insUsers(su)
    val t1 = System.currentTimeMillis
    val rowCount = Await.result(resultInsertUser, 5.minutes)
    val t2 = System.currentTimeMillis
    log.info(s" Into table users inserted $rowCount rows with ${(t2-t1)} ms.")
  }

}
