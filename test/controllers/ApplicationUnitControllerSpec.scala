package controllers

import baseSpec.BaseSpecWithApplication
import models.{APIError, DataModel}
import org.scalamock.scalatest.MockFactory
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Request, Result}
import play.api.test.FakeRequest
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}
import services.{ApplicationService, LibraryService}

import scala.concurrent.Future

class ApplicationUnitControllerSpec extends BaseSpecWithApplication with MockFactory {

  //unit testing
  val mockServiceLayer: ApplicationService = mock[ApplicationService]
  val mockLibrary: LibraryService = mock[LibraryService]

  val unitTestController = new ApplicationController(
    component,
    mockServiceLayer,
    mockLibrary
  )

  private val dataModel: DataModel = DataModel(
    "abcd",
    "testname",
    "test description",
    100
  )

  "ApplicationController unit test .create()" should {
    "create a book in the database" in {
      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))

      (mockServiceLayer.create(_: Request[JsValue])).expects(request).returning(Future(Right(dataModel))).once()

      val createdResult: Future[Result] = unitTestController.create()(request)

      status(createdResult) shouldBe Status.CREATED
      contentAsJson(createdResult).as[DataModel] shouldBe dataModel

    }

    "cannot create book" in {

      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))

      (mockServiceLayer.create(_: Request[JsValue])).expects(request).returning(Future(Left(APIError.BadAPIResponse(415, "could not make book")))).once()

      val createdResult: Future[Result] = unitTestController.create()(request)

      status(createdResult) shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(createdResult) shouldBe Json.toJson("Bad response from upstream; got status: 415, and got reason could not make book")
    }

}

}
