package pkg

import org.slf4j.LoggerFactory

object SlickStudy extends App {
  val log = LoggerFactory.getLogger(getClass.getName)

    log.info("========================================== BEGIN ============================================")

  log.info("postgres")
    new (PgTest).run

    log.info("cassandra")
    new (CassTest).run

    log.info("========================================== END ============================================")

}





