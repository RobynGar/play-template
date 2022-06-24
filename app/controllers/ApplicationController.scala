package controllers

import models.DataModel
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.{ApplicationService, LibraryService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}



@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents, val applicationService: ApplicationService, val libraryService: LibraryService)(implicit val ec: ExecutionContext) extends BaseController {
//implicit executive context is used for async operations like map/flatMap. executionContext is another name for ThreadPool
  def index(): Action[AnyContent] = Action.async { implicit request =>
    applicationService.index().map{
      case Right(book: Seq[DataModel]) => Ok(Json.toJson(book))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def create(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    applicationService.create(request).map {
      case Right(value) => Created(Json.toJson(value))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

//    request.body.validate[DataModel] match {
//      case JsSuccess(dataModel, _) =>
//        dataRepository.create(dataModel).map(_ => Created)
//      case JsError(_) => Future(BadRequest)
//    }


  def read(id: String): Action[AnyContent] = Action.async { implicit request =>
    applicationService.read(id).map{
      case Right(book: DataModel) => Ok(DataModel.formats.writes(book))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
//     dataRepository.read(id).map{
//      //case book if(book.isInstanceOf[DataModel]) => Ok(Json.toJson(book))
//      case book: DataModel if(book._id == id)=> Ok(DataModel.formats.writes(book))
//      case APIError.BadAPIResponse(500, "Could not connect") => BadRequest
//    }
  }

  def readName(name: String): Action[AnyContent] = Action.async { implicit request =>
    applicationService.readName(name).map{
      case Right(book: DataModel) => Ok(Json.toJson(book))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }

  }

  def update(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    applicationService.update(id, request).map {
      case Right(book: DataModel) => Accepted(Json.toJson(book))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }


    //    request.body.validate[DataModel] match {
//      //this is saying look at the body of the request
//      // that is being given and validate (check) it
//      // matches the format specified in the DataModel
//      // then if it does or if it does not do different things
//      case JsSuccess(dataModel,_) =>
//        dataRepository.update(id, dataModel).map(result => Accepted)
////        books.map(item => Json.toJson(DataModel.formats.writes(dataModel))).map(result => Accepted(result))
//      case JsError(_) => Future(BadRequest)
//    }
  }

  def updateField(id: String): Action[JsValue] = Action.async(parse.json) { implicit request =>
    applicationService.updateField(id, request).map {
      case Right(updated: DataModel) => Accepted(Json.toJson(updated))
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
    }
  }

  def delete(id: String): Action[AnyContent]= Action.async { implicit request =>
    applicationService.delete(id).map{
      case Right(deleted: String) => Accepted
      case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))

    }
//    dataRepository.delete(id)
//    Future(Accepted)
    }


  def getGoogleBook(search: String, term: String): Action[AnyContent] = Action.async { implicit request =>
   libraryService.getGoogleBook(search = search, term = term).value.map{
     case Right(book) => Ok(Json.toJson(book))
     case Left(error) => Status(error.httpResponseStatus)(Json.toJson(error.reason))
   }

  }

}

