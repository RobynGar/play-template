
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._
/*1.2*/import models.DataModel

object book extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[DataModel,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*2.2*/(book: DataModel):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*3.1*/("""
"""),format.raw/*4.1*/("""<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>book</title>
</head>
<body>
<h1>DB Book</h1>
<div>

  <ul>
    <h3>"""),_display_(/*15.10*/book/*15.14*/.title),format.raw/*15.20*/("""</h3>
    <li>ID: """),_display_(/*16.14*/book/*16.18*/.id),format.raw/*16.21*/("""</li>
    <li>Description: """),_display_(/*17.23*/book/*17.27*/.description),format.raw/*17.39*/("""</li>
    <li>Page Count: """),_display_(/*18.22*/book/*18.26*/.pageCount),format.raw/*18.36*/("""</li>
  </ul>

</div>
</body>
</html>"""))
      }
    }
  }

  def render(book:DataModel): play.twirl.api.HtmlFormat.Appendable = apply(book)

  def f:((DataModel) => play.twirl.api.HtmlFormat.Appendable) = (book) => apply(book)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-11-30T17:09:16.455253
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/play-template/app/views/book.scala.html
                  HASH: d5387e5730ef04702efe3af81b7ed427102b6b78
                  MATRIX: 432->1|762->26|873->44|900->45|1069->187|1082->191|1109->197|1155->216|1168->220|1192->223|1247->251|1260->255|1293->267|1347->294|1360->298|1391->308
                  LINES: 17->1|22->2|27->3|28->4|39->15|39->15|39->15|40->16|40->16|40->16|41->17|41->17|41->17|42->18|42->18|42->18
                  -- GENERATED --
              */
          