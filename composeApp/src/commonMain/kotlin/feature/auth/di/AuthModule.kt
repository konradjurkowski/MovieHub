package feature.auth.di

import feature.auth.data.remote.AuthService
import feature.auth.data.remote.AuthServiceImpl
import feature.auth.presentation.forgot_password.ForgotPasswordViewModel
import feature.auth.presentation.login.LoginViewModel
import feature.auth.presentation.register.RegisterViewModel
import feature.auth.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    singleOf(::AuthServiceImpl) bind AuthService::class

    factoryOf(::SplashViewModel)
    factoryOf(::LoginViewModel)
    factoryOf(::ForgotPasswordViewModel)
    factoryOf(::RegisterViewModel)
}
