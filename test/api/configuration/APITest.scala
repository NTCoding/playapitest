package api.configuration

import java.io.StringReader
import java.sql.DriverManager

import com.ibatis.common.jdbc.ScriptRunner
import org.scalatest.{BeforeAndAfterAll, FreeSpecLike, Matchers}
import play.api.Play
import play.api.Play.current
import play.api.test.{DefaultAwaitTimeout, FutureAwaits, TestServer}
import TestDatabase._
import DBScriptRunner._

import scala.io.Source
import scala.util.Random

trait APITest extends FreeSpecLike with Matchers with BeforeAndAfterAll with FutureAwaits with DefaultAwaitTimeout {
  val port: Int = 1000 + Random.nextInt(8999)
  val dbScript: String

  lazy val server: TestServer = CreateTestApplicationWithEmptyDB(port, dbScript)

  def startApi() = {
    info(s"Starting API test: ${getClass.getName} on port: ${server.port}")
    info(s"Running /db.sql against $dbUrl")
    runDbScript(dbUrl, username, password, "/db.sql")
    info(s"Running $dbScript against $dbUrl")
    runDbScript(dbUrl, username, password, dbScript)
  }

  override def afterAll() {
    server.stop()
  }
}

object DBScriptRunner {
  def runDbScript(url: String, username: String, password: String, resource: String) {
    Class.forName("com.mysql.jdbc.Driver")
    val con = DriverManager.getConnection(url, username, password)
    val sru = new ScriptRunner(con, false, false)
    val scr = Source.fromInputStream(Play.resourceAsStream(resource).get).getLines.toList.mkString("\n")
    val reader = new StringReader(scr.replace("use chatroomz;", s"use $dbName;"))
    sru.runScript(reader)
  }
}
