# Atiende · Backend de Atención al Cliente (Fase 1)

## Qué es el proyecto
Backend de atención al cliente para **Grano Alto Café** (tienda online de café de especialidad). Centraliza los mensajes recibidos desde diversos canales (WhatsApp, correo y chat web) como tickets con estado y prioridad, permitiendo gestionar el ciclo de atención y conocer qué mensajes están pendientes de respuesta.

---

## Requisitos
- **Java**: 17
- **Maven**: 3.8+ (o utilizar el wrapper `./mvnw` incluido)
- **Base de datos**: MySQL

---

## Cómo ejecutarlo

1. **Configurar las variables de entorno para la base de datos**:
   En `application.properties`, la conexión se realiza mediante variables de entorno:
   - `DB_URL` (ejemplo: `jdbc:mysql://localhost:3306/atiende_db`)
   - `DB_USERNAME` (ejemplo: `root`)
   - `DB_PASSWORD` (ejemplo: `root`)

   Puedes definirlas en el sistema operativo o en un archivo `.env` en la raíz del proyecto:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/atiende_db
   DB_USERNAME=root
   DB_PASSWORD=root
   ```

2. **Ejecutar la aplicación**:
   - En Windows:
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   - En Linux / macOS:
     ```bash
     ./mvnw spring-boot:run
     ```

   La aplicación iniciará por defecto en el puerto `8080`.

---

## Endpoints

| Método | Ruta | Respuestas |
|---|---|---|
| `POST` | `/api/tickets` | `201` ticket creado · `400` datos inválidos |
| `GET` | `/api/tickets?estado=` | `200` lista, más recientes primero; el filtro es opcional |
| `GET` | `/api/tickets/{id}` | `200` · `404` |
| `PATCH` | `/api/tickets/{id}/estado` | `200` · `400` · `404` · `409` transición no permitida |

---

## Ejemplos cURL

### 1. Crear un ticket (`POST /api/tickets`)
> **Nota de negocio**: Todo ticket nuevo nace en estado `ABIERTO` y con prioridad `MEDIA`. Si se envían `estado` o `prioridad` en la petición, el sistema los ignora.

```bash
curl -X POST http://localhost:8080/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "clienteNombre": "Carlos Pérez",
    "clienteContacto": "carlos@cafe.com",
    "canal": "WHATSAPP",
    "mensaje": "Consulta sobre variedades de café de origen Huila."
  }'
```

### 2. Cambiar estado de un ticket (`PATCH /api/tickets/{id}/estado`)
```bash
curl -X PATCH http://localhost:8080/api/tickets/1/estado \
  -H "Content-Type: application/json" \
  -d '{
    "estado": "EN_PROCESO"
  }'
```

---

## Tabla de Transiciones de Estado

Las transiciones permitidas se rigen por la siguiente matriz (`CERRADO` es un estado final; cualquier otra transición se rechaza con error `409`):

| Desde | Hacia | Caso real |
|---|---|---|
| `ABIERTO` | `EN_PROCESO` | Alguien de soporte toma el ticket |
| `ABIERTO` | `CERRADO` | Spam o mensaje duplicado |
| `EN_PROCESO` | `RESUELTO` | Se respondió al cliente |
| `RESUELTO` | `CERRADO` | El cliente confirmó la solución |
| `RESUELTO` | `EN_PROCESO` | El cliente dice que no quedó resuelto |

---

## Formato de Error

Las respuestas de error siguen una estructura JSON uniforme con: `codigo`, `error`, `mensaje`, `fecha`.

Ejemplo:
```json
{
  "codigo": 400,
  "error": "Bad Request",
  "mensaje": "mensaje: El mensaje es obligatorio",
  "fecha": "2026-10-22T00:15:30.123456"
}
```

---

## Cómo correr los tests

Los tests unitarios están desarrollados con **JUnit 5** y **Mockito** (sin levantar el contexto de Spring).

Para ejecutar todos los tests:
- En Windows:
  ```powershell
  .\mvnw.cmd test
  ```
- En Linux / macOS:
  ```bash
  ./mvnw test
  ```

Para ejecutar una clase de test específica:
```powershell
.\mvnw.cmd test "-Dtest=EstadoTicketTest"
.\mvnw.cmd test "-Dtest=TicketServicioTest"
```
