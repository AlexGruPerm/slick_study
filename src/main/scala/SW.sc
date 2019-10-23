import scala.concurrent.Future
import scala.util.{Failure, Success}
/*
import scala.util.Failure
import scala.util.Success
import scala.concurrent.duration.Duration
*/
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * Scala Concurrency Advice: For Future Fun Fold On A FlatMap
 * https://medium.com/@brybunch/scala-concurrency-advice-for-future-fun-fold-on-a-flatmap-a06f37fdd498
*/
//  val ec = scala.concurrent.ExecutionContext.global

  def calcOneNum(inVal :Int) :Future[Int] = {
    println("calcOneNum - "+inVal)
    Future(inVal*10)
  }

  def calcBatchOfFutures(si :Seq[Int]) :Seq[Future[Int]] = {
    println("calcBatchOfFutures - head="+si.head)
    si.map(i => calcOneNum(i))
  }

val l :Seq[Int] = Seq(1,2,3,4,5,6,7,8,9,10,11)

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


//Await.result(r, 5.seconds)

