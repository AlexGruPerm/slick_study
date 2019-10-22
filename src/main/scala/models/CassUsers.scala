package models

import com.outworkers.phantom.dsl._
import scala.concurrent.Future

abstract class CassUsers extends Table[CassUsers, User] {
  object id extends BigIntColumn with PartitionKey
  object name extends StringColumn
  object email extends StringColumn
  object edomain extends StringColumn

  override def fromRow(row: Row): User = {
     User(
      id(row),
      name(row),
      email(row),
      edomain(row)
    )
  }

  def getById(id: BigInt): Future[Option[User]] = {
    select
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

}

abstract class UserExamples extends CassUsers {
  override def getById(id: BigInt): Future[Option[User]] = {
    select.where(_.id eqs id).one()
  }
}

