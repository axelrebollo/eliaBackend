Estructura de un microservicio con DDD+Hexagonal:
```
src/
├── API/                    # Capa de API para interfaces externas
│    ├── Controllers/           # Endpoints REST
│    └── HandlerException/      # Intercepta excepciones
├── Application/            # Capa de aplicación (casos de uso)
│   ├── UseCases/               # Casos de uso
│   ├── DTOs/                   # Responses
│   ├── Services/               # Servicios de aplicación
│   ├── Repositories/           # Interfaces con infrastructura
│   └── Exceptions/             # Excepciones de aplicación
├── Domain/                 # Capa de dominio
│   ├── Entities/               # Entidades del dominio
│   ├── ValueObjects/           # Objetos de valor
│   ├── Repositories/           # No usado pero preparado
│   ├── Services/               # Servicios de dominio
│   └── Exceptions/             # Excepciones del dominio
└── Infrastructure/         # Capa de implementaciones externas (no frontend)
    ├── Adapters/               # Adaptadores entre capas y entidades para DB
    ├── Exceptions/             # Excepciones de infrastructura
    ├── JpaEntities/            # Entidades para la DB
    ├── Kafka/                  # Broker de mensajeria
    │   ├── Producers/              # Productores
    │   └── Consumers/              # Consumidores
    ├── Persistence/            # Persistencia JPA en DB            
    └── Repositories/           # Repositorios de infrastructura

```
La aplicación incluye tres archivos docker-compose con propósitos diferenciados según el entorno y la fase del desarrollo:

- docker-compose.yml: Este archivo está diseñado para el entorno de desarrollo. Permite levantar los servicios necesarios 
para el desarrollo local.

  - `Dos bases de datos PostgreSQL`
  - `Apache Kafka`
  - `Zookeeper`

Con este archivo no se generan imágenes personalizadas, simplemente se levantan contenedores con configuraciones básicas 
para facilitar el trabajo de desarrollo.

- docker-compose-prod.yml: Este archivo se utiliza para la generación de imágenes de los microservicios. En este entorno 
no se levantan Apache Kafka, Zookeeper ni las bases de datos, ya que estos servicios están contemplados en el archivo siguiente.

  - `Microservicio User`
  - `Microservicio Notebook`

Su proposito principal es construir las imágenes Docker personalizadas de los microservicios para su posterior despliegue.

- docker-compose-deploy.yml: Este archivo permite, mediante un solo comando, desplegar por completo la aplicación. Incluye:
  - `Las dos bases de datos PostgreSQL`
  - `Zookeeper`
  - `Apache Kafka`
  - `Los dos microservicios (User, Notebook)`
  - `La imágen del frontend`

La imágen no se genera automáticamente en este compose. Debe ser creada manualmente con su correspondiente DockerFile, ya 
que en un entorno real el frontend suele ser alojado en plataformas que integran directamente con GitHub (Vercel por ejemplo),
sin necesidad de contenedores.

Los comandos resumen para generar la imagen del fontend son:

    `docker build -t eliafrontend .`
    `docker run -p 3000:3000 eliafrontend`