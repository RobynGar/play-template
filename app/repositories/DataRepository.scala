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
   try {
     Right(collection
       .insertOne(book)
       .toFuture()
       .map(_ => book))
   } catch {
     case _: Exception => Left(Future(APIError.BadAPIResponse(415, "Could not make book")))
   }

  private def byID(id: String): Bson =
    Filters.and(
      Filters.equal("_id", id)
    )

 // val emptyBook = classOf[DataModel].newInstance()

  def read(id: String): Future[Product] = {
    collection.find(byID(id)).headOption flatMap {
      case Some(data) =>
        Future(data)
     // case _ => Future(APIError.BadAPIResponse(500, "Could not connect"))
    }
  }

  def update(id: String, book: DataModel): Future[result.UpdateResult] =
    collection.replaceOne(
      filter = byID(id),
      replacement = book,
      options = new ReplaceOptions().upsert(true) //What happens when we set this to false?
    ).toFuture()

  def delete(id: String): Future[result.DeleteResult] = {
    collection.deleteOne(
        filter = byID(id)).toFuture()
  }

  def deleteAll(): Future[Unit] = collection.deleteMany(empty()).toFuture().map(_ => ())

}
