package controllers

import models.Document
import play.api.mvc._

object Application extends BaseController {

  def index = Action {
    Redirect("/search")
  }

}
