package repositories

import models.APIError.BadAPIResponse
import models.{APIError, DataModel}
import org.mongodb.scala.bson.conversions.Bson
import org.mongodb.scala.model.Filters.empty
import org.mongodb.scala.model._
import org.mongodb.scala.result
import uk.gov.hmrc.mongo.MongoComponent
import uk.gov.hmrc.mongo.play.json.PlayMongoRepository

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DataRepository @Inject()(mongoComponent: MongoComponent)(implicit ec: ExecutionContext) extends PlayMongoRepository[DataModel](
  collectionName = "dataModels",
  mongoComponent = mongoComponent,
  domainFormat = DataModel.formats,
  indexes = Seq(IndexModel(
    Indexes.ascending("_id")
  )),
  replaceIndexes = false
) {

  def create(book: DataModel): Future[Either[APIError, DataModel]] =
    collection
       .insertOne(book)
       .toFuture()
       .map {
         case value if value.wasAcknowledged.equals(true) => Right(book)
         case _ => Left(APIError.BadAPIResponse(415, "Could not make book"))
       }



  private def byID(id: String): Bson =
    Filters.and(
      Filters.equal("_id", id)
    )

 // val emptyBook = classOf[DataModel].newInstance()

  def read(id: String): Future[Either[APIError, DataModel]] = {
    collection.find(byID(id)).headOption flatMap {
      case Some(data) =>
        Future(Right(data))
      case _ => Future(Left(APIError.BadAPIResponse(500, "Could not connect")))
    }
  }

  def update(id: String, book: DataModel): Future[Either[APIError, DataModel]] =
    collection.replaceOne(
      filter = byID(id),
      replacement = book,
      options = new ReplaceOptions().upsert(true) //What happens when we set this to false?
    ).toFuture().map{
      case value if(value.wasAcknowledged().equals(true)) => Right(book)
      case _ =>  Left(APIError.BadAPIResponse(400, "Could not update book"))
    }

  def delete(id: String): Future[Either[APIError, Int]] = {
    collection.deleteOne(
        filter = byID(id)).toFuture().map{
      case value if(value.wasAcknowledged().equals(true)) => Right(1)
      case _ =>  Left(APIError.BadAPIResponse(400, "Could not update book"))
    }
  }

  def deleteAll(): Future[Unit] = collection.deleteMany(empty()).toFuture().map(_ => ())

}
