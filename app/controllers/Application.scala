package controllers

import models.Document
import play.api.mvc._

object Application extends BaseController {

  def index = Action {
    Ok(views.html.search(searchForm, Array.empty[Document]))
  }

}
