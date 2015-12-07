package controllers

import com.sksamuel.elastic4s.{ElasticsearchClientUri, ElasticClient}
import com.typesafe.config.ConfigFactory
import models.UserSearch
import org.elasticsearch.common.settings.Settings
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.Controller

import scala.text

/**
  * Created by fred on 02/12/15.
  */
class BaseController extends Controller {

  val config = ConfigFactory.load()
  val host = config.getString("elasticsearch.host")
  val port = config.getInt("elasticsearch.port")
  val clusterName = config.getString("elasticsearch.cluster")
  val indexName = config.getString("elasticsearch.index")
  val settings = Settings.settingsBuilder().put("cluster.name", clusterName).build()
  val client = ElasticClient.transport(settings, ElasticsearchClientUri(s"elasticsearch://$host:$port"))

  val searchForm: Form[UserSearch] = Form(
    mapping(
      "keyword" -> text
    )(UserSearch.apply)(UserSearch.unapply))



}
