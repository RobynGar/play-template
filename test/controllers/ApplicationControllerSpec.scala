package controllers

import baseSpec.BaseSpecWithApplication
import cats.data.EitherT
import models.{APIError, DataModel}
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import play.api.test.FakeRequest
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContentAsEmpty, Result}
import play.api.test.Helpers.{POST, contentAsJson, defaultAwaitTimeout, route, status}
import services.ApplicationService

import scala.concurrent.Future





class ApplicationControllerSpec extends BaseSpecWithApplication with MockFactory with ScalaFutures{

  val mockServiceLayer = mock[ApplicationService]
  val TestApplicationController = new ApplicationController(
    component,
    repository,
    mockServiceLayer,
    service

  )
  private val dataModel: DataModel = DataModel(
    "abcd",
    "test name",
    "test description",
    100
  )
  private val updatedDataModel: DataModel = DataModel(
    "abcd",
    "updated test name",
    "test description",
    100
  )




  "ApplicationController .index" should {

    val result = TestApplicationController.index()(FakeRequest())

    "return" in {
      status(result) shouldBe Status.OK
    }

  }

  "ApplicationController .create()" should {

    "create a book in the database" in {
      beforeEach()
      (mockServiceLayer.create(FakeRequest[DataModel]))
      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)

      status(createdResult) shouldBe Right(Status.CREATED)
      afterEach()
    }

    "try to create a book in the database with wrong format" in {
      beforeEach()
      val request = buildPost("/api/create").withBody[JsValue](Json.obj())
      val createdResult = TestApplicationController.create()(request)
      status(createdResult) shouldBe Status.UNSUPPORTED_MEDIA_TYPE
      afterEach()
    }

  }

  "ApplicationController .read()" should {

    "find a book in the database by id" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))
      val readRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/api/abcd")
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val readResult: Future[Result] = TestApplicationController.read("abcd")(readRequest) //need to uncomment readRequest
      //val readResult: Future[Result] = TestApplicationController.read("abcd")(FakeRequest()) // works without having readRequest

      status(createdResult) shouldBe Status.CREATED
      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[DataModel] shouldBe DataModel("abcd", "test name", "test description", 100)
      afterEach()
    }

    "cannot find a book in the database as id does not exist" in {
      beforeEach()
      val readRequest: FakeRequest[AnyContentAsEmpty.type] = buildGet("/api/5")
      val readResult: Future[Result] = TestApplicationController.read("5")(readRequest)

      status(readResult) shouldBe Status.BAD_REQUEST
      afterEach()
    }

  }


  "ApplicationController .update()" should {

    "find a book in the database by id and update the fields" in {
      beforeEach()
      val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
      val updateRequest: FakeRequest[JsValue] = buildPut("/api/abcd").withBody[JsValue](Json.toJson(updatedDataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val updateResult: Future[Result] = TestApplicationController.update("abcd")(updateRequest)

      status(createdResult) shouldBe Status.CREATED
      status(updateResult) shouldBe Status.ACCEPTED
      //contentAsJson(updateResult).as[DataModel] shouldBe DataModel("abcd", "updated test name", "test description", 100) //this will not work as we are not returning the updates result in the body of accepted action
      afterEach()
    }

    "try update a book by an id that does not exist and non conforming body format" in {
      beforeEach()
      val updateRequest = buildPut("/api/4").withBody[JsValue](Json.obj())

      val updateResult = TestApplicationController.update("4")(updateRequest)

      status(updateResult) shouldBe Status.BAD_REQUEST
      afterEach()
    }

  }


  "ApplicationController .delete()" should {

      "find a book in the database by id and delete" in {
        beforeEach()
        val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
        val deleteRequest: FakeRequest[AnyContentAsEmpty.type ] = buildDelete("/api/:id")
        val createdResult: Future[Result] = TestApplicationController.create()(request)
        val deleteResult: Future[Result] = TestApplicationController.delete("abcd")(deleteRequest)

        status(createdResult) shouldBe Status.CREATED
        status(deleteResult) shouldBe Status.ACCEPTED
        afterEach()
      }

    "cannot find a book in the database as id and cannot delete" in {
      beforeEach()
      val deleteRequest: FakeRequest[AnyContentAsEmpty.type] = buildDelete("/api/8")

      val deleteResult: Future[Result] = TestApplicationController.delete("8")(deleteRequest)

      status(deleteResult) shouldBe Status.BAD_REQUEST
      afterEach()
    }

  }






  override def beforeEach(): Unit = repository.deleteAll()
  override def afterEach(): Unit = repository.deleteAll()

}
