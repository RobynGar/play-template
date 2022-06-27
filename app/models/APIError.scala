package models

import play.api.http.Status

sealed abstract class APIError(
val httpResponseStatus: Int,
  val reason: String
  )

  object APIError {

    final case class BadAPIResponse(upstreamStatus: Int, upstreamMessage: String)
      extends APIError(
        Status.INTERNAL_SERVER_ERROR,
        s"Bad response from upstream; got status: $upstreamStatus, and got reason $upstreamMessage"
      )

    final case class GenericResponse(status: Int, message: String)
      extends APIError(
        status, message
    )
    final case class JsonError(status: Int, message: String)
      extends APIError(
        status,
        message
      )

  }
