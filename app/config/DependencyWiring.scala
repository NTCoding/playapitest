package config

import com.google.inject.AbstractModule
import data.{DatabaseNotificationsRetriever, NotificationsRetriever}
import slick.driver.MySQLDriver.api._

class DependencyWiring extends AbstractModule {
  override def configure() {
    bind(classOf[Database]).toInstance(Database.forConfig("chatroomz"))
    bind(classOf[NotificationsRetriever]).to(classOf[DatabaseNotificationsRetriever])
  }
}
