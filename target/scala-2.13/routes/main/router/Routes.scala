// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:2
  HomeController_0: controllers.HomeController,
  // @LINE:3
  ApplicationController_1: controllers.ApplicationController,
  // @LINE:17
  Assets_2: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:2
    HomeController_0: controllers.HomeController,
    // @LINE:3
    ApplicationController_1: controllers.ApplicationController,
    // @LINE:17
    Assets_2: controllers.Assets
  ) = this(errorHandler, HomeController_0, ApplicationController_1, Assets_2, "/")

  def withPrefix(addPrefix: String): Routes = {
    val prefix = play.api.routing.Router.concatPrefix(addPrefix, this.prefix)
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, HomeController_0, ApplicationController_1, Assets_2, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.HomeController.index()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api""", """controllers.ApplicationController.index()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/create""", """controllers.ApplicationController.create()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/""" + "$" + """id<[^/]+>""", """controllers.ApplicationController.read(id:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/dbbook/""" + "$" + """id<[^/]+>""", """controllers.ApplicationController.showDBBook(id:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/showBook/""" + "$" + """search<[^/]+>/""" + "$" + """term<[^/]+>""", """controllers.ApplicationController.showBook(search:String, term:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/addGoogle/""" + "$" + """search<[^/]+>/""" + "$" + """term<[^/]+>""", """controllers.ApplicationController.addFromApi(search:String, term:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/name/""" + "$" + """name<[^/]+>""", """controllers.ApplicationController.readName(name:String)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/""" + "$" + """id<[^/]+>""", """controllers.ApplicationController.update(id:String)"""),
    ("""PUT""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/update/""" + "$" + """id<[^/]+>""", """controllers.ApplicationController.updateField(id:String)"""),
    ("""DELETE""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/""" + "$" + """id<[^/]+>""", """controllers.ApplicationController.delete(id:String)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/addbook/form""", """controllers.ApplicationController.addNewBook()"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """api/addbook/form""", """controllers.ApplicationController.addBookForm()"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """library/getGoogle/""" + "$" + """search<[^/]+>/""" + "$" + """term<[^/]+>""", """controllers.ApplicationController.getGoogleBook(search:String, term:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:2
  private[this] lazy val controllers_HomeController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_HomeController_index0_invoker = createInvoker(
    HomeController_0.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.HomeController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ An example controller showing a sample home page""",
      Seq()
    )
  )

  // @LINE:3
  private[this] lazy val controllers_ApplicationController_index1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api")))
  )
  private[this] lazy val controllers_ApplicationController_index1_invoker = createInvoker(
    ApplicationController_1.index(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "index",
      Nil,
      "GET",
      this.prefix + """api""",
      """""",
      Seq()
    )
  )

  // @LINE:4
  private[this] lazy val controllers_ApplicationController_create2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/create")))
  )
  private[this] lazy val controllers_ApplicationController_create2_invoker = createInvoker(
    ApplicationController_1.create(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "create",
      Nil,
      "POST",
      this.prefix + """api/create""",
      """""",
      Seq()
    )
  )

  // @LINE:5
  private[this] lazy val controllers_ApplicationController_read3_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_read3_invoker = createInvoker(
    ApplicationController_1.read(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "read",
      Seq(classOf[String]),
      "GET",
      this.prefix + """api/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:6
  private[this] lazy val controllers_ApplicationController_showDBBook4_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/dbbook/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_showDBBook4_invoker = createInvoker(
    ApplicationController_1.showDBBook(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "showDBBook",
      Seq(classOf[String]),
      "GET",
      this.prefix + """api/dbbook/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:7
  private[this] lazy val controllers_ApplicationController_showBook5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/showBook/"), DynamicPart("search", """[^/]+""",true), StaticPart("/"), DynamicPart("term", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_showBook5_invoker = createInvoker(
    ApplicationController_1.showBook(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "showBook",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """api/showBook/""" + "$" + """search<[^/]+>/""" + "$" + """term<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_ApplicationController_addFromApi6_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/addGoogle/"), DynamicPart("search", """[^/]+""",true), StaticPart("/"), DynamicPart("term", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_addFromApi6_invoker = createInvoker(
    ApplicationController_1.addFromApi(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "addFromApi",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """api/addGoogle/""" + "$" + """search<[^/]+>/""" + "$" + """term<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_ApplicationController_readName7_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/name/"), DynamicPart("name", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_readName7_invoker = createInvoker(
    ApplicationController_1.readName(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "readName",
      Seq(classOf[String]),
      "GET",
      this.prefix + """api/name/""" + "$" + """name<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_ApplicationController_update8_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_update8_invoker = createInvoker(
    ApplicationController_1.update(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "update",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """api/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_ApplicationController_updateField9_route = Route("PUT",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/update/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_updateField9_invoker = createInvoker(
    ApplicationController_1.updateField(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "updateField",
      Seq(classOf[String]),
      "PUT",
      this.prefix + """api/update/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:12
  private[this] lazy val controllers_ApplicationController_delete10_route = Route("DELETE",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/"), DynamicPart("id", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_delete10_invoker = createInvoker(
    ApplicationController_1.delete(fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "delete",
      Seq(classOf[String]),
      "DELETE",
      this.prefix + """api/""" + "$" + """id<[^/]+>""",
      """""",
      Seq()
    )
  )

  // @LINE:13
  private[this] lazy val controllers_ApplicationController_addNewBook11_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/addbook/form")))
  )
  private[this] lazy val controllers_ApplicationController_addNewBook11_invoker = createInvoker(
    ApplicationController_1.addNewBook(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "addNewBook",
      Nil,
      "GET",
      this.prefix + """api/addbook/form""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_ApplicationController_addBookForm12_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("api/addbook/form")))
  )
  private[this] lazy val controllers_ApplicationController_addBookForm12_invoker = createInvoker(
    ApplicationController_1.addBookForm(),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "addBookForm",
      Nil,
      "POST",
      this.prefix + """api/addbook/form""",
      """""",
      Seq()
    )
  )

  // @LINE:17
  private[this] lazy val controllers_Assets_versioned13_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned13_invoker = createInvoker(
    Assets_2.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )

  // @LINE:20
  private[this] lazy val controllers_ApplicationController_getGoogleBook14_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("library/getGoogle/"), DynamicPart("search", """[^/]+""",true), StaticPart("/"), DynamicPart("term", """[^/]+""",true)))
  )
  private[this] lazy val controllers_ApplicationController_getGoogleBook14_invoker = createInvoker(
    ApplicationController_1.getGoogleBook(fakeValue[String], fakeValue[String]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.ApplicationController",
      "getGoogleBook",
      Seq(classOf[String], classOf[String]),
      "GET",
      this.prefix + """library/getGoogle/""" + "$" + """search<[^/]+>/""" + "$" + """term<[^/]+>""",
      """""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:2
    case controllers_HomeController_index0_route(params@_) =>
      call { 
        controllers_HomeController_index0_invoker.call(HomeController_0.index())
      }
  
    // @LINE:3
    case controllers_ApplicationController_index1_route(params@_) =>
      call { 
        controllers_ApplicationController_index1_invoker.call(ApplicationController_1.index())
      }
  
    // @LINE:4
    case controllers_ApplicationController_create2_route(params@_) =>
      call { 
        controllers_ApplicationController_create2_invoker.call(ApplicationController_1.create())
      }
  
    // @LINE:5
    case controllers_ApplicationController_read3_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_ApplicationController_read3_invoker.call(ApplicationController_1.read(id))
      }
  
    // @LINE:6
    case controllers_ApplicationController_showDBBook4_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_ApplicationController_showDBBook4_invoker.call(ApplicationController_1.showDBBook(id))
      }
  
    // @LINE:7
    case controllers_ApplicationController_showBook5_route(params@_) =>
      call(params.fromPath[String]("search", None), params.fromPath[String]("term", None)) { (search, term) =>
        controllers_ApplicationController_showBook5_invoker.call(ApplicationController_1.showBook(search, term))
      }
  
    // @LINE:8
    case controllers_ApplicationController_addFromApi6_route(params@_) =>
      call(params.fromPath[String]("search", None), params.fromPath[String]("term", None)) { (search, term) =>
        controllers_ApplicationController_addFromApi6_invoker.call(ApplicationController_1.addFromApi(search, term))
      }
  
    // @LINE:9
    case controllers_ApplicationController_readName7_route(params@_) =>
      call(params.fromPath[String]("name", None)) { (name) =>
        controllers_ApplicationController_readName7_invoker.call(ApplicationController_1.readName(name))
      }
  
    // @LINE:10
    case controllers_ApplicationController_update8_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_ApplicationController_update8_invoker.call(ApplicationController_1.update(id))
      }
  
    // @LINE:11
    case controllers_ApplicationController_updateField9_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_ApplicationController_updateField9_invoker.call(ApplicationController_1.updateField(id))
      }
  
    // @LINE:12
    case controllers_ApplicationController_delete10_route(params@_) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_ApplicationController_delete10_invoker.call(ApplicationController_1.delete(id))
      }
  
    // @LINE:13
    case controllers_ApplicationController_addNewBook11_route(params@_) =>
      call { 
        controllers_ApplicationController_addNewBook11_invoker.call(ApplicationController_1.addNewBook())
      }
  
    // @LINE:14
    case controllers_ApplicationController_addBookForm12_route(params@_) =>
      call { 
        controllers_ApplicationController_addBookForm12_invoker.call(ApplicationController_1.addBookForm())
      }
  
    // @LINE:17
    case controllers_Assets_versioned13_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned13_invoker.call(Assets_2.versioned(path, file))
      }
  
    // @LINE:20
    case controllers_ApplicationController_getGoogleBook14_route(params@_) =>
      call(params.fromPath[String]("search", None), params.fromPath[String]("term", None)) { (search, term) =>
        controllers_ApplicationController_getGoogleBook14_invoker.call(ApplicationController_1.getGoogleBook(search, term))
      }
  }
}
