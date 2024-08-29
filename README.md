# Rick And Morty App (tecnical test)
Aplicación Android compatible con Android 7.0 o superior, hecha con Kotlin y Jetpack Compose sobre [Rick And Morty](https://www.imdb.com/title/tt2861424/), 
donde se hace el uso de la [Rick And Morty API](https://rickandmortyapi.com/)

![App Mockup](https://github.com/user-attachments/assets/0b4ee2ea-a4c0-4bd9-ad28-2db31197a5b3)

## Tecnologías utilizadas
- Jetpack Compose
- Kotlin
- Paging 3 (para paginación e implementación de un "scroll infinito")
- Retrofit (para peticiones remotas)
- Realm (para cache local)
- Dagger-Hilt (para injección de dependencias)

## Descarga y prueba del proyecto
**Rick And Morty App** puede ser usada en [Android Studio](https://developer.android.com/studio) en dos modos, _debug_ y _release_, para ambas variantes se necesita una previa configuración para correr
la app en un dispositivo o emulador.
### Configuración general
Para iniciar a usar la aplicación debes descargar la rama **main** del proyecto actual. Al momento de descargar el proyecto, en tu archivo generado _local.properties_ debes agregar la siguiente linea de código:
```
BACKEND_URL = "https://rickandmortyapi.com/api/"
```
Dicha cadena de conexión automaticamente al correr el proyecto, podrás hacer uso de **Rick And Morty App**

### Selección de build-variant
Puedes seleccionar una build-variant directamente en la UI de Android Studio ![image](https://github.com/user-attachments/assets/04436b93-72c6-4829-8b21-c33aa6c2fb9e)
### Debug
No necesitas mayor configuración ni requisitos mayores a los mencionados anteriormente, solamente correr el proyecto en un dispositivo Android.

### Release
Para esta variante, se requiere de una signing key para su compilación minificada y reobfuscada. En el caso de este proyecto, tiene por defecto la misma que en modo debug, sin embargo, no se recomienda dejar dicha configuración de esa manera.
Para más información sobre como generar una signing key y usarla para firmar y subir nuestra app a Play Store, puedes consultar este [recurso](https://developer.android.com/studio/publish/app-signing?hl=es-419#generate-key) de Android Developers.




