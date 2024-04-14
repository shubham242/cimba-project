package com.summary

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write
import com.summary.ScalaSummarizationService._
object ScalaService extends App {

  implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer : ActorMaterializer.type = ActorMaterializer
    import system.dispatcher

    val route =
      path("summarize") {
        post {
          entity(as[String]) { websiteUrl =>
            implicit val formats: DefaultFormats.type = DefaultFormats
            val json = parse(websiteUrl)
            val url = (json \ "websiteUrl").values.toString
            complete {
              HttpResponse(
                entity = HttpEntity(ContentType(MediaTypes.`application/json`), summarizeAndLog(url))
              )
            }
          }
        }
      } ~
        path("requests") {
          get {
            val results = fetchAllRequests().map { case (id, url, summary, timestamp) =>
              Map("id" -> id, "websiteUrl" -> url, "summary" -> summary, "timestamp" -> timestamp)
            }
            implicit val formats = Serialization.formats(NoTypeHints)
            complete(HttpResponse(entity = HttpEntity(ContentType(MediaTypes.`application/json`), write(results))))
          }
        }

    val server = Http().newServerAt("localhost", 9090).bind(route)
    server.map { _ =>
      println("Successfully started on localhost:9090 ")
    } recover { case ex =>
      println("Failed to start the server due to: " + ex.getMessage)
    }
}
