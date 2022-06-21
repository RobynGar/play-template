package models

import play.api.libs.json.{Json, OFormat}
//import reactivemongo.api.bson.{BSONDocument, BSONDocumentReader, BSONObjectID}


case class DataModel(_id: String, name: String, description: String, numSales: Int)
object DataModel {
  implicit val formats: OFormat[DataModel] = Json.format[DataModel]
//  implicit object BookBSONReader extends BSONDocumentReader[DataModel] {
//    def read(doc: BSONDocument): DataModel = {
//      DataModel(
//        doc.get("id"),
//        doc.get("title"),
//        doc.get("description"),
//        doc.get("")
//
//      //getAs[BSONObjectID]("_id")
////        doc.getAs[BSONDateTime]("_creationDate").map(dt => new DateTime(dt.value)),
////        doc.getAs[BSONDateTime]("_updateDate").map(dt => new DateTime(dt.value)),
////        doc.getAs[String]("title").get,
////        doc.getAs[String]("description").get)
//    }
//  }

}
