

# Disclaimer

Por temas de diferencias en el equipo, Cedula Medica y Cedula Medica 2 se divieron, se hizo el proyecto desde 0

Además, mi compañera por temas de tiempo y salud, no puedo aparecer

# Cedula Medica Backend

Este proyecto es una aplicación web diseñada para brindar asistencia rápida en caso de accidentes de tráfico. Utilizando tecnología de escaneo de códigos QR, la aplicación permite a los usuarios acceder fácilmente a información vital y comunicarse con los servicios de emergencia cuando más lo necesitan.

- DockerHub: https://hub.docker.com/repository/docker/yorksgomez/cedula/general
- URL de la app: http://35.223.110.190:8080/noauth/hola

## Carecteristicas Importantes

### Seguridad

- Spring Boot Security
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/86ec11dd-0da7-411c-a659-fd1cd203c87a)
- JWT Token
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/506257b8-b168-4936-b01d-98903dcce41f)
- NO-CORS para testing
- Uso de algoritmo de sal para proteger los QR de Web Scrapping

### Implementación

- Dockerizado
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/8e47d0db-5211-468c-a479-d8cdfda7abd0)
- Se publica por el puerto 8080
- Conexión a dos bases de datos MongoDB Atlas
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/36d6f2a4-cbd9-4ff7-a376-f7e3ec95a01a)
- Montado en un servidor en Google Cloud Platform para contenedores
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/533104fa-3586-441a-b820-95a31a79e2e3)

### Testing

- Uso de Spring Boot Test
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/ebb7ecc3-de6a-42c7-abb5-6278fbb9f0d5)
- MvCMock
- Conexión a base de datos de testing por medio de perfiles
-  ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/d8e772ac-9fb7-4103-8a35-ab9135cfc397)

### Rutas

- UserController y rutas de autenticación
- QRController para rutas de gestión del QR
- HistoryController para rutas del historial médico

## Ejecución

Para ejecutarlo debe ejecutarse el comando en Windows

./mvnw clean package

./mvnw test

./mvnw spring-boot:run

## Uso

- Login
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/0925e785-bf21-445c-b79d-0f8b7ddf4211)
- Obtener QR
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/5bdbf706-56e6-4eca-a52f-64eb44086c3d)
- Ver información médica
- ![image](https://github.com/yorksgomez/cedula-back/assets/23731047/306e54ed-e0e8-4a32-8142-d09118f246ee)
- Hay algunas otras rutas como update de información médica, ver mi información médica, etc.


