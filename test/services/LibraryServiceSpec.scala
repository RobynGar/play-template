package services

import baseSpec.BaseSpec
import cats.data.EitherT
import connectors.LibraryConnector
import models.APIError.BadAPIResponse
import play.api.libs.ws.WSClient
import models.{APIError, DataModel}
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.{JsObject, JsValue, Json, OFormat}

import scala.concurrent.{ExecutionContext, Future}


class LibraryServiceSpec extends BaseSpec with MockFactory with ScalaFutures with GuiceOneAppPerSuite{

  val mockConnector = mock[LibraryConnector]
  implicit val executionContext: ExecutionContext = app.injector.instanceOf[ExecutionContext]
  val testService = new LibraryService(mockConnector)
  implicit val formats: OFormat[DataModel] = Json.format[DataModel]

  val gameOfThrones: JsValue = Json.obj(
    "_id" -> "someId",
    "name" -> "A Game of Thrones",
    "description" -> "The best book!!!",
    "numSales" -> 100
  )

  "getGoogleBook" should {
    val url: String = "testUrl"
//before we changed the return type
//    "return a book" in {
//      (mockConnector.get[DataModel](_: String)(_: OFormat[DataModel], _: ExecutionContext))
//        .expects(url, *, *).returning(Future(gameOfThrones.as[DataModel])).once()
//
//      whenReady(testService.getGoogleBook(urlOverride = Some(url), search = "", term = "")) { result =>
//        result shouldBe DataModel("someId", "A Game of Thrones", "The best book!!!", 100)
//      }
//    }
    "return a book" in {

      (mockConnector.get[DataModel](_: String)(_: OFormat[DataModel], _: ExecutionContext))
        .expects(url, *, *).returning(EitherT.rightT[Future, APIError](gameOfThrones.as[DataModel])).once()


      whenReady(testService.getGoogleBook(urlOverride = Some(url), search = "", term = "").value) { result =>
        result shouldBe Right(DataModel("someId", "A Game of Thrones", "The best book!!!", 100))
      }

    }

    "return an error" in {
      val url: String = "testUrl"

      (mockConnector.get[JsObject](_: String)(_: OFormat[JsObject], _: ExecutionContext))
        .expects(url, *, *)
        .returning(EitherT.leftT[Future, JsObject](BadAPIResponse(500, "Could not connect"))).once()

      whenReady(testService.getGoogleBook(urlOverride = Some(url), search = "", term = "").value) { result =>
        result shouldBe Left(BadAPIResponse(500, "Could not connect"))
      }
    }
  }

}
