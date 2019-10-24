import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

val v :Future[Int] = Future(5)
val s :Future[Int] = Future(3)
val r :Future[Int] = v.map(v => v+2)

Await.result(r,1.minutes)

