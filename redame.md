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
Deploy del proyecto