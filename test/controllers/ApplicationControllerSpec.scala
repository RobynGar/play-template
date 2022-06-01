package controllers

import baseSpec.BaseSpecWithApplication
import models.DataModel
import play.api.test.FakeRequest
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{Action, AnyContentAsEmpty, Result}
import play.api.test.Helpers.{contentAsJson, defaultAwaitTimeout, status}
import scala.concurrent.Future





class ApplicationControllerSpec extends BaseSpecWithApplication{


  val TestApplicationController = new ApplicationController(
    component,
    repository
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
    beforeEach()
    val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))
    val createdResult: Future[Result] = TestApplicationController.create()(request)


    "create a book in the database" in {

      status(createdResult) shouldBe Status.CREATED
    }
    afterEach()
  }


  "ApplicationController .read()" should {
    beforeEach()
    "find a book in the database by id" in {

      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))
      //val readRequest: FakeRequest[AnyContentAsEmpty.type ] = buildGet("/api/read/:id")
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      //val readResult: Future[Result] = TestApplicationController.read("abcd")(readRequest) //need to uncomment readRequest
      val readResult: Future[Result] = TestApplicationController.read("abcd")(FakeRequest()) // works without having readRequest

      status(createdResult) shouldBe Status.CREATED
      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[DataModel] shouldBe DataModel("abcd", "test name", "test description", 100)

    }
    afterEach()
  }

  "ApplicationController .update()" should {
    beforeEach()
    "find a book in the database by id and update the fields" in {

      val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
      val updateRequest: FakeRequest[JsValue] = buildPut("/api/update/:id").withBody[JsValue](Json.toJson(updatedDataModel))
      val createdResult: Future[Result] = TestApplicationController.create()(request)
      val updateResult: Future[Result] = TestApplicationController.update("abcd")(updateRequest)

      status(createdResult) shouldBe Status.CREATED
      status(updateResult) shouldBe Status.ACCEPTED
      //contentAsJson(updateResult).as[DataModel] shouldBe DataModel("abcd", "updated test name", "test description", 100) //this will not work as we are not returning the updates result in the body of accepted action
    }
    afterEach()
  }

  "ApplicationController .delete()" should {
    beforeEach()
      "find a book in the database by id and update the fields" in {

        val request: FakeRequest[JsValue] = buildPost("/api").withBody[JsValue](Json.toJson(dataModel))
        val deleteRequest: FakeRequest[AnyContentAsEmpty.type ] = buildDelete("/api/delete/:id")
        val createdResult: Future[Result] = TestApplicationController.create()(request)
        val deleteResult: Future[Result] = TestApplicationController.delete("abcd")(deleteRequest)

        status(createdResult) shouldBe Status.CREATED
        status(deleteResult) shouldBe Status.ACCEPTED

      }
    afterEach()

  }

  override def beforeEach(): Unit = repository.deleteAll()
  override def afterEach(): Unit = repository.deleteAll()

}