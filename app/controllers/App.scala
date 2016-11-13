package controllers

import scala.concurrent._
import scala.util.{Success, Failure}
import scala.concurrent.duration._
import scala.language.postfixOps

import java.security.MessageDigest
import java.util.Random

import play.api._
import play.api.data._
import play.api.libs.concurrent._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.iteratee._
import play.api.libs.json._
import play.api.Logger
import play.api.mvc._
import play.api.Play.current

import models._


object App extends Controller {

	def splash = Action { implicit request =>
    	val reply = Reply(false, "")
		Ok(views.html.msg(reply))
	}

	def get(key: String) = Action.async { implicit request =>
		val msg = Message.get(key)
		msg map {
			case Some(s) => {
				Logger.debug(s"get $key $msg")
				val image = Key.msgImage(s)
				val reply = Reply(true, image)
				Ok(views.html.msg(reply))
			}
			case None => {
				Logger.debug("get None")
				val reply = Reply(false, "nonesuch")
				Ok(views.html.msg(reply))
			}
		}
	}

	def put = Action.async { implicit request =>
		request.body.asText match {
			case Some(msg) => {
				if (msg.length > 0) {
					val key = Key.next
					Message.put(key, msg) map {
						case true => {
							Logger.debug(s"put true $key $msg")
							val image     = Key.urlImage(key)
							val response  = Json.obj("status" -> "ok", "key" -> key, "image" -> image)
	 						Ok(response)
						}
						case _ => {
							Logger.debug(s"put false $key $msg")
							val reason    = "We could not save your message, please try again"
							val response  = Json.obj("status" -> "error", "reason" -> reason)
							Ok(response)
						}
					}
				}
				else {
						Logger.debug(s"put false: no msg")
						val reason    = "zero length message"
						val response  = Json.obj("status" -> "error", "reason" -> reason)
						Future { Ok(response) }
				}
			}
			case None => {
				Logger.debug("put None")
				val reason    = "We received an empty message"
				val response  = Json.obj("status" -> "error", "reason" -> reason)
				Future { Ok(response) }
			}
		}
	}
}

