package feature.auth.di

import feature.auth.data.remote.AuthService
import feature.auth.data.remote.AuthServiceImpl
import feature.auth.data.remote.UserRepository
import feature.auth.data.remote.UserRepositoryImpl
import feature.auth.presentation.forgot_password.ForgotPasswordViewModel
import feature.auth.presentation.login.LoginViewModel
import feature.auth.presentation.register.RegisterViewModel
import feature.auth.presentation.splash.SplashViewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthService> { AuthServiceImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    factory<SplashViewModel> { SplashViewModel(get()) }
    factory<LoginViewModel> { LoginViewModel(get(), get()) }
    factory<ForgotPasswordViewModel> { ForgotPasswordViewModel(get(), get()) }
    factory<RegisterViewModel> { RegisterViewModel(get(), get()) }
}
