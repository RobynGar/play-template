package models

import play.api.libs.json.{Json, OFormat}

case class Field(fieldName: String, value: String)
object Field {
  implicit val formats: OFormat[Field] = Json.format[Field]

}