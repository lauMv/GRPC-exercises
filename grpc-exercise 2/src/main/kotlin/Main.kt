package grpc

fun main() {
    println("gRPC Server is starting...")

    val server = GrpcServer(50051)
    server.start()
    server.blockUntilShutdown()
}

