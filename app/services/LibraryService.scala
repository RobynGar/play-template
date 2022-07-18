package services


import cats.data.EitherT
import connectors.LibraryConnector
import models.{APIError, DataModel}
import repositories.{DataRepository, TraitDataRepo}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}


@Singleton
class LibraryService @Inject()(connector: LibraryConnector, dataRepository: TraitDataRepo){

  def getGoogleBook(urlOverride: Option[String] = None, search: String, term: String)(implicit ec: ExecutionContext): EitherT[Future, APIError, DataModel] = {
    connector.get[DataModel](urlOverride.getOrElse(s"https://www.googleapis.com/books/v1/volumes?q=$search%$term"))
  }

  def addApiUser(search: String, term: String)(implicit ec: ExecutionContext):Future[Either[APIError, DataModel]]= {
    getGoogleBook(search = search, term = term).value.flatMap{
      case Right(book: DataModel) => dataRepository.create(book)
      case Left(error) => Future(Left(APIError.BadAPIResponse(400, "could not add book")))
    }
  }


}
