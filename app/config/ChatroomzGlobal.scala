package config

import com.google.inject.AbstractModule
import data.{DatabaseNotificationsRetriever, NotificationsRetriever}
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Filter, RequestHeader, Result}
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future

class DependencyWiring() extends AbstractModule {
  override def configure() {
    bind(classOf[Database]).toInstance(Database.forConfig("chatroomz"))
    bind(classOf[NotificationsRetriever]).to(classOf[DatabaseNotificationsRetriever])
  }
}


object AccessLog extends Filter {
  def apply(next: RequestHeader => Future[Result])(request: RequestHeader): Future[Result] = {
    val start = System.currentTimeMillis
    val result = next(request)
    result map { r =>
      val responseTime = System.currentTimeMillis() - start
      Logger.debug(s"${request.uri} - ${r.header.status} - $responseTime}")
    }
    result
  }
}
