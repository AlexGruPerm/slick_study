/**
 * Scala Concurrency Advice: For Future Fun Fold On A FlatMap
 * https://medium.com/@brybunch/scala-concurrency-advice-for-future-fun-fold-on-a-flatmap-a06f37fdd498
 */
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


def f1 :Future[Int] = Future{
  println("begin f1")
  Thread.sleep(2000)
  println("end f1")
  1}

def f2 :Future[Int] = Future{
  println("begin f2")
  Thread.sleep(3000)
  println("end f2")
  2}

def f3 :Future[Int] = Future{
  println("begin f3")
  Thread.sleep(4000)
  println("end f3")
  3}

def f4 :Future[Int] = Future{
  println("begin f4")
  Thread.sleep(5000)
  println("end f4")
  4}

val t1 = System.currentTimeMillis
val fb = for{
  v1 <- f1
  v2 <- f2
  v3 <- f3
  v4 <- f4} yield (v1,v2,v3,v4)

Await.result(fb,1.minute)
val t2 = System.currentTimeMillis

println(s"Summary duaration is : ${t2-t1} ms.")



/*
val fb = f1.flatMap(v1 =>
  f2.flatMap(v2 =>
    f3.flatMap(v3 =>
      f4.map(v4 => (v1, v2, v3, v4)))))
*/


/*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success
*/


//  val ec = scala.concurrent.ExecutionContext.global
/*
  def calcOneNum(inVal :Int) :Future[Int] = {
    println("calcOneNum - "+inVal)
    Future(inVal*10)
  }

  def calcBatchOfFutures(si :Seq[Int]) :Seq[Future[Int]] = {
    println("calcBatchOfFutures - head="+si.head)
    si.map(i => calcOneNum(i))
  }
*/

//val l :Seq[Int] = Seq(1,2,3,4,5,6,7,8,9,10,11)

/**
val groupedWidgets: List[Seq[Int]]= l.grouped(3).toList

groupedWidgets.foldLeft(Future.successful(true){
  (joiningFuture, subGroupOfWidgets) =>
    joiningFuture.flatMap{
      joiningFutureResult =>
        val listOfFutureResults: List[Future[Boolean]] = subGroupOfWidgets.map {
          widget =>functionReturningFutureBoolean(widget)
        }
        Future.sequence(listOfFutureResults).map(_.forall(_ == true))
    }
}
*/

  /*
val groupsOfInts = l.grouped(3)

val r :Future[Int] = groupsOfInts.foldLeft(Future.successful(0)) {
  (joiningFuture :Future[Int], subGroup :Seq[Int]) =>
    joiningFuture.flatMap {
      joiningFutureResult => {
        val listOfFutureResults: Seq[Future[Int]] = subGroup.map { thisVal => calcOneNum(thisVal)
        }
        Future.sequence(listOfFutureResults).map(f => if (f.exists(e => e==0)) 0 else 1)
      }
    }
}

  r.onComplete {
    case Success(s) => println(s"Success s=$s")
    case Failure(f) => println(s"Failure cause=${f.getCause} msg=${f.getMessage} ")
  }
*/

//Await.result(r, 5.seconds)



