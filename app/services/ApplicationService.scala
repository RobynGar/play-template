package services


import models.{APIError, DataModel, Field}
import play.api.data.Form
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.Request
import repositories.{DataRepository, TraitDataRepo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.impl.Promise
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ApplicationService @Inject()(dataRepository: TraitDataRepo)(implicit ec: ExecutionContext) {


  def index(): Future[Either[APIError, Seq[DataModel]]] = {
    dataRepository.index().map{
      case Right(books: Seq[DataModel]) => Right(books)
      case Left(_) => Left(APIError.BadAPIResponse(404, "could not find books"))
    }
  }



  def create(implicit input: Request[JsValue]): Future[Either[APIError, DataModel]] = {
    input.body.validate[DataModel] match {
      case JsSuccess(value, _) => dataRepository.create(value)
      case JsError(errors) => Future(Left(APIError.BadAPIResponse(415, "could not make book")))
    }
  }

  def addNewBook(book: DataModel): Future[Either[APIError, DataModel]] = {
    dataRepository.create(book).map {
      case Left(value) => Left(APIError.BadAPIResponse(415, "could not make book"))
      case Right(book) => Right(book)
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
      case JsSuccess(field, _) if(field.fieldName == "title")=> dataRepository.updateField(id, "title", field.value)
      case JsSuccess(field, _) if(field.fieldName == "description")=> dataRepository.updateField(id, "description", field.value)
      case JsSuccess(field, _) if(field.fieldName == "pageCount")=> dataRepository.updateField(id, "pageCount", field.value)
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








