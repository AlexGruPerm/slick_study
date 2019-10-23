/**
 * Scala Concurrency Advice: For Future Fun Fold On A FlatMap
 * https://medium.com/@brybunch/scala-concurrency-advice-for-future-fun-fold-on-a-flatmap-a06f37fdd498
 */
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

/*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success
*/

def calcNum(n :Int) :Future[Int] = {
  println("calcNum")
  Future(n)
}

def calcGroupNum(groupSeq :Seq[Int]) :Future[Seq[Future[Int]]] = {
    println("calcOneNum")
    Future(groupSeq.map(i => calcNum(i)))
}
                  //   6    + 15    + 15 = 36
val l :Seq[Int] = Seq(1,2,3,  4,5,6,  7,8)

val groupsOfInts :List[Seq[Int]] = l.grouped(3).toList

val r :Future[Int] = groupsOfInts.foldLeft(Future.successful(0)){
  (acc :Future[Int], num :Seq[Int]) => {
    acc.flatMap{
      accInt :Int => calcGroupNum(num).map{
        ints =>
          //-----------------------------------
          ints.foldLeft(Future.successful(0)){
            (acci :Future[Int], numi :Seq[Int]) => {
             acci.flatMap{
               accInti :Int => Future.sequence( numi.map(intsi => intsi + accInti))
             }
            }
          }
          // ----------------------------------
        + accInt
      }
    }
  }
}

Await.result(r, 5.seconds)

/*
def calcGroupNum(groupSeq :Seq[Int]) :Future[Int] = {
    println("calcOneNum")
    Future(groupSeq.sum)
}
                  //   6    + 15    + 15 = 36
val l :Seq[Int] = Seq(1,2,3,  4,5,6,  7,8)

val groupsOfInts :List[Seq[Int]] = l.grouped(3).toList

val r :Future[Int] = groupsOfInts.foldLeft(Future.successful(0)){
  (acc :Future[Int], num :Seq[Int]) => {
    acc.flatMap{
      accInt => calcGroupNum(num).map(_ + accInt)
    }
  }
}

Await.result(r, 5.seconds)
*/




