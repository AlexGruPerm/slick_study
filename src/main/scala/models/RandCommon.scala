package models

trait RandCommon {

  val seqEmailDomain :Seq[String] = Seq("mail.ru","gmail.com","ya.ru")

  def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }

  def randomAlphaNumericString(length: Int): String = {
    val chars = ('a' to 'z')
    randomStringFromCharList(length, chars)
  }

}
