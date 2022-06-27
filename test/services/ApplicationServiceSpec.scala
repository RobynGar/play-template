package services

import baseSpec.BaseSpecWithApplication
import models.{APIError, DataModel, Field}
import org.scalamock.scalatest.MockFactory
import org.scalatest.EitherValues
import org.scalatest.concurrent.IntegrationPatience
import play.api.libs.json.{JsValue, Json}
import play.api.test.{DefaultAwaitTimeout, FakeRequest, FutureAwaits}
import repositories.TraitDataRepo

import scala.concurrent.Future

//mock dataRepository and test service layer

class ApplicationServiceSpec extends BaseSpecWithApplication with MockFactory with EitherValues with FutureAwaits with DefaultAwaitTimeout with IntegrationPatience {

  val mockDataRepository: TraitDataRepo = mock[TraitDataRepo]

  val UnitTestApplicationService = new ApplicationService(
    mockDataRepository
  )
  //added to attempt pull request
  private val dataModel: DataModel = DataModel(
    "abcd",
    "testname",
    "test description",
    100
  )
  private val dataModel2: DataModel = DataModel(
    "blob",
    "testname2",
    "test description2",
    10000
  )

  private val updatedDataModel: DataModel = DataModel(
    "abcd",
    "updated field name",
    "test description",
    100
  )
  private val updatedField: Field = Field(
    "title",
    "updated field name"
  )

  private val dataModelSequence: Seq[DataModel] = Seq(dataModel, dataModel2)

    "ApplicationService .index()" should {
      "receives a sequence of books" in {
        (() => mockDataRepository.index()).expects()
          .returning(Future(Right(dataModelSequence)))

        whenReady(UnitTestApplicationService.index()) {result =>
          result shouldBe Right(dataModelSequence)
        }
      }

      "receives a error from controller" in {
        (() => mockDataRepository.index()).expects()
          .returning(Future(Left(APIError.BadAPIResponse(404, "could not find books"))))

        whenReady(UnitTestApplicationService.index()) {result =>
          result shouldBe Left(APIError.BadAPIResponse(404, "could not find books"))
        }
      }
    }

  "ApplicationService .create()" should {


    "validate a dataModel, with mock data repository" in {
      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))
      (mockDataRepository.create(_: DataModel))
        .expects(dataModel)
        .returning(Future(Right(dataModel))).once()

      whenReady(UnitTestApplicationService.create(request)) { result =>
        result shouldBe Right(dataModel)
      }
    }

    "unable to validate a dataModel, with mock data repository" in {
      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.obj())

      whenReady(UnitTestApplicationService.create(request)) { result =>
        result shouldBe Left(APIError.BadAPIResponse(415, "could not make book"))
      }
    }
    "validate a dataModel, but mock data repository returns error" in {
      val request: FakeRequest[JsValue] = buildPost("/api/create").withBody[JsValue](Json.toJson(dataModel))
      (mockDataRepository.create(_: DataModel))
        .expects(dataModel)
        .returning(Future(Left(APIError.BadAPIResponse(415, "could not make book")))).once()

      whenReady(UnitTestApplicationService.create(request)) { result =>
        result shouldBe Left(APIError.BadAPIResponse(415, "could not make book"))
      }
    }

  }

  "ApplicationService .read()" should {

    "receives a valid id string" in {
      (mockDataRepository.read(_: String))
        .expects(*)
        .returning(Future(Right(dataModel))).once()

      whenReady(UnitTestApplicationService.read("blob")) { result =>
        result shouldBe Right(dataModel)
      }
    }

    "does not receives a valid id string" in {
      (mockDataRepository.read(_: String))
        .expects(*)
        .returning(Future(Left(APIError.BadAPIResponse(404, "could not find book")))).once()

      whenReady(UnitTestApplicationService.read("blob")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(404, "could not find book"))
      }
    }
  }


  "ApplicationService .readName()" should {

    "receives a valid name string" in {
      (mockDataRepository.readName(_: String))
        .expects(*)
        .returning(Future(Right(dataModel))).once()

      whenReady(UnitTestApplicationService.readName("blob")) { result =>
        result shouldBe Right(dataModel)
      }
    }

    "does not receives a valid name string" in {
      (mockDataRepository.readName(_: String))
        .expects(*)
        .returning(Future(Left(APIError.BadAPIResponse(404, "could not find book")))).once()

      whenReady(UnitTestApplicationService.readName("blob")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(404, "could not find book"))
      }
    }
  }


  "ApplicationService .update()" should {

    "receives a valid id string and book" in {
      val request: FakeRequest[JsValue] = buildPut("/api/abcd").withBody[JsValue](Json.toJson(dataModel))
      (mockDataRepository.update(_: String, _: DataModel))
        .expects(*, *)
        .returning(Future(Right(dataModel))).once()

      whenReady(UnitTestApplicationService.update("abcd", request)) { result =>
        result shouldBe Right(dataModel)
      }
    }

    "receives a valid book but invalid id string " in {
      val request: FakeRequest[JsValue] = buildPut("/api/abcd").withBody[JsValue](Json.toJson(dataModel))

      (mockDataRepository.update(_: String, _: DataModel))
        .expects(*, *)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not update book")))).once()

      whenReady(UnitTestApplicationService.update("blob", request)) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not update book"))
      }
    }
     "correct id but unable to validate a dataModel, with mock data repository" in {
          val request: FakeRequest[JsValue] = buildPut("/api/abcd").withBody[JsValue](Json.obj())

          whenReady(UnitTestApplicationService.update("abcd", request)) { result =>
            result shouldBe Left(APIError.BadAPIResponse(400, "could not update book"))
          }
        }

  }

  "ApplicationService .updateField()" should {

    "receives a valid id string and book" in {
      val request: FakeRequest[JsValue] = buildPut("/api/update/abcd").withBody[JsValue](Json.toJson(updatedField))
      (mockDataRepository.updateField(_: String, _: String, _: String))
        .expects(*, *, *)
        .returning(Future(Right(updatedDataModel))).once()

      whenReady(UnitTestApplicationService.updateField("abcd", request)) { result =>
        result shouldBe Right(updatedDataModel)
      }
    }

    "receives a valid book but invalid id string " in {
      val request: FakeRequest[JsValue] = buildPut("/api/update/blob").withBody[JsValue](Json.toJson(updatedField))

      (mockDataRepository.updateField(_: String,_: String, _: String))
        .expects(*, *, *)
        .returning(Future(Left(APIError.BadAPIResponse(400, "could not update book")))).once()

      whenReady(UnitTestApplicationService.updateField("blob", request)) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not update book"))
      }
    }

    "correct id but unable to validate a dataModel, with mock data repository" in {
      val request: FakeRequest[JsValue] = buildPost("/api/update/abcd").withBody[JsValue](Json.obj())

      whenReady(UnitTestApplicationService.updateField("abcd", request)) { result =>
        result shouldBe Left(APIError.BadAPIResponse(400, "could not update book"))
      }
    }

  }


  "ApplicationService .delete()" should {

    "receives a valid id string" in {
      (mockDataRepository.delete(_: String))
        .expects(*)
        .returning(Future(Right("deleted"))).once()

      whenReady(UnitTestApplicationService.delete("blob")) { result =>
        result shouldBe Right("deleted")
      }
    }

    "does not receives a valid id string" in {
      (mockDataRepository.delete(_: String))
        .expects(*)
        .returning(Future(Left(APIError.BadAPIResponse(404, "could not find book")))).once()

      whenReady(UnitTestApplicationService.delete("blob")) { result =>
        result shouldBe Left(APIError.BadAPIResponse(404, "could not delete book"))
      }
    }
  }

}



