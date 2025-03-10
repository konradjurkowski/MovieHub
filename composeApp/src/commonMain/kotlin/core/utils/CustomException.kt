package core.utils

import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.something_went_wrong
import org.jetbrains.compose.resources.StringResource

abstract class CustomException(val messageRes: StringResource = Res.string.something_went_wrong) : Throwable()

// TODO ADD CUSTOM MESSAGES
class GenericException : CustomException()
class FailureResponseException: CustomException()
class FirebaseMovieExistException : CustomException()
class FirebaseMovieNotExistException : CustomException()
class FirebaseSeriesExistException : CustomException()
class FirebaseSeriesNotExistException : CustomException()
class UserExistException : CustomException()
class NoInternetConnectionException : CustomException()
