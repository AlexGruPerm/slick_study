package connector

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}

object Connector {
  private val hosts = Seq("10.241.5.234")
  private val keyspace = "prj"

  lazy val connector: CassandraConnection = ContactPoints(hosts).keySpace(keyspace)
}
