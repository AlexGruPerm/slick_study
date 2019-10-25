package covers

import com.outworkers.phantom.dsl._
import models.User
import scala.concurrent.Future

abstract class CassUsers extends Table[CassUsers, User] with RootConnector {

  override def tableName: String = "users"
  object id extends LongColumn with PartitionKey{
    override lazy val name = "\"id\""
  }
  object name extends StringColumn{
    override lazy val name = "\"name\""
  }
  object email extends StringColumn {
    override lazy val name = "\"email\""
  }
  object edomain extends StringColumn{
    override lazy val name = "\"edomain\""
  }

  def createTableIfNex()={
    create.ifNotExists().future()
    }

  def getAllUsers: Future[List[User]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getById(id: Long): Future[Option[User]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def deleteById(id: Long): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def insUser(u :User): Future[Long] =
    insert.value(_.id, u.id)
      .value(_.name, u.name)
      .value(_.email, u.email)
      .value(_.edomain, u.edomain)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .ifNotExists()
      .future().map(_ => 1L/*u.id*/)
}


