package pkg

import slick.jdbc.PostgresProfile.backend.Database
import org.slf4j.LoggerFactory
import models._
import scala.concurrent.Await
import scala.concurrent.duration._

object SlickStudy extends App {
  val log = LoggerFactory.getLogger(getClass.getName)
  log.info("========================================== BEGIN ============================================")
  val db = Database.forConfig("pgdb")
  val slickDrv = new SlickDriver(db)
  val resultAll :Seq[User] = Await.result(slickDrv.getAllUsers, 30.seconds)
  resultAll.foreach(println)
  Await.result(slickDrv.getUser(2), 30.seconds).foreach(println)
  log.info("========================================== END ============================================")
}





