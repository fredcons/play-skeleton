package controllers

import play.api.mvc._

class Dictionary extends BaseController {

  def index = Action {
    Ok(views.html.dictionary())
  }

  def doSearch = Action {
    Ok(views.html.dictionary())
  }

}
