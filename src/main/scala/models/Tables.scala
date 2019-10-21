package models

import org.slf4j.LoggerFactory
import slick.lifted.{TableQuery, Tag}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

object T {
  val users = TableQuery[UsersTable]
}

final case class User(id: Long = 0, name: String, email: String)

class UsersTable(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email")
  def * = (id, name, email).mapTo[User]
}

class SlickDriver(db :Database){
  val log = LoggerFactory.getLogger(getClass.getName)
  val users = TableQuery[UsersTable]
  //log.info(s"If not exists - ${users.schema.createStatements.mkString}")

  def getAllUsers  :Future[Seq[User]] = {
    val action: DBIO[Seq[User]] = users.result
    val future: Future[Seq[User]] = db.run(action)
    log.info(users.result.statements.mkString)
    future
  }

  def getUser(userId :Int) :Future[Seq[User]] = {
    val action: DBIO[Seq[User]] = users.filter(_.id === userId.toLong).result
    val future: Future[Seq[User]] = db.run(action)
    log.info(users.result.statements.mkString)
    future
  }

}

