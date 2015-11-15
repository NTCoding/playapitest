package api

import api.configuration.APITest
import play.api.Play.current
import play.api.libs.json.{Format, Json}
import play.api.libs.ws.WS

class notifications_latest extends APITest {
  import TestData._
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

  object TestData {
    implicit val f: Format[APINotification] = Json.format[APINotification]
    // These correspond to the insertions in "$dbScript"
    val testNotifications = Seq(
      APINotification("test room1", "user 1", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room2", "user 2", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room3", "user 3", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room4", "user 4", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room5", "user 5", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room6", "user 6", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room7", "user 7", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room8", "user 8", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room9", "user 9", "This room is so amazing", "2015-01-01 00:00:00.0"),
      APINotification("test room10", "user 10", "This room is so amazing", "2015-01-01 00:00:00.0")
    )
  }
}

case class APINotification(room: String, sender: String, message: String, timestamp: String)

