package controllers

import models.Document
import play.api.mvc._

import com.sksamuel.elastic4s.jackson.ElasticJackson.Implicits._

class Search extends BaseController {

  def index = Action {
    Ok(views.html.search(Array.empty[Document]))
  }

  def doSearch = Action {
    import com.sksamuel.elastic4s.ElasticDsl._
    val response = client.execute { search in "documents"->"title" query "iso"}.await
    val documents = response.as[Document]
    Ok(views.html.search(documents))
  }

}

