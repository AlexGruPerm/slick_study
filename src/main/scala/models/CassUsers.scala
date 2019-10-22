package models

import com.outworkers.phantom.dsl._
import scala.concurrent.Future

abstract class CassUsers extends Table[CassUsers, User] {

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

  def getById(id: Long): Future[Option[User]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

}


