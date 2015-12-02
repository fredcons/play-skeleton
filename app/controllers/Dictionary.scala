package controllers

import play.api.mvc._

class Dictionary extends BaseController {

  def index = Action {
    Ok(views.html.dictionary())
  }

  def search = Action {
    Ok(views.html.search())
  }

}
