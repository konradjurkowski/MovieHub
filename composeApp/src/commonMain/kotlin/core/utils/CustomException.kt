package core.utils

import moviehub.composeapp.generated.resources.Res
import moviehub.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

@OptIn(ExperimentalResourceApi::class)
abstract class CustomException(val messageRes: StringResource = Res.string.app_name) : Throwable()

@OptIn(ExperimentalResourceApi::class)
class SomethingWentWrongException: CustomException()

@OptIn(ExperimentalResourceApi::class)
class FailureResponseException: CustomException()
