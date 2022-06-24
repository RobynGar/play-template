package models

import play.api.libs.json.{Json, OFormat}
import org.mongodb.scala.bson.{BsonDocument, BsonObjectId}
//import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONObjectID}


case class DataModel(_id: String, name: String, description: String, numSales: Int)
object DataModel {
  implicit val formats: OFormat[DataModel] = Json.format[DataModel]

//  implicit object BookBSONReader {
//    def read(doc: BsonDocument): DataModel = {
//      DataModel(
//        doc.getString("id").toString, //.get("id").toString, //or this not sure if either work
//        doc.get("title").toString,
//        doc.get("description").toString,
//        doc.getInt32("pageNumber").intValue())
//    }
//  }

}
