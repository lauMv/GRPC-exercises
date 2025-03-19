package grpc

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("gRPC Server is starting...")

    val server = GrpcServer(50051)
    server.start()
    server.blockUntilShutdown()
}

