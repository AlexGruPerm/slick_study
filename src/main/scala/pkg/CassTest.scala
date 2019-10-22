package pkg

//import scala.concurrent._
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.outworkers.phantom.database.Database
import models.CassUsers
import pkg.SlickStudy.log

/*
import com.datastax.driver.core.SocketOptions
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.{ContactPoints, _}
import models.CassUsers
import pkg.SlickStudy.log
*/

/*
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.typesafe.config.ConfigFactory
import scala.collection.JavaConverters._

import collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
*/
object Connector {
  private val hosts = Seq("10.241.5.234")
  private val keyspace = "prj"
  lazy val connector: CassandraConnection = ContactPoints(hosts).keySpace(keyspace)
}

class MyDb(override val connector: CassandraConnection) extends Database[MyDb](Connector.connector) {
  object users extends CassUsers with Connector{
    override def tableName: String = "users"
  }
}

object db extends MyDb(Connector.connector)

class CassTest {
  def run ={
    log.info("CassTest.run")
    val ue = db.users.getById(1L)
    log.info(ue.toString)
    //Await.result(Future.sequence(ue), 5.minutes).foreach(println)
  }
}


