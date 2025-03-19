package grpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder


class GrpcClient(private val channel: ManagedChannel) {
    private val stub: UserServiceGrpc.UserServiceBlockingStub =
        UserServiceGrpc.newBlockingStub(channel)

    fun getUser(userId: String) {
        val request = UserServiceOuterClass.GetUserRequest.newBuilder().setUserId(userId).build()
        val response = stub.getUser(request)

        println("Response from server:")
        println("User ID: ${response.userId}")
        println("Name: ${response.name}")
        println("Email: ${response.email}")
    }
}

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val client = GrpcClient(channel)
    client.getUser("123")

    channel.shutdown()

}