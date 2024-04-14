package com.summary

import okhttp3.{MediaType, OkHttpClient, Request, RequestBody}
import org.json4s.MonadicJValue.jvalueToMonadic
import org.json4s.jackson.JsonMethods._
import org.jsoup.Jsoup

import java.sql._
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

object ScalaSummarizationService {
  private val dbUrl = "jdbc:postgresql://localhost:5432/historydb"
  private val dbUser = "root"
  private val dbPassword = "root"


  def test():String={
  "Testing"
  }

  object DatabaseLogger {

    def logRequest(websiteUrl: String, summary: String): Unit = {
      val json = parse(summary)
      val summaryText = (json \ "summary").values.toString

      val connection: Connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)
      val sql = "INSERT INTO request_history (website_url, summary) VALUES (?, ?)"
      val statement: PreparedStatement = connection.prepareStatement(sql)

      try {
        statement.setString(1, websiteUrl)
        statement.setString(2, summaryText)
        statement.executeUpdate()
      } catch {
        case ex: SQLException => println(s"Error executing SQL: ${ex.getMessage}")
      } finally {
        // Ensure resources are closed
        if (statement != null) statement.close()
        if (connection != null) connection.close()
      }
    }
  }

  object WebsiteCrawler {
    def crawlAndExtractText(url: String): String = {
      try {
        val doc = Jsoup.connect(url).get()
        val text = doc.select("body").text()
        text
      } catch {
        case e: Exception =>
          println(s"An error occurred while crawling and extracting text: ${e.getMessage}")
          ""
      }
    }

    def escapeForJson(text: String): String = {
      text.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\b", "\\b") // Backspace
        .replace("\f", "\\f") // Form feed
        .replace("\n", "\\n") // Newline
        .replace("\r", "\\r") // Carriage return
        .replace("\t", "\\t") // Tab
    }

    def sendTextToSummarizer(text: String, summarizerUrl: String): Try[String] = {
      val client = new OkHttpClient()
      val escapedText = escapeForJson(text)
      val jsonPayload = s"""{ "text": "$escapedText" }"""
      val requestBody = RequestBody.create(jsonPayload, MediaType.parse("application/json"))
      val request = new Request.Builder()
        .url(summarizerUrl)
        .post(requestBody)
        .build()

      Try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
          response.body().string()
        } else {
          throw new RuntimeException(s"Failed to call summarizer service: ${response.code()} - ${response.message()}")
        }
      }
    }
  }

  def summarizeAndLog(websiteUrl: String): String = {
    val text = WebsiteCrawler.crawlAndExtractText(websiteUrl)
    if (text.isEmpty) {
      "Error: Unable to extract text from the website."
    } else {
      val summaryResult = WebsiteCrawler.sendTextToSummarizer(text, "http://localhost:8000/summarize")

      summaryResult match {
        case Success(summary) =>
          DatabaseLogger.logRequest(websiteUrl, summary)
          val json = parse(summary)
          val summaryText = (json \ "summary").values.toString
          summaryText
        case Failure(exception) =>
          println(s"Error getting summary: ${exception.getMessage}")
          "Error: Unable to generate summary."
      }
    }
  }


  def fetchAllRequests(): Seq[(Int, String, String, Timestamp)] = { // Assuming your table structure
    val connection: Connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)

    val sql = "SELECT id, website_url, summary, timestamp FROM request_history"
    val statement = connection.createStatement()
    val results = statement.executeQuery(sql)

    val requests = new mutable.ArrayBuffer[(Int, String, String, Timestamp)]()
    while (results.next()) {
      val id = results.getInt(1)
      val url = results.getString(2)
      val summary = results.getString(3)
      val timestamp = results.getTimestamp(4)
      requests += ((id, url, summary, timestamp))
    }

    results.close()
    statement.close()
    connection.close()

    requests.toSeq
  }
}





