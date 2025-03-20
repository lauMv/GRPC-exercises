package grpc

import io.grpc.Server
import io.grpc.ServerBuilder

class GrpcServer(private val port: Int) {
    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(UserServiceImpl())
        .build()

    fun start() {
        server.start()
        println("gRPC Server started on port $port")
        Runtime.getRuntime().addShutdownHook(Thread {
            println("Server down")
            this@GrpcServer.stop()
        })
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

}

fun main() {
    val server = GrpcServer(50051)
    server.start()
    server.blockUntilShutdown()
}