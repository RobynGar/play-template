package services


import models.{APIError, DataModel, Field}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.Request
import repositories.{DataRepository, TraitDataRepo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ApplicationService @Inject()(dataRepository: TraitDataRepo)(implicit ec: ExecutionContext) {


  def index(): Future[Either[APIError, Seq[JsValue]]] = {
    dataRepository.index().map{
      case Right(books: Seq[DataModel]) => Right(books.map(book => Json.toJson(book)))
      case Left(_) => Left(APIError.BadAPIResponse(404, "could not find books"))
    }
  }



  def create(input: Request[JsValue]): Future[Either[APIError, DataModel]] = {
    input.body.validate[DataModel] match {
      case JsSuccess(value, _) => dataRepository.create(value)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(415, "could not make book")))
    }

  }


  def read(id: String): Future[Either[APIError, DataModel]] =
      dataRepository.read(id).map{
        case Right(book: DataModel) => Right(book)
        case Left(errors) => Left(APIError.BadAPIResponse(404, "could not find book"))
      }


  def readName(name: String): Future[Either[APIError, DataModel]] =
    dataRepository.readName(name).map{
      case Right(book: DataModel) => Right(book)
      case Left(errors) => Left(APIError.BadAPIResponse(404, "could not find book"))
    }


  def update(id: String, input: Request[JsValue]): Future[Either[APIError, DataModel]] = {
    input.body.validate[DataModel] match {
      case JsSuccess(book, _) => dataRepository.update(id, book)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "could not update book")))
    }

  }

  def updateField(id: String, input: Request[JsValue]): Future[Either[APIError, DataModel]] = {
    input.body.validate[Field] match {
      case JsSuccess(field, _) if(field.fieldName == "name")=> dataRepository.updateField(id, "name", field.value)
      case JsSuccess(field, _) if(field.fieldName == "description")=> dataRepository.updateField(id, "description", field.value)
      case JsSuccess(field, _) if(field.fieldName == "numSales")=> dataRepository.updateField(id, "numSales", field.value)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "could not update book")))
    }

  }


  def delete(id: String): Future[Either[APIError, String]] = {
    dataRepository.delete(id).map{
      case Right(deleted: String) => Right("deleted")
      case Left(error) => Left(APIError.BadAPIResponse(404, "could not delete book"))
    }
  }






  }








