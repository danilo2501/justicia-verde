# Justicia Verde

Aplicación Android desarrollada para facilitar la creación de denuncias ambientales de forma simple, visual y accesible. El proyecto busca servir como una herramienta digital para que las personas puedan reportar situaciones que afecten el medio ambiente, adjuntar evidencia fotográfica y consultar reportes ya registrados dentro de la plataforma.

## Propósito Del Proyecto

`Justicia Verde` nace con un enfoque social y ambiental. La idea principal es ofrecer una aplicación móvil donde cualquier persona pueda:

- identificarse con su nombre o continuar de forma anónima,
- registrar una denuncia ambiental,
- adjuntar entre 1 y 5 imágenes como evidencia,
- consultar reportes almacenados,
- revisar el detalle completo de cada caso.

El objetivo es convertir la denuncia ciudadana en un proceso más rápido, organizado y útil para futuras acciones de seguimiento, monitoreo o visibilización de problemáticas ambientales.

## Tecnologías Utilizadas

### Android y UI

- `Kotlin`: lenguaje principal del proyecto.
- `Jetpack Compose`: construcción completa de la interfaz declarativa.
- `Material 3`: base visual para componentes, tipografía y esquema de color.
- `Navigation Compose`: manejo de navegación entre pantallas.

### Persistencia Local

- `DataStore Preferences`: almacenamiento local del nombre del usuario y del modo anónimo.

### Backend En Firebase

- `Firebase Firestore`: base de datos principal para guardar usuarios y reportes.
- `Firebase Storage`: almacenamiento de imágenes de evidencia.
- `Firebase Google Services`: integración del proyecto Android con Firebase.

### Carga De Imágenes

- `Coil`: visualización de imágenes locales y remotas dentro de Compose.

### Concurrencia

- `Kotlin Coroutines`
- `kotlinx-coroutines-play-services`: integración entre corrutinas y tareas de Firebase.

## Cómo Está Implementado

La aplicación trabaja con una arquitectura simple orientada a pantallas Compose y navegación central desde `MainActivity`.

### Flujo Principal

1. El usuario entra a la pantalla de inicio.
2. Puede ingresar su nombre o usar una cuenta anónima.
3. Si ingresa con nombre, este se guarda en Firestore dentro de la colección `usuarios`.
4. Desde la pantalla principal puede:
   - crear una nueva denuncia,
   - ver los reportes ya existentes.
5. Al crear una denuncia:
   - selecciona el tipo de caso,
   - escribe la descripción,
   - indica la ubicación,
   - selecciona de 1 a 5 imágenes opcionales desde galería.
6. Las imágenes se suben a Firebase Storage.
7. Las URLs públicas generadas se guardan en Firestore junto con la denuncia.
8. En la vista de reportes, cada denuncia puede abrirse en detalle y mostrar sus imágenes en cuadrícula.
9. Cada imagen puede abrirse en tamaño completo.

## Modelo De Datos

### Colección `usuarios`

Cada documento guarda información básica de la persona que ingresó con nombre.

Ejemplo:

```json
{
  "nombre": "Martha",
  "fecha": 1770000000000
}
```

### Colección `reportes`

Cada documento representa una denuncia ambiental.

Ejemplo:

```json
{
  "id": "abc123",
  "usuario": "Martha",
  "tipo": "Contaminación del aire",
  "descripcion": "Se observa humo negro constante en la zona.",
  "ubicacion": "Popayán, barrio centro",
  "imagenesUrls": [
    "https://.../imagen_0.jpg",
    "https://.../imagen_1.jpg"
  ],
  "fecha": 1770000000000
}
```

### Storage

Las imágenes se guardan con una estructura organizada por reporte:

```text
reportes/{reporteId}/imagen_0.jpg
reportes/{reporteId}/imagen_1.jpg
```

## Pantallas Principales

### `PantallaInicio`

- ingreso con nombre obligatorio,
- opción de cuenta anónima,
- persistencia local del usuario.

### `PantallaPrincipal`

- acceso rápido para crear denuncia,
- acceso a la lista de reportes.

### `FormularioReporteMaltrato`

- formulario principal de denuncias,
- selección de tipo de caso,
- captura de descripción y ubicación,
- selección de imágenes desde galería,
- subida a Storage y guardado en Firestore.

### `ReportesScreen`

- lista de denuncias registradas,
- lectura desde Firestore,
- navegación al detalle del reporte.

### `ReporteCompleto`

- detalle completo del caso,
- visualización de evidencia fotográfica,
- apertura de imágenes en pantalla completa.

## Seguridad Y Reglas

El proyecto incluye archivos para reglas de Firebase:

- `firestore.rules`
- `storage.rules`

Actualmente están pensadas para permitir lectura y creación de usuarios, reportes e imágenes desde la aplicación. En una siguiente etapa, estas reglas deben endurecerse cuando exista autenticación real o distintos niveles de acceso.

## Enfoque Del Proyecto

El enfoque actual de `Justicia Verde` es construir una base funcional, clara y utilizable para denuncias ambientales ciudadanas. No se busca solo una app bonita, sino una herramienta que pueda servir como punto de partida para un sistema más serio de recepción y seguimiento de casos.

Sus pilares actuales son:

- facilidad de uso,
- registro rápido de denuncias,
- evidencia visual,
- persistencia en la nube,
- consulta simple de reportes.

## Visión Del Proyecto

La visión a futuro de `Justicia Verde` puede crecer hacia una plataforma más completa de gestión ambiental ciudadana. Algunas líneas naturales de evolución son:

- autenticación de usuarios,
- panel administrativo para revisar denuncias,
- estados del reporte: pendiente, en revisión, resuelto,
- geolocalización con mapa,
- notificaciones de seguimiento,
- filtros por tipo de denuncia o zona,
- evidencia en video además de imágenes,
- integración con entidades u organizaciones ambientales.

En otras palabras, el proyecto apunta a convertirse en una herramienta tecnológica de apoyo para la participación ciudadana y la protección del entorno.

## Ejecución Del Proyecto

### Requisitos

- Android Studio
- SDK Android configurado
- proyecto Firebase enlazado mediante `google-services.json`

### Ejecutar

1. Abrir el proyecto en Android Studio.
2. Sincronizar dependencias Gradle.
3. Verificar que Firestore y Storage estén habilitados en Firebase.
4. Publicar las reglas de `firestore.rules` y `storage.rules`.
5. Ejecutar la app en emulador o dispositivo.

## Estado Actual

Actualmente el proyecto ya permite:

- ingresar con nombre o de forma anónima,
- guardar usuarios en Firestore,
- crear denuncias ambientales,
- subir imágenes a Firebase Storage,
- guardar denuncias en Firestore,
- listar reportes,
- ver el detalle de cada denuncia con sus imágenes.

## Nombre Del Proyecto

`Justicia Verde` representa la combinación entre participación ciudadana, defensa del entorno y uso de tecnología para visibilizar problemas ambientales de forma práctica y moderna.
