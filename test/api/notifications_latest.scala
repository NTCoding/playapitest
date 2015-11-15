package api

import java.io.StringReader
import java.sql.DriverManager

import com.ibatis.common.jdbc.ScriptRunner
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, FreeSpecLike, Matchers}
import play.api.Play
import play.api.Play.current
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.{Format, Json}
import play.api.libs.ws.WS
import play.api.test.{DefaultAwaitTimeout, FutureAwaits, TestServer}
import slick.driver.MySQLDriver.api._
import DBScriptRunner._

import scala.collection.JavaConverters._
import scala.io.Source
import scala.util.Random

class notifications_latest extends AcceptanceTest {
  import TestData._
  implicit val f: Format[APINotification] = Json.format[APINotification]
  val dbScript = "/api/notifications.sql"

  "When requesting the latest notifications" - {
    startApi()
    val httpRes = await(WS.url(s"http://localhost:$port/notifications/latest").get())

    "A 200 HTTP resonse is returned" in {
      assert(httpRes.status === 200)
    }

    "The most recent notifications are returned as JSON in the response body" in {
      val notifications = Json.parse(httpRes.body).as[Seq[APINotification]]
      assert(notifications === testNotifications)
    }
  }

  override def afterAll() {
    server.stop()
  }

  object TestData {
    // These correspond to the insertions in "$dbScript"
    val testNotifications = Seq(
      APINotification("test room1", "user 1", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room2", "user 2", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room3", "user 3", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room4", "user 4", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room5", "user 5", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room6", "user 6", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room7", "user 7", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room8", "user 8", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room9", "user 9", "This room is so amazing", "2015-01-01 00:00:00"),
      APINotification("test room10", "user 10", "This room is so amazing", "2015-01-01 00:00:00")
    )
  }
}

case class APINotification(room: String, sender: String, message: String, timestamp: String)

trait AcceptanceTest extends FreeSpecLike with Matchers with BeforeAndAfterAll with FutureAwaits with DefaultAwaitTimeout {
  import TestDatabase._
  val port: Int = 1000 + Random.nextInt(8999)
  val dbScript: String

  lazy val server: TestServer = CreateTestApplicationWithEmptyDB(port, dbScript)

  def startApi() = {
    info(s"Starting Acceptance test: ${getClass.getName} on port: ${server.port}")
    info(s"Running /db.sql against $dbUrl")
    runDbScript(dbUrl, username, password, "/db.sql")
    info(s"Running $dbScript against $dbUrl")
    runDbScript(dbUrl, username, password, dbScript)
  }

  override def afterAll() {
    server.stop()
  }
}

object TestDatabase {
  val username = "chatty"
  val password = "chatty"
  val dbName = "acceptancetests"
  val dbUrl = s"jdbc:mysql://localhost:3306"
}

object CreateTestApplicationWithEmptyDB {
  import TestDatabase._
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

object DBScriptRunner {
  def runDbScript(url: String, username: String, password: String, resource: String) {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection(url, username, password)
    val sru = new ScriptRunner(con, false, false)
    val scr = Source.fromInputStream(Play.resourceAsStream(resource).get).getLines.toList.mkString("\n")
    val reader = new StringReader(scr.replace("use chatroomz;", "use acceptancetests;"))
    sru.runScript(reader)
  }

  def runDbCommand(url: String, username: String, password: String, command: String) {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection(url, username, password)
    val s = con.createStatement()
    s.executeUpdate(command)
  }
}