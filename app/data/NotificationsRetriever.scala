package data

import com.google.inject.Inject
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult

import scala.concurrent.Future

class DatabaseNotificationsRetriever @Inject() (db: Database) extends NotificationsRetriever {

  implicit val parser: GetResult[Notification] = GetResult(r =>
    Notification(r.<<, r.<<, r.<<, r.<<)
  )

  private val select =
    sql"""
       select room, username, message, timestamp from notifications
       order by timestamp desc
       limit 100
    """

  def fetchLatest = db.run(select.as[Notification])
}

trait NotificationsRetriever {
  def fetchLatest: Future[Seq[Notification]]
}

case class Notification(room: String, sender: String, message: String, timestamp: String)
