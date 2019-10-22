package models

class RandomCassData extends RandCommon{

  def getRandomUser(id :Long) :User = {
    val uName = randomAlphaNumericString(5).capitalize
    val eDomain = scala.util.Random.shuffle(seqEmailDomain).head
    User(id,name=uName,email=s"$uName@${eDomain}",edomain=eDomain)
  }

}
