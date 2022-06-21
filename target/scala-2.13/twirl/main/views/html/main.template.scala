
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

object main extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template2[String,Html,play.twirl.api.HtmlFormat.Appendable] {

  /*
 * This template is called from the `index` template. This template
 * handles the rendering of the page header and body tags. It takes
 * two arguments, a `String` for the title of the page and an `Html`
 * object to insert into the body of the page.
 */
  def apply/*7.2*/(title: String)(content: Html):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*8.1*/("""
"""),format.raw/*9.1*/("""<!DOCTYPE html>
<html lang="en">
    <head>
        """),format.raw/*12.62*/("""
        """),format.raw/*13.9*/("""<title>"""),_display_(/*13.17*/title),format.raw/*13.22*/("""</title>
        <link rel="stylesheet" media="screen" href=""""),_display_(/*14.54*/routes/*14.60*/.Assets.versioned("stylesheets/main.css")),format.raw/*14.101*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(/*15.59*/routes/*15.65*/.Assets.versioned("images/favicon.png")),format.raw/*15.104*/("""">

    </head>
    <body>
        """),format.raw/*20.32*/("""
        """),_display_(/*21.10*/content),format.raw/*21.17*/("""

      """),format.raw/*23.7*/("""<script src=""""),_display_(/*23.21*/routes/*23.27*/.Assets.versioned("javascripts/main.js")),format.raw/*23.67*/("""" type="text/javascript"></script>

<!--    <div id="googleBooks"></div>-->

<!--    <script>-->

<!--        showBooks = books => """),format.raw/*29.34*/("""{"""),format.raw/*29.35*/("""-->
<!--        const booksDiv = document.querySelector('#googleBooks');-->
<!--         books.forEach(book => """),format.raw/*31.36*/("""{"""),format.raw/*31.37*/("""-->
<!--    const bookElement = document.createElement(‘p’);-->
<!--    bookElement.innerText = `book Title: $"""),format.raw/*33.47*/("""{"""),format.raw/*33.48*/("""book.title"""),format.raw/*33.58*/("""}"""),format.raw/*33.59*/("""`;-->
<!--    booksDiv.append(bookElement);-->

<!--  """),format.raw/*36.7*/("""}"""),format.raw/*36.8*/(""");-->
<!--        """),format.raw/*37.13*/("""}"""),format.raw/*37.14*/("""-->

<!--       fetch('https://www.googleapis.com/books/v1/volumes?q=rowling%potter')-->
<!--       .then(response => response.json())-->
<!--       .then(books => showBooks(books.result));-->

<!--    </script>-->
    </body>
</html>
"""))
      }
    }
  }

  def render(title:String,content:Html): play.twirl.api.HtmlFormat.Appendable = apply(title)(content)

  def f:((String) => (Html) => play.twirl.api.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: 2022-06-21T11:58:36.101
                  SOURCE: /Users/robyn.garlington/Documents/scalaTraining/assignments/play-template/app/views/main.scala.html
                  HASH: d0b48370b69c39c708f55c842925ade0e311e24c
                  MATRIX: 987->260|1111->291|1138->292|1218->397|1254->406|1289->414|1315->419|1404->481|1419->487|1482->528|1570->589|1585->595|1646->634|1709->759|1746->769|1774->776|1809->784|1850->798|1865->804|1926->844|2085->975|2114->976|2253->1087|2282->1088|2420->1198|2449->1199|2487->1209|2516->1210|2597->1264|2625->1265|2671->1283|2700->1284
                  LINES: 26->7|31->8|32->9|35->12|36->13|36->13|36->13|37->14|37->14|37->14|38->15|38->15|38->15|42->20|43->21|43->21|45->23|45->23|45->23|45->23|51->29|51->29|53->31|53->31|55->33|55->33|55->33|55->33|58->36|58->36|59->37|59->37
                  -- GENERATED --
              */
          