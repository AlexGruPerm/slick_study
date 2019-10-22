package pkg

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.dsl._
import connector.Connector
import models.CassUsers

class MyDb(override val connector: CassandraConnection) extends Database[MyDb](Connector.connector) {
  object UserModel extends CassUsers with Connector

}

object CassDatabase extends MyDb(Connector.connector)