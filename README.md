# Rick And Morty App (tecnical test)
Aplicación Android compatible con Android 7.0 o superior, hecha con Kotlin y Jetpack Compose sobre [Rick And Morty](https://www.imdb.com/title/tt2861424/), 
donde se hace el uso de la [Rick And Morty API](https://rickandmortyapi.com/)

# Funcionalidades
En **Rick And Morty App** puedes ver a tus personajes favoritos de la serie, ver sus orígenes y últimas locaciones, su estado, especie y tipo de personaje. Puedes compartirtlos con tus amigos e incluso buscarlos por su nombre

## Screenshots
![App Mockup](https://github.com/user-attachments/assets/0b4ee2ea-a4c0-4bd9-ad28-2db31197a5b3) 

# Descarga y prueba del proyecto
**Rick And Morty App** puede ser usada en [Android Studio](https://developer.android.com/studio) en dos modos, _debug_ y _release_, para ambas variantes se necesita una previa configuración para correr
la app en un dispositivo o emulador.
### Configuración general
Para iniciar a usar la aplicación debes descargar la rama **main** del proyecto actual. Al momento de descargar el proyecto, en tu archivo generado _local.properties_ debes agregar la siguiente linea de código:
```
BACKEND_URL = "https://rickandmortyapi.com/api/"
```
Dicha cadena de conexión automaticamente al correr el proyecto, podrás hacer uso de **Rick And Morty App**

## Selección de build-variant
Puedes seleccionar una build-variant directamente en la UI de Android Studio ![image](https://github.com/user-attachments/assets/04436b93-72c6-4829-8b21-c33aa6c2fb9e)
### Debug
No necesitas mayor configuración ni requisitos mayores a los mencionados anteriormente, solamente correr el proyecto en un dispositivo Android.

### Release
Para esta variante, se requiere de una signing key para su compilación minificada y reobfuscada. En el caso de este proyecto, tiene por defecto la misma que en modo debug, sin embargo, no se recomienda dejar dicha configuración de esa manera.
Para más información sobre como generar una signing key y usarla para firmar y subir nuestra app a Play Store, puedes consultar este [recurso](https://developer.android.com/studio/publish/app-signing?hl=es-419#generate-key) de Android Developers.

# UI
Para la UI de la aplicación fue diseñada en base a los lineamientos de [Material 3](https://m3.material.io/) de Google.
Soporta 2 diferentes temas:
- [Dynamic Color](https://m3.material.io/blog/announcing-material-you) (a partir de Android 12)
- [RickAndMortyTheme](https://github.com/fernanortega-hub/RickAndMorty/blob/main/app/src/main/java/com/fernanortega/rickandmorty/ui/theme/Color.kt) como tema base para versiones anteriores de Android. 
Ambos temas funcionan en dark y light mode, toda la UI fue hecha con Jetpack Compose.

# Soluciones
- Para el desarrollo de esta app se utiliza programación reactiva y asíncrona con ayuda de [Flow](https://kotlinlang.org/docs/flow.html) y [Coroutines](https://kotlinlang.org/docs/coroutines-basics.html) de Kotlin.
- Dicha implementación ayuda a mantener una arquitectura limpia y unidireccional consumiendo datos remotos con ayuda de [Retrofit](https://github.com/square/retrofit) y almacenarlos y usar como datos sin conexión por medio de [Realm](https://github.com/realm/realm-kotlin). 
- La API de Rick And Morty implementa paginación para prácticamente todos sus endpoints, para ello, se decide implementar [Paging](https://developer.android.com/jetpack/androidx/releases/paging), al mismo tiempo, facilitamos el desarrollo de una implementación personalizada de dicha paginación y damos como resultado un _infinity scroll_ para una mejor experiencia de usuario en el Feed de la app.
