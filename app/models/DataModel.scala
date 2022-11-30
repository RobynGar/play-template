package models

import play.api.libs.json.{Json, OFormat}
import play.api.data._
import play.api.data.Forms._


case class DataModel(id: String, title: String, description: String, pageCount: Int)
object DataModel {
  implicit val formats: OFormat[DataModel] = Json.format[DataModel]

  val bookForm: Form[DataModel] = Form(
    mapping(
      "id" -> text,
      "title" -> text,
      "description" -> text,
      "pageCount" -> number
    )(DataModel.apply)(DataModel.unapply)
  )

}
