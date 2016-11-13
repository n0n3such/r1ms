
package models

import org.joda.time.DateTime
import play.api.libs.Crypto._
import play.api.Logger
import play.api.Play
import play.api.Play.current
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection.JSONCollection
import play.modules.reactivemongo.MongoController
import reactivemongo.api._
import reactivemongo.api.indexes._
import play.modules.reactivemongo.json._
import reactivemongo.bson._
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

case class Message (
    val key:    String,
    val msg:    String,
    val when:   Long
)

object Message {

  import play.api.libs.json.Json
  implicit val messageFormat = Json.format[Message]

  val config    = Play.current.configuration
  val ttl       = config.getInt("db.ttl").getOrElse(60 * 60 * 24 * 14)  // 2 weeks

  def msgCollection : JSONCollection = ReactiveMongoPlugin.db.collection[JSONCollection]("messages")
  val index = new Index(Seq(("when", IndexType.Ascending)), options = BSONDocument("expireAfterSeconds" -> BSONInteger(ttl)))
  msgCollection.indexesManager.ensure(index)

  def get(key: String) : Future[Option[String]] = {
  val cursor = msgCollection.find(Json.obj("key" -> key)).cursor[Message]()
    cursor.headOption map {
        case Some(m) => {
          val secretMsg = m.msg
          msgCollection.remove(Json.obj("key" -> key))
          Some(decryptAES(secretMsg))
        }
        case None => None
    }
  }

  def put(key: String, msg: String) : Future[Boolean] = {
	  val secretMsg = encryptAES(msg)
      msgCollection.insert(Message(key, secretMsg, System.currentTimeMillis)) map { lasterror => lasterror.ok }
  }
}

