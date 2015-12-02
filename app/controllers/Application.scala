package controllers

import models.Document
import play.api.mvc._

class Application extends BaseController {

  def index = Action {
    Ok(views.html.search(Array.empty[Document]))
  }

}
