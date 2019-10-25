import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

val rndInit = scala.util.Random
val sourceItems :Seq[Int] = Seq(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17)
val groupsOfItem :Iterator[Seq[Int]] = sourceItems.grouped(3)//.toList

//emulate insert onw row.
def calcNum(n :Int) :Future[Int] = Future{
  val slp  = rndInit.nextInt(500)
  println(s"   calcNum n=$n  slp=$slp")
  Thread.sleep(slp)
  1
}

//process one batch
def calcGroup(groupSeq :Seq[Int]) :Future[Int] = {
  println(s"calcOneNum First value si ${groupSeq.head}")
  Thread.sleep(1000)
  Future.sequence(groupSeq.map(n => calcNum(n))).map(v => v.sum)
}

groupsOfItem.foldLeft(Future.successful(0)){
  (acc :Future[Int], num :Seq[Int]) => {
    acc.flatMap{
      accInt => calcGroup(num).map(_ + accInt)
    }
  }
}.onComplete{
  case Success(s) => println(s"Success inserted $s rows.")
  case Failure(f) => println(s"pg Failure cause=${f.getCause} msg=${f.getMessage} ")
}

