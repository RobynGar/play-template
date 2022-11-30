// @GENERATOR:play-routes-compiler
// @SOURCE:conf/routes

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:2
package controllers {

  // @LINE:2
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:2
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:3
  class ReverseApplicationController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def addFromApi(search:String, term:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/addGoogle/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("search", search)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("term", term)))
    }
  
    // @LINE:11
    def updateField(id:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "api/update/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:5
    def read(id:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:9
    def readName(name:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/name/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("name", name)))
    }
  
    // @LINE:4
    def create(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "api/create")
    }
  
    // @LINE:20
    def getGoogleBook(search:String, term:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "library/getGoogle/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("search", search)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("term", term)))
    }
  
    // @LINE:7
    def showBook(search:String, term:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/showBook/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("search", search)) + "/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("term", term)))
    }
  
    // @LINE:13
    def addNewBook(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/addbook/form")
    }
  
    // @LINE:14
    def addBookForm(): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "api/addbook/form")
    }
  
    // @LINE:12
    def delete(id:String): Call = {
      
      Call("DELETE", _prefix + { _defaultPrefix } + "api/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:6
    def showDBBook(id:String): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api/dbbook/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:10
    def update(id:String): Call = {
      
      Call("PUT", _prefix + { _defaultPrefix } + "api/" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("id", id)))
    }
  
    // @LINE:3
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "api")
    }
  
  }

  // @LINE:17
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:17
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
