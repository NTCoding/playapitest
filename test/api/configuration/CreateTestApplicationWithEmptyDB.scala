package api.configuration

import com.typesafe.config.ConfigFactory
import play.api.inject._
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.TestServer
import slick.driver.MySQLDriver.api._
import scala.collection.JavaConverters._

object CreateTestApplicationWithEmptyDB {
  import api.configuration.TestDatabase._
  val m = Map(
    "chatroomz" -> Map(
      "url" -> s"$dbUrl/$dbName",
      "driver" -> "com.mysql.jdbc.Driver",
      "properties" -> Map(
        "user" -> username,
        "password" -> password).asJava,
      "numThreads" -> 10).asJava
  ).asJava
  val dbConfig = ConfigFactory.parseMap(m)


  def apply(port: Int, dbScript: String) = {
    val db = Database.forConfig("chatroomz", dbConfig)
    val app = new GuiceApplicationBuilder()
      .configure(Map("logger.root" -> "ERROR", "logger.application" -> "ERROR"))
      .overrides(bind[Database].toInstance(db))
      .build
    val server = TestServer(port, app)
    server.start()
    server
  }
}
