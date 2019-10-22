package drivers

import models.PgUser
import org.slf4j.LoggerFactory
import slick.jdbc.PostgresProfile.api._
import slick.lifted.{TableQuery, Tag}

import scala.concurrent.Future

class UsersTable(tag: Tag) extends Table[PgUser](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def email = column[String]("email")
  def edomain = column[String]("edomain")
  def * = (id, name, email, edomain).mapTo[PgUser]
}

class PgSlickDriver(db :Database){
  val log = LoggerFactory.getLogger(getClass.getName)
  val users = TableQuery[UsersTable]
  log.info(s"If not exists - ${users.schema.createStatements.mkString}")
  val createUserTable: DBIO[Unit] = users.schema.create
  db.run(createUserTable)

  def getAllUsers  :Future[Seq[PgUser]] = {
    val action: DBIO[Seq[PgUser]] = users.result
    val future: Future[Seq[PgUser]] = db.run(action)
    log.info(users.result.statements.mkString)
    future
  }

  def getUser(userId :Int) :Future[Seq[PgUser]] = {
    val action: DBIO[Seq[PgUser]] = users.filter(_.id === userId.toLong).result
    val future: Future[Seq[PgUser]] = db.run(action)
    log.info(users.result.statements.mkString)
    future
  }

  def insUsers(insUser :Seq[PgUser]) :Future[Option[Int]] = {
    val insert: DBIO[Option[Int]] = users ++= insUser
    val result: Future[Option[Int]] = db.run(insert)
    result
  }


}

