/**
 * Scala Concurrency Advice: For Future Fun Fold On A FlatMap
 * https://medium.com/@brybunch/scala-concurrency-advice-for-future-fun-fold-on-a-flatmap-a06f37fdd498
 */

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

def calcNum(n :Int) :Future[Int] = {
  println(s"calcNum n=$n")
  Thread.sleep(500)
  Future(1)
}

def calcGroupNum(groupSeq :Seq[Int]) :Future[Int] = {
  println(s"calcOneNum head.value = ${groupSeq.head}")
  Thread.sleep(1000)
  groupSeq.foldLeft(Future.successful(0)){
    (acc :Future[Int], num :Int) => {
      acc.flatMap{accInt =>
        calcNum(num).map(_ + accInt)
      }
    }
  }
}

//                    6       + 15    + 15 = 36
val l :Seq[Int] = Seq(1,2,3,  4,5,6,  7,8)

val groupsOfInts :List[Seq[Int]] = l.grouped(3).toList

val r :Future[Int] = groupsOfInts.foldLeft(Future.successful(0)){
  (acc :Future[Int], num :Seq[Int]) => {
    acc.flatMap{
      accInt => calcGroupNum(num).map(_ + accInt)
    }
  }
}

r.onComplete{
  case Success(s) => println(s"Success inserted $s rows.")
  case Failure(f) => println(s"pg Failure cause=${f.getCause} msg=${f.getMessage} ")
}

//Await.result(r, 5.seconds)

/*
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success
*/



/*

def calcNum(n :Int) :Future[Int] = {
  println("calcNum")
  Future(n)
}

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



