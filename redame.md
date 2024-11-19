Pasos para crear un microservicio:

- Crear un proyecto spring boot con maven. 
  - JDK 17
- Borrar el .idea y arrastrarlo fisicamente al proyecto eliaBackend
- Abrir eliaBackend y compilar maven con 
  - mvn install

Estructura de un microservicio con DDD+Hexagonal:
```
src/
├── Application/       # Capa de aplicación (casos de uso)
│   ├── UseCases/
│   ├── DTOs/
│   └── Services/
├── Domain/            # Núcleo del dominio
│   ├── Entities/      # Entidades del dominio
│   ├── ValueObjects/  # Objetos de valor
│   ├── Repositories/  # Interfaces de repositorios
│   ├── Services/      # Servicios de dominio
│   └── Exceptions/    # Excepciones del dominio
├── Infrastructure/    # Implementaciones externas
│   ├── Persistence/   # Base de datos
│   ├── Messaging/     # Comunicación entre microservicios
│   └── Adapters/      # Adaptadores para puertos
└── API/               # Interfaces externas
├── Controllers/   # Endpoints REST
├── Middlewares/
└── Models/
```