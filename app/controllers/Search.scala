package controllers

import models.Document
import models.UserSearch
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import com.sksamuel.elastic4s.jackson.ElasticJackson.Implicits._

object Search extends BaseController {

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
        val response = client.execute { search in "documents"->"title" query userSearch.keyword}.await
        val documents = response.as[Document]
        Ok(views.html.search(searchForm, documents))
      }
    )


  }



}

