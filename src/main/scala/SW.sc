val l :Seq[Int] = Seq(1,2,3,4,5,6,7,8,9,10,11)

val groups = l.grouped(3)

//println("groups = "+groups.size)

groups.foreach(g =>  println(g.size))

(1 to 3).foreach(println)
