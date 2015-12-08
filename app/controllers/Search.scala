package controllers

import com.sksamuel.elastic4s.{ElasticsearchClientUri, ElasticClient}
import com.typesafe.config.ConfigFactory
import models.Document
import models.UserSearch
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import com.sksamuel.elastic4s.jackson.ElasticJackson.Implicits._

import models.SearchImplicits._

import scala.collection.mutable

object Search extends Controller {

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

  def index = Action {

    Ok(views.html.search(searchForm, Array.empty[Document]))
  }

  def doSearch = Action { implicit request =>

    searchForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.search(formWithErrors, Array.empty[Document]))
      },
      userData => {
        /* binding success, you get the actual value. */
        val userSearch = UserSearch(userData.keyword)
        import com.sksamuel.elastic4s.ElasticDsl._
        val response = client.execute {
          search in "afnor" types "documents" fields("titlefr", "summary") query userSearch.keyword aggregations {
            aggregation terms "orgori" field "orgori" size 5
            nestedAggregation("supports").path("supports").aggregations(
              aggregation terms "format" field "format"
            )
          }
        }.await
        val documents = response.as[Document]
        val aggOrgOri = response.aggregations.getAsMap.get("orgori").asInstanceOf[StringTerms]
        //agg.getBuckets.size shouldBe 5
        //agg.getBucketByKey("foo").getDocCount
        Ok(views.html.search(searchForm, documents))
      }
    )


  }



}

