package models

class RandomPgData extends RandCommon{

  def getRandomUser :PgUser = {
    val uName = randomAlphaNumericString(5).capitalize
    val eDomain = scala.util.Random.shuffle(seqEmailDomain).head
    PgUser(name=uName,email=s"$uName@${eDomain}",edomain=eDomain)
  }

}
