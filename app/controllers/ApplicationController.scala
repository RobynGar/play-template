package controllers

import play.api.mvc.{BaseController, ControllerComponents}
import javax.inject.Inject

// to get this page on 9000 http://localhost:9000/api/
//@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{

  def index() = TODO

  def create() = TODO

  def read(id: String) = TODO

  def update(id: String) = TODO

  def delete(id: String) = TODO

}

