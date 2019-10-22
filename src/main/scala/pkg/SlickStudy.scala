package pkg

import org.slf4j.LoggerFactory

object SlickStudy extends App {
  val log = LoggerFactory.getLogger(getClass.getName)
  val randomCntRows :Int =100000
    log.info("========================================== BEGIN ============================================")

    log.info("postgres")
    new (PgTest).run(randomCntRows)

    log.info("cassandra")
    new (CassTest).run(randomCntRows)

    log.info("========================================== END ============================================")

}





