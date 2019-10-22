package models

class RandomData {

  private def randomStringFromCharList(length: Int, chars: Seq[Char]): String = {
    val sb = new StringBuilder
    for (i <- 1 to length) {
      val randomNum = util.Random.nextInt(chars.length)
      sb.append(chars(randomNum))
    }
    sb.toString
  }

  private def randomAlphaNumericString(length: Int): String = {
    val chars = ('a' to 'z')
    randomStringFromCharList(length, chars)
  }

  private val seqEmailDomain :Seq[String] = Seq("mail.ru","gmail.com","ya.ru")

  def getRandomUser :PgUser = {
    val uName = randomAlphaNumericString(5).capitalize
    val eDomain = scala.util.Random.shuffle(seqEmailDomain).head
    PgUser(name=uName,email=s"$uName@${eDomain}",edomain=eDomain)
  }

}
