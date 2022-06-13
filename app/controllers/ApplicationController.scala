package controllers

import models.{APIError, DataModel}
import play.api.libs.json.{JsError, JsObject, JsSuccess, JsValue, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import repositories.DataRepository
import services.{ApplicationService, LibraryService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}



@Singleton
class ApplicationController @Inject()(val controllerComponents: ControllerComponents, val dataRepository: DataRepository, val applicationService: ApplicationService, val libraryService: LibraryService)(implicit val ec: ExecutionContext) extends BaseController {
//implicit executive context is used for async operations like map/flatMap. executionContext is another name for ThreadPool
  def index(): Action[AnyContent] = Action.async { implicit request =>
    val books: Future[Seq[DataModel]] = dataRepository.collection.find().toFuture()
    books.map(items => Json.toJson(items)).map(result => Ok(result))
  }

  def create(): Either[APIError, String] = Action.async(parse.json) { implicit request =>
    applicationService.create(request).map {
      case Right(book: DataModel) => Created
      case Left(error: APIError) => error
    }
  }

//    request.body.validate[DataModel] match {
//      case JsSuccess(dataModel, _) =>
//        dataRepository.create(dataModel).map(_ => Created)
//      case JsError(_) => Future(BadRequest)
//    }


  def read(id: String): Either [APIError, DataModel] = Action.async { implicit request =>
    applicationService.read(id).map{
      case book: DataModel => Ok(DataModel.formats.writes(book))
      case error => NotFound
    }
//     dataRepository.read(id).map{
//      //case book if(book.isInstanceOf[DataModel]) => Ok(Json.toJson(book))
//      case book: DataModel if(book._id == id)=> Ok(DataModel.formats.writes(book))
//      case APIError.BadAPIResponse(500, "Could not connect") => BadRequest
//    }

  }

  def update(id: String) = Action.async(parse.json) { implicit request =>
      applicationService.update(id, request).map{
        case Right(book: DataModel) => Accepted
        case Left(error) => BadRequest(error.reason)
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

  def delete(id: String): Action[AnyContent]= Action.async { implicit request =>
    applicationService.delete(id).map{
          //.value after the (id) for the methods in service before i took out the eitherT
      case Right(numDeleted: Int) => Accepted
      case Left(error: APIError) => NotFound(error.reason)
    }
//    dataRepository.delete(id)
//    Future(Accepted)
    }


  def getGoogleBook(search: String, term: String): Action[AnyContent] = Action.async { implicit request =>
   libraryService.getGoogleBook(search = search, term = term).value.map{
     case Right(book) => Ok(DataModel.formats.writes(book))
     case Left(error) => BadRequest
   }

  }

}

