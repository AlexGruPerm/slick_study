package connector

import com.datastax.driver.core.{HostDistance, PoolingOptions}
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}

object Connector {
  private val hosts = Seq("10.241.5.234")
  private val keyspace = "prj"

  val poolingOptions = new PoolingOptions()
    .setConnectionsPerHost(HostDistance.LOCAL, 1, 200)
    .setMaxRequestsPerConnection(HostDistance.LOCAL, 256)
    .setNewConnectionThreshold(HostDistance.LOCAL, 100)
    .setCoreConnectionsPerHost(HostDistance.LOCAL, 200)

  lazy val connector: CassandraConnection = ContactPoints(hosts)
    //.noHeartbeat().withClusterBuilder(_.withPoolingOptions(poolingOptions))
    .keySpace(keyspace)
}
