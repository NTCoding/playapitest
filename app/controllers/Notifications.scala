package controllers

import javax.inject.Inject

import data.{Notification, NotificationsRetriever}
import play.api.libs.json.{Format, Json}
import play.api.mvc._

import play.api.libs.concurrent.Execution.Implicits._

class Notifications @Inject() (retriever: NotificationsRetriever) extends Controller {
  implicit val f: Format[Notification] = Json.format[Notification]

  def latest = Action.async {
    retriever.fetchLatest map { notifications =>
      Ok(Json.toJson(notifications))
    }
  }

}
