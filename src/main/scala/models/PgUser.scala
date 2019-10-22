package models

import drivers.UsersTable
import slick.lifted.TableQuery

object T {
  val users = TableQuery[UsersTable]
}

final case class PgUser(id: Long = 0, name: String, email: String, edomain :String)
