package connectors

import cats.data.EitherT
import models.APIError
import play.api.libs.json.OFormat
import play.api.libs.ws.{WSClient, WSResponse}
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class LibraryConnector @Inject()(ws: WSClient) {
  def get[Response](url: String)(implicit rds: OFormat[Response], ec: ExecutionContext): EitherT[Future, APIError, Response] = {
    //right is the correct response (right response) left is the error path in the return statement the error is the first one declared (on the left hand side)
    val request = ws.url(url)
    val response = request.get()
    //above will give a WSResponse
    //EitherT allows for the return of either of the future types specified (Future[APIError] or Future[Response]) eitherT allows for the first type specified (Future) to be applied to the other types
    EitherT{
      response.map {
        result =>
          Right(result.json.as[Response])
      } //first we try to parse the response as a json body if that does not work we move on to recover step
        .recover{ case _: WSResponse =>
          Left(APIError.BadAPIResponse(500, "Could not connect"))
        } // this will give us our custom error Future[APIError] telling us it has been unable to get the book we requested
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