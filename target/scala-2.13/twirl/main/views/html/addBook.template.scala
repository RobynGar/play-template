
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
/*2.2*/import helper._

object addBook extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template3[Form[DataModel],RequestHeader,Messages,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*4.2*/(addBook: Form[DataModel])(implicit request: RequestHeader, messages: Messages):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*5.1*/("""
"""),_display_(/*6.2*/main("Add Book")/*6.18*/{_display_(Seq[Any](format.raw/*6.19*/("""
    """),format.raw/*7.5*/("""<div>
        """),_display_(/*8.10*/helper/*8.16*/.form(action = routes.ApplicationController.addBookForm())/*8.74*/ {_display_(Seq[Any](format.raw/*8.76*/("""
        """),_display_(/*9.10*/helper/*9.16*/.CSRF.formField),format.raw/*9.31*/("""
        """),_display_(/*10.10*/helper/*10.16*/.inputText(addBook("id"))),format.raw/*10.41*/("""
        """),_display_(/*11.10*/helper/*11.16*/.inputText(addBook("title"))),format.raw/*11.44*/("""
        """),_display_(/*12.10*/helper/*12.16*/.inputText(addBook("description"))),format.raw/*12.50*/("""
        """),_display_(/*13.10*/helper/*13.16*/.inputText(addBook("pageCount"))),format.raw/*13.48*/("""
        """),format.raw/*14.9*/("""<input class="submitButton" type="submit" value="Submit">
        """)))}),format.raw/*15.10*/("""
    """),format.raw/*16.5*/("""</div>
""")))}))
      }
    }
  }

  def render(addBook:Form[DataModel],request:RequestHeader,messages:Messages): play.twirl.api.HtmlFormat.Appendable = apply(addBook)(request,messages)

  def f:((Form[DataModel]) => (RequestHeader,Messages) => play.twirl.api.HtmlFormat.Appendable) = (addBook) => (request,messages) => apply(addBook)(request,messages)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-11-30T17:09:16.462395
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/play-template/app/views/addBook.scala.html
                  HASH: 1031bf52f2facd38d33338c148ff382188b58707
                  MATRIX: 432->1|463->26|817->44|990->124|1017->126|1041->142|1079->143|1110->148|1151->163|1165->169|1231->227|1270->229|1306->239|1320->245|1355->260|1392->270|1407->276|1453->301|1490->311|1505->317|1554->345|1591->355|1606->361|1661->395|1698->405|1713->411|1766->443|1802->452|1900->519|1932->524
                  LINES: 17->1|18->2|23->4|28->5|29->6|29->6|29->6|30->7|31->8|31->8|31->8|31->8|32->9|32->9|32->9|33->10|33->10|33->10|34->11|34->11|34->11|35->12|35->12|35->12|36->13|36->13|36->13|37->14|38->15|39->16
                  -- GENERATED --
              */
          