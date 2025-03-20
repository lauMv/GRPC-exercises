## Exercise 1
Run:
```
./gradlew clean
./gradlew build
./gradlew compileKotlin
./gradlew generateProto
./gradlew run
```
> Starting the project, downloading all dependencies, and setting up my configurations was tricky.
I encountered some compatibility issues, and since gRPC was new to me, I needed extra time to learn how to
configure it properly in order to build my proto file. After that, things became easier.

## Exercise 2
Same as the first plus 
```
Run GrpcClient
Run StreamUserClient
```

## Exercise 3

### Concurrency & Scalability

- *Scalability*: The User service could be deployed in a containerized environment, allowing it to scale dynamically
based on incoming request volume.
- *Load Balancing*: Requests will be distributed across instances using a load balancer, such as Kubernetes Ingress.
- *Threading & Asynchronous Processing*: Request will be proccesed asynchronously to efficiently handle
high-concurrency scenarios with streaming methods.

### Service-to-Service Communication
The UserService is one of our principal microservices. It will be handle all user-related 
operations such as:

- *Authentication Service*: Validates users via gRPC.
- *Order Service*: Processes orders via gRPC.
- *Payment Service*: Processes payments via gRPC.
- *Notification Service*: Sends emails and notifications asynchronously via Kafka.
- *Database (PostgreSQL)*: Stores user data and caches frequently accessed information.

```
               ┌───────────────────────────────┐
               │         UserService           │         ┌──────────────┐
               ├───────────────────────────────┤ ── ── > | OrderService |
               │ - Userser operations          |         └──────────────┘
               │ - OrderService                |       
               │ - AuthService                 │
               │ - PaymentService              │         ┌────────────────┐
               │ - Publishes events to Kafka   │ ── ── > | PaymentService |
               └──────────┬───────────┬────────┘         └────────────────┘
                          │           │
             ┌────────────▼───────┐   ▼─────────────────────┐
             │   AuthService      │   | NotificationService │
             └────────────────────┘   └─────────────────────┘
```
- *GRPC for synchronous calls*: User GRPS to interact with the other microservices like OrderService orm PaymentService.
- *Notifications*: For messages queue use asynchronous events like kafka.
- *Service Comunication*: User a service registry like eureka to dynamically discover services.
  
### Data Consistency
-*DB transactions*: Use atomic operations.
-*Event-Driven consistency*: Use Kafka to publish events and maintain eventual consistency.

### Error Handling & Retries
- *Retries with exponential backoff*: GRPC client retries failed calls after increasing delays.
- *Fallback Mechanisms*: If an external service is unavailable, cached data or default responses are used as temporary fallbacks

### Deployment
- *Containerization*: Docker containers ensures consistency across environments.
- *Kubernetes Orchestration*: Manages deployments scaling and service discovery.
- *CI/CD*: Automated builds, test and deployements.





