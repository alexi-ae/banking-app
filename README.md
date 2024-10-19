# BANKING APP

Sistema de Transferencias de Dinero en Tiempo Real

## Tabla de Contenidos

1. [Caso de Negocio](#casodenegocio)
2. [Características](#características)
3. [Modelos](#modelos)
4. [Proceso](#proceso)
5. [Tecnologías Utilizadas](#tecnologíasutilizadas)
6. [Autor](#autor)

## Caso de Negocio

Un banco desea implementar un sistema de transferencias de dinero en tiempo real que permita a los
clientes enviar y recibir fondos de manera instantánea a nivel nacional. Este sistema busca mejorar
la experiencia del cliente y competir con servicios de pago alternativos que han ganado popularidad.

## Características

Se realizaron múltiples módulos para poder tener la base del flujo de un proceso bancario real.

- **Registro**: Los nuevos usuarios (potenciales clientes) pueden registrarse proporcionando solo su
  dirección de correo electrónico. La contraseña se almacena de forma segura utilizando un algoritmo
  de hash (por ejemplo, bcrypt).
- **Inicio de Sesión Seguro**: Esta funcionalidad tiene 2 procesos en las cuales se aplican medidas
  de seguridad. En ambos casos se puede iniciar sesión con su dirección de correo y contraseña.
    1. Los usuarios con cuenta **PENDING** (pendiente de aprobación), se le genera un token para que
       solo puedan acceder al flujo de Onboarding.
    2. Los usuarios con cuenta **APPROVED** (aprobados), se le genera un token con acceso completo
       al sistema.
- **Onboarding**: Los usuarios, tendrán que completar su información, como datos de contacto,
  personal, identificación, cargar evidencia de documento, información legal para con ello poder
  realizar la validación y darle los accesos como cliente al sistema.
- **Autorización de Transacciones**: Con el usuario aprobado, se podrá realizar diversas
  transacciones en el sistema, como transferencias, consulta de saldo, historial de movimientos.

## Modelos

1. Users (Usuario de acceso al sistema, potencial cliente)
2. Customer (Cliente de la empresa)
3. Contact (datos de contacto base)
4. Document (dato personal según documento de identificación)
5. ExtraInfo (información legal)
6. Files (archivo de sustento de identidad)
7. Account (cuenta bancaria lógica)
8. Transaction (transacciones u operaciones bancarias)
9. TransactionMovement (movimientos de cuentas)

## Proceso

Sigue estos pasos probar los servicios:

1. Registrarse: Se ingresa solo su correo electrónico y una contraseña.
   ```bash
   curl --location 'localhost:8080/ob/register' \
   --header 'Content-Type: application/json' \
   --data-raw '{
       "email":"test@example.com",
       "password":"PassTest1"
   }'
   ```

2. Login (Proceso onboarding): El usuario para continuar el proceso de registro debe hacer un login,
   con ello obtendrá un token para acceder a todo el proceso de Onboarding.
    ```bash
   curl --location 'localhost:8080/auth/login' \
   --header 'Content-Type: application/json' \
   --data-raw '{
   "username": "test@example.com",
   "password": "PassTest1"
   }'
    ```
3. Onboarding - contact: El usuario completará la información básica de contacto, y se enviará un sms o
   whatsaap con un código de verificación.
    ```bash
   curl --location 'localhost:8080/ob/contact' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' \
   --header 'Content-Type: application/json' \
   --data '{
   "countryCode":"PE",
   "callingCode":"+51",
   "phoneNumber":"987654321"
   }'
    ```
4. Onboarding - contact-validate: El usuario ingresara el código de verificación.
    ```bash
   curl --location 'localhost:8080/ob/contact-validate' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' \
   --header 'Content-Type: application/json' \
   --data '{
   "code":"123456"
   }'
    ```
5. Onboarding - personal-info: El usuario completara información personal .
    ```bash
   curl --location 'localhost:8080/ob/personal-info' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' \
   --header 'Content-Type: application/json' \
   --data '{
   "firstName":"test1",
   "lastName":"te1",
   "birthdate":"20-10-1990"
   }'
    ```
6. Onboarding - identity-info: El usuario completara datos de identificación (datos de documento de
   identidad).
    ```bash
   curl --location 'localhost:8080/ob/identity-info' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' \
   --header 'Content-Type: application/json' \
   --data '{
   "documentCountry":"PE",
   "documentType":"DNI",
   "documentNumber":"78945612"
   }'
    ```

7. Onboarding - upload-document: El usuario enviará un archivo(imagen) de su documento de identidad.
    ```bash
   curl --location 'localhost:8080/ob/upload-document' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' \
   --form 'file=@"/C:/Users/test/file/document_identity.jpeg"'
   ```

8. Onboarding - extra-info: El usuario indicara si es una persona políticamente expuesta.
    ```bash
   curl --location 'localhost:8080/ob/extra-info' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' \
   --header 'Content-Type: application/json' \
   --data '{
   "politicallyExposed": false
   }'
   ```

9. Onboarding - processing-info: El usuario no ingresará datos, pero gatillara el proceso de
   validación de toda la información anteriormente enviada.
    ```bash
   curl --location --request POST 'localhost:8080/ob/processing-info' \
   --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA'

   ```

10. Info: Luego de volver a iniciar sesión, el usuario podrá realizar la consulta de su información
    básica, además de acceder al sistema como cliente.
     ```bash
    curl --location --request POST 'localhost:8080/customers/info' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA' 
    ```

11. Accounts: El usuario podrá visualizar las cuentas bancarias que tiene asociada.
     ```bash
    curl --location --request POST 'localhost:8080/accounts' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA'   
    ```

12. Transaction - send: El cliente podrá realizar operaciones, como enviar dinero a un número de
    cuenta
    ```bash
    curl --location 'localhost:8080/transactions/send' \
      --header 'Authorization: {{token}}' \
      --header 'Content-Type: application/json' \
      --data '{
      "originAccount":"191682792029360",
      "destinationAccount":"191527696043267",
      "amount":255.00,
      "description":"test transferencia"
      }'

    ```

13. Transaction - movements: El cliente podrá consultar sus movimientos por cada cuenta que tenga.
    ```bash
    curl --location 'localhost:8080/transactions/account/2' \
      --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwiY3VzdG9tZXJJZCI6MSwiaWQiOjEsInN0YXRlIjoiUEVORElORyIsImVtYWlsIjoidGVzdEBleGFtcGxlLmNvbSIsInN1YiI6InRlc3RAZXhhbXBsZS5jb20iLCJpYXQiOjE3MjkzNjMwODUsImV4cCI6MTcyOTM2NDA4NX0.QXD49EG6gWLswrD1oEBs0ZlyuxhnUVTUYn9VZuhJBeA'   
    ```

## Tecnologías Utilizadas

- **Lenguaje de programación**: Java
- **Frameworks**:Spring Boot
- **Base de datos**: H2
- **ORM**: Hibernate y JPA
- **Control de versiones**: Git

## Autor

- **Nombre**: Alexi Acuña
- **Rol**: Desarrollador Principal
- **Descripción**: Desarrollador de software con experiencia en aplicaciones Java y Spring Boot.
  Apasionado por la creación de soluciones eficientes y escalables.
- **GitHub**: [github.com/alexi-ae](https://github.com/alexi-ae)
- **LinkedIn**: [linkedin.com/in/ronald-alexi-ae](https://www.linkedin.com/in/ronald-alexi-ae/)