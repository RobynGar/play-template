package repositories

import com.google.inject.ImplementedBy
import models.{APIError, DataModel}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.{empty, equal}
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.model._
import play.api.libs.json.{JsValue, Json}
import play.libs.Json
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[DataRepository])
trait TraitDataRepo {
  def index(): Future[Either[APIError, Seq[DataModel]]]
  def create(book: DataModel): Future[Either[APIError, DataModel]]
  def read(id: String): Future[Either[APIError, DataModel]]
  def readName(id: String): Future[Either[APIError, DataModel]]
  def update(id: String, book: DataModel): Future[Either[APIError, DataModel]]
  def updateField(id: String, fieldName: String, value: String): Future[Either[APIError, DataModel]]
  def delete(id: String): Future[Either[APIError, String]]
}

@Singleton
class DataRepository @Inject()(mongoComponent: MongoComponent)(implicit ec: ExecutionContext) extends PlayMongoRepository[DataModel] (
  collectionName = "dataModels",
  mongoComponent = mongoComponent,
  domainFormat = DataModel.formats,
  indexes = Seq(IndexModel(
    Indexes.ascending("_id")
  )
)) with TraitDataRepo {



  def index(): Future[Either[APIError, Seq[DataModel]]]  =
    collection.find().toFuture().map{
      case books: Seq[DataModel] => Right(books)
      case _ => Left(APIError.BadAPIResponse(404, "could not find books"))
    }

  def create(book: DataModel): Future[Either[APIError, DataModel]] =
    collection
       .insertOne(book)
       .toFutureOption()
       .map {
         case Some(value) if value.wasAcknowledged() => Right(book)
         case _ => Left(APIError.BadAPIResponse(415, "could not make book"))
       }



  private def byID(id: String): Bson =
    Filters.and(
      Filters.equal("_id", id)
    )

  private def byName(name: String): Bson =
    Filters.and(
      Filters.equal("name", name)
    )

 // val emptyBook = classOf[DataModel].newInstance()

  def read(id: String): Future[Either[APIError, DataModel]] = {
    collection.find(byID(id)).headOption flatMap {
      case Some(data) =>
        Future(Right(data))
      case _ => Future(Left(APIError.BadAPIResponse(404, "could not read book")))
    }
  }

  def readName(name: String): Future[Either[APIError, DataModel]] = {
    collection.find(byName(name)).headOption flatMap {
      case Some(data) =>
        Future(Right(data))
      case _ => Future(Left(APIError.BadAPIResponse(404, "could not read book")))
    }
  }

  def update(id: String, book: DataModel): Future[Either[APIError, DataModel]] =
    collection.replaceOne(
      filter = byID(id), //selection criteria
      replacement = book, //replacement document
      options = new ReplaceOptions().upsert(true) //upsert when true inserts what replacement is = to if no document matches the filter (no matching id) or if does match replaces it with what is in replacement
    ).toFutureOption().map{
      case Some(value) if value.wasAcknowledged() => Right(book)
      case _ =>  Left(APIError.BadAPIResponse(400, "could not update book"))
    }

  def updateField(id: String, fieldName: String, value: String): Future[Either[APIError, DataModel]]= {
    if (fieldName == "numSales") {
      collection.findOneAndUpdate(equal("_id", id),
        set(fieldName, value.toInt),
        options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
      ).toFutureOption().map {
        case Some(value: DataModel) => Right(value)
        case _ => Left(APIError.BadAPIResponse(400, "could not update book"))
      }
    } else {
      collection.findOneAndUpdate(equal("_id", id),
        set(fieldName, value),
        options = FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
      ).toFutureOption().map {
        case Some(value: DataModel) => Right(value)
        case _ => Left(APIError.BadAPIResponse(400, "could not update book"))
      }
    }

  }

  def delete(id: String): Future[Either[APIError, String]] = {
    collection.deleteOne(
        filter = byID(id)).toFutureOption().map{
      case Some(value) if value.getDeletedCount == 1 => Right("deleted")
      //case value if value.wasAcknowledged().equals(true) => Right("deleted")
      case _ =>  Left(APIError.BadAPIResponse(400, "Could not update book"))
    }
  }

  def deleteAll(): Future[Unit] = collection.deleteMany(empty()).toFuture().map(_ => ())

}
