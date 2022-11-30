package connectors

import cats.data.EitherT
import models.{APIError, DataModel, GoogleBook}
import play.api.libs.json.{JsError, JsLookupResult, JsObject, JsSuccess, JsValue, Json, OFormat}
import play.api.libs.ws.{WSClient, WSResponse}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class LibraryConnector @Inject()(ws: WSClient) {
  def get[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): EitherT[Future, APIError, DataModel] = {
    //right is the correct response (right response) left is the error path in the return statement the error is the first one declared (on the left hand side)
    //response contains the data returned from the server
    val request = ws.url(url)
    val response = request.get()
    //above will give a WSResponse
    //EitherT allows for the return of either of the future types specified (Future[APIError] or Future[Response]) eitherT allows for the first type specified (Future) to be applied to the other types
    EitherT{
      response.map {
        result =>
          //attempt1
//          val parsed = Json.parse(result.body)
//          val id: String = (parsed \ "items" \ "id").as[String]
//          val name: String = (parsed \ "items" \ "volumeInfo" \ "title").as[String]
//          val description: String =( parsed \ "items" \ "volumeInfo" \ "description").as[String]
//          val pageNum: Int = (parsed \ "items" \ "volumeInfo" \ "pageCount").as[Int]
//          println(s"robyn${Json.prettyPrint(parsed)}")
//          println(s"moose${pageNum.as[String]}")
//                Right(DataModel(id, name, description, pageNum))
//          val js = result.json
//          println("blob")
//          println(Json.prettyPrint(js))
          //val apiBook = result.json.as[GoogleBook]
            //Right(DataModel(apiBook.items.head.id, apiBook.items.head.volumeInfo.title, apiBook.items.head.volumeInfo.description, apiBook.items.head.volumeInfo.pageCount))

               result.json.validate[GoogleBook] match {
                    case JsSuccess(value, _) =>
                      Right(DataModel(value.items.head.id, value.items.head.volumeInfo.title, value.items.head.volumeInfo.description, value.items.head.volumeInfo.pageCount))
                    case JsError(errors) =>
                      val errorMessage = errors.map {
                        case (_, es) => es.toString()
                      }.flatten.mkString("/n")
                      Left(APIError.JsonError(400, "could not validate book"))
                  }

      } //first we try to parse the response from a json body to a DataModel if that does not work we move on to recover step
//        .recover{ case _: WSResponse =>
//          Left(APIError.BadAPIResponse(500, "Could not connect"))
//        } // this will give us our custom error Future[APIError] telling us it has been unable to get the book we requested
    }
  }

}

//before error handling
//class LibraryConnector @Inject()(ws: WSClient) {
//  def get[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): Future[Response] = {
//    val request = ws.url(url)
//    val response = request.get()
//    response.map {
//      result =>
//        result.json.as[Response]
//    }
//  }
//}