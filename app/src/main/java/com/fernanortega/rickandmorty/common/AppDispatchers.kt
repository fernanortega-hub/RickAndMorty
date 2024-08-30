package com.fernanortega.rickandmorty.common

import javax.inject.Qualifier
import kotlinx.coroutines.Dispatchers
/**
 * La función de el contenido de este archivo es para tener mejor manejos de los
 * [Dispatchers], facilitando la inyección donde se necesite y posibles unit test que puedan realizarse
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val appDispatcher: AppDispatchers)

enum class AppDispatchers {
    Default,
    IO
}