package controllers

import baseSpec.BaseSpecWithApplication
import models.{APIError, DataModel, Field}
import org.scalamock.scalatest.MockFactory
import play.api.http.Status
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{AnyContent, Request, Result}
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
  private val updatedNumField: Field = Field(
    "numSales",
    "3500"
  )
  private val fieldNumModel: DataModel = DataModel(
    "abcd",
    "testname",
    "test description",
    3500
  )

  private val updatedField: Field = Field(
    "name",
    "updated field name"
  )

  private val fieldStringModel: DataModel = DataModel(
    "abcd",
    "updated field name",
    "test description",
    100
  )
  private val dataModelSequence: Seq[DataModel] = Seq(dataModel)

  "ApplicationController unit test .index()" should {

    "return" in {
      val result: FakeRequest[AnyContent] = buildGet("/api")
      (() => mockServiceLayer.index()).expects().returning(Future(Right(dataModelSequence))).once()
      val indexResult: Future[Result] = unitTestController.index()(result)

      status(indexResult) shouldBe Status.OK
      contentAsJson(indexResult).as[Seq[DataModel]] shouldBe dataModelSequence
    }
  }

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


  "ApplicationController unit test .read()" should {
    "read a book in the database" in {
      val request: FakeRequest[AnyContent] = buildGet("/api/abcd")

      (mockServiceLayer.read(_ : String)).expects(*).returning(Future(Right(dataModel))).once()

      val readResult: Future[Result] = unitTestController.read("abcd")(request)

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[DataModel] shouldBe dataModel

    }

    "cannot read book" in {

      val request: FakeRequest[AnyContent] = buildGet("/api/2")

      (mockServiceLayer.read(_: String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(404, "could not read book")))).once()

      val readResult: Future[Result] = unitTestController.read("2")(request)

      status(readResult) shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(readResult) shouldBe Json.toJson("Bad response from upstream; got status: 404, and got reason could not read book")
    }

  }

  "ApplicationController unit test .readName()" should {
    "by name read a book in the database" in {
      val request: FakeRequest[AnyContent] = buildGet("/api/name/testname")

      (mockServiceLayer.readName(_ : String)).expects(*).returning(Future(Right(dataModel))).once()

      val readResult: Future[Result] = unitTestController.readName("testname")(request)

      status(readResult) shouldBe Status.OK
      contentAsJson(readResult).as[DataModel] shouldBe dataModel

    }

    "cannot read book" in {

      val request: FakeRequest[AnyContent] = buildGet("/api/unknown")

      (mockServiceLayer.readName(_: String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(404, "could not read book")))).once()

      val readNameResult: Future[Result] = unitTestController.readName("unknown")(request)

      status(readNameResult) shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(readNameResult) shouldBe Json.toJson("Bad response from upstream; got status: 404, and got reason could not read book")
    }

  }


  "ApplicationController unit test .update()" should {
    "update a book in the database" in {
      val request: FakeRequest[JsValue] = buildPut("/api/abcd").withBody[JsValue](Json.toJson(dataModel))

      (mockServiceLayer.update(_ : String, _ : Request[JsValue])).expects(*, request).returning(Future(Right(dataModel))).once()

      val updateResult: Future[Result] = unitTestController.update("abcd")(request)

      status(updateResult) shouldBe Status.ACCEPTED
      contentAsJson(updateResult).as[DataModel] shouldBe dataModel

    }

    "cannot update book" in {

      val request: FakeRequest[JsValue] = buildPut("/api/2").withBody[JsValue](Json.obj())

      (mockServiceLayer.update(_ : String, _ : Request[JsValue])).expects(*, request).returning(Future(Left(APIError.BadAPIResponse(400, "could not update book")))).once()

      val readResult: Future[Result] = unitTestController.update("2")(request)

      status(readResult) shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(readResult) shouldBe Json.toJson("Bad response from upstream; got status: 400, and got reason could not update book")
    }

  }

  "ApplicationController unit test .updateField()" should {
    "update one string field a book in the database" in {
      val request: FakeRequest[JsValue] = buildPut("/api/update/abcd").withBody[JsValue](Json.toJson(updatedField))

      (mockServiceLayer.updateField(_ : String, _ : Request[JsValue])).expects(*, request).returning(Future(Right(fieldStringModel))).once()

      val updateResult: Future[Result] = unitTestController.updateField("abcd")(request)

      status(updateResult) shouldBe Status.ACCEPTED
      contentAsJson(updateResult).as[DataModel] shouldBe fieldStringModel

    }

    "update one int field a book in the database" in {
      val request: FakeRequest[JsValue] = buildPut("/api/update/abcd").withBody[JsValue](Json.toJson(updatedNumField))

      (mockServiceLayer.updateField(_ : String, _ : Request[JsValue])).expects(*, request).returning(Future(Right(fieldNumModel))).once()

      val updateResult: Future[Result] = unitTestController.updateField("abcd")(request)

      status(updateResult) shouldBe Status.ACCEPTED
      contentAsJson(updateResult).as[DataModel] shouldBe fieldNumModel

    }

    "cannot update one field of a book" in {

      val request: FakeRequest[JsValue] = buildPut("/api/update/2").withBody[JsValue](Json.obj())

      (mockServiceLayer.updateField(_ : String, _ : Request[JsValue])).expects(*, request).returning(Future(Left(APIError.BadAPIResponse(400, "could not update book")))).once()

      val updateResult: Future[Result] = unitTestController.updateField("2")(request)

      status(updateResult) shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(updateResult) shouldBe Json.toJson("Bad response from upstream; got status: 400, and got reason could not update book")
    }

  }

  "ApplicationController unit test .delete()" should {
    "delete a book in the database" in {
      val request: FakeRequest[AnyContent] = buildDelete("/api/abcd")

      (mockServiceLayer.delete(_ : String)).expects(*).returning(Future(Right("deleted"))).once()

      val deleteResult: Future[Result] = unitTestController.delete("abcd")(request)

      status(deleteResult) shouldBe Status.ACCEPTED

    }

    "cannot delete book" in {
      val request: FakeRequest[AnyContent] = buildDelete("/api/abcd")

      (mockServiceLayer.delete(_ : String)).expects(*).returning(Future(Left(APIError.BadAPIResponse(404, "could not delete book")))).once()

      val deleteResult: Future[Result] = unitTestController.delete("abcd")(request)


      status(deleteResult) shouldBe Status.INTERNAL_SERVER_ERROR
      contentAsJson(deleteResult) shouldBe Json.toJson("Bad response from upstream; got status: 404, and got reason could not delete book")
    }

  }

}
