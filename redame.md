Pasos para crear un microservicio:

- Crear un proyecto spring boot con maven. 
  - JDK 17
- Borrar el .idea y arrastrarlo fisicamente al proyecto eliaBackend
- Abrir eliaBackend y compilar maven con 
  - mvn install

Estructura de un microservicio con DDD+Hexagonal:
```
src/
├── API/                    # Interfaces externas
│    ├── Controllers/       # Endpoints REST
│    └── HandlerException/  # Intercepta excepciones
├── Application/            # Capa de aplicación (casos de uso)
│   ├── UseCases/           # Casos de uso
│   ├── DTOs/               # Responses
│   ├── Services/           # Servicios de aplicación
│   ├── Repositories/       # Interfaces con infrastructura
│   └── Exceptions/         # Excepciones de aplicación
├── Domain/                 # Núcleo del dominio
│   ├── Entities/           # Entidades del dominio
│   ├── ValueObjects/       # Objetos de valor
│   ├── Repositories/       # 
│   ├── Services/           # Servicios de dominio
│   └── Exceptions/         # Excepciones del dominio
└── Infrastructure/         # Implementaciones externas
    ├── Adapters/           # Adaptadores entre capas y entidades para DB
    ├── Exceptions/         # Excepciones de infrastructura
    ├── JpaEntities/        # Entidades para la DB
    ├── Kafka/              # Broker de mensajeria
    │   ├── Producers/      # Productores
    │   └── Consumers/      # Consumidores
    ├── Persistence/        # Persistencia JPA en DB            
    └── Repositories/       # Repositorios de infrastructura

```
El proyecto se crea en un solo repositorio para facilitar el desarrollo. 
Cada microservicio es independiente en su carpeta y únicamente están unidos en el proyecto general.

La configuración actual es para localhost en desarrollo. Para los cambios a producción se debe:

- Cambiar las configuraciones de docker compose.
- Cambiar las configuraciones de aplication.properties.
- Cambiar las configuraciones de kafka en sus archivos de configuración general.