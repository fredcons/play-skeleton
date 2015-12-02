package controllers

import play.api.mvc._

class Search extends BaseController {

  def index = Action {
    Ok(views.html.search())
  }

  def search = Action {

    

    Ok(views.html.search())
  }

}
