package services


import models.{APIError, DataModel}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters
import org.mongodb.scala.result
import play.api.libs.json.{JsObject, JsValue}
import play.api.mvc.Request
import repositories.DataRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class ApplicationService @Inject()(val dataRepository: DataRepository)(implicit val ec: ExecutionContext) {


//  private def byID(id: String): Either[APIError, Bson] = {
//    try {
//      Right(Filters.and(
//        Filters.equal("_id", id)
//      ))
//    } catch {
//      case _: Exception  => Left(APIError.BadAPIResponse(404, "book not found in database"))
//    }
//  }

  def create(input: Request[JsValue]): Either[APIError, Future[DataModel]] = {
    try{
      Right(dataRepository.create(input.body.validate[DataModel].get))
    } catch{
        case _: Exception => Left(APIError.BadAPIResponse(415, "Could not make book"))
    }
  }



  def read(id: String): Either[APIError, Future[DataModel]] = {
    try{
      Right(dataRepository.read(id).map{
        case book: DataModel => book
      })
    } catch {
          case _: Exception => Left(APIError.BadAPIResponse(404, "Could not find book"))
        }
    }


  def update(id: String, input: Request[JsValue]): Either[APIError, Future[result.UpdateResult]] = {
    try{
      Right(dataRepository.update(id, input.body.validate[DataModel].get))
    } catch{
        case _: Exception => Left(APIError.BadAPIResponse(400, "Could not update book"))
      }
    }



  def delete(id: String): Either[APIError, Future[result.DeleteResult]] = {
    try{
      Right(dataRepository.delete(id))
      }
    catch {
          case _: Exception => Left(APIError.BadAPIResponse(404, "Could not delete book"))
        }
    }

  }








