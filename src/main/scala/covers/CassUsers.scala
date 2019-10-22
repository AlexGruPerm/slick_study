package covers

import com.outworkers.phantom.dsl._
import models.User
import scala.concurrent.Future

abstract class CassUsers extends Table[CassUsers, User] with RootConnector {

  override def tableName: String = "users"
  object id extends BigIntColumn with PartitionKey{
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

  def getAllUsers: Future[List[User]] = {
    select
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getById(id: BigInt): Future[Option[User]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def deleteById(id: BigInt): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def insUser(su :Seq[User]): Future[ResultSet] = {
    Batch.unlogged
      .add(
        su.map(u =>
        insert
        .value(_.id, u.id)
        .value(_.name, u.name)
        .value(_.email, u.email)
        .value(_.edomain, u.edomain)
      )
    ).future()
    /*
    su.map(u =>
    insert
      .value(_.id, u.id)
      .value(_.name, u.name)
      .value(_.email, u.email)
      .value(_.edomain, u.edomain)
      .future()
    )
    */
    /*
    BUG : https://github.com/outworkers/phantom/issues/774
    store(u)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .ifNotExists.future()
    */
  }

  //updates logged, unlogged - https://outworkers.github.io/phantom/basics/batches.html

}


