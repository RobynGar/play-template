package services


import models.{APIError, DataModel, Field}
import play.api.libs.json.{JsError, JsSuccess, JsValue}
import play.api.mvc.Request
import repositories.DataRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ApplicationService @Inject()(val dataRepository: DataRepository)(implicit val ec: ExecutionContext) {


  def create(input: Request[JsValue]): Future[Either[APIError, DataModel]] = {
    input.body.validate[DataModel] match {
      case JsSuccess(value, _) => dataRepository.create(value)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(415, "Could not make book")))
    }

  }


  def read(id: String): Future[Either[APIError, DataModel]] =
      dataRepository.read(id).map{
        case Right(book: DataModel) => Right(book)
        case Left(errors) => Left(APIError.BadAPIResponse(404, "Could not find book"))
      }


  def readName(name: String): Future[Either[APIError, DataModel]] =
    dataRepository.readName(name).map{
      case Right(book: DataModel) => Right(book)
      case Left(errors) => Left(APIError.BadAPIResponse(404, "Could not find book"))
    }


  def update(id: String, input: Request[JsValue]): Future[Either[APIError, DataModel]] = {
    input.body.validate[DataModel] match {
      case JsSuccess(book, _) => dataRepository.update(id, book)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "Could not update book")))
    }

  }

  def updateField(id: String, input: Request[JsValue]): Future[Either[APIError, String]] = {
    input.body.validate[Field] match {
      case JsSuccess(field, _) if(field.fieldName == "name")=> dataRepository.updateField(id, "name", field.value)
      case JsSuccess(field, _) if(field.fieldName == "description")=> dataRepository.updateField(id, "description", field.value)
      case JsSuccess(field, _) if(field.fieldName == "numSales")=> dataRepository.updateField(id, "numSales", field.value)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(400, "Could not update book")))
    }

  }


  def delete(id: String): Future[Either[APIError, String]] = {
    dataRepository.delete(id).map{
      case Right(deleted: String) => Right("deleted")
      case Left(error) => Left(APIError.BadAPIResponse(404, "Could not delete book"))
    }
  }






  }








