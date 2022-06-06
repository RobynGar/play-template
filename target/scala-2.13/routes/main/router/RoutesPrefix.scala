// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/robyn.garlington/Documents/scalaTraining/assignments/play-template/conf/routes
// @DATE:Mon Jun 06 10:24:10 BST 2022


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
