package controllers

import play.api.mvc._

class Application extends BaseController {

  def index = Action {
    Ok(views.html.search())
  }

}
