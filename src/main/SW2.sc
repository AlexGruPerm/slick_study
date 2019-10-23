import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success

Future{1}.map(_ + Future(1))

val r :Future[Int] = Future{1} + Future{2}

/*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success
*/
