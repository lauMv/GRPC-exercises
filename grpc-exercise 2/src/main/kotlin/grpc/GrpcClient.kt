package grpc
import UserServiceOuterClass.ListUsersRequest
import UserServiceOuterClass.UserResponse
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver


class GrpcClient(private val channel: ManagedChannel) {
    private val stub: UserServiceGrpc.UserServiceStub = UserServiceGrpc.newStub(channel)


    fun listUsers(filter: String) {
        val request = ListUsersRequest.newBuilder().setFilter(filter).build()

        val responseObserver = object : StreamObserver<UserResponse> {
            override fun onNext(response: UserResponse) {
                println("Received user: ${response.userId} - ${response.name} - ${response.email}")
            }

            override fun onError(t: Throwable) {
                println("Error: ${t.message}")
            }

            override fun onCompleted() {
                println("All users received.")
            }
        }
        stub.listUsers(request, responseObserver)
        Thread.sleep(2000)
    }
}

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = UserServiceGrpc.newBlockingStub(channel)
    val request = ListUsersRequest.newBuilder().setFilter("").build()

    val responseIterator = stub.listUsers(request)
    while (responseIterator.hasNext()) {
        val user = responseIterator.next()
        println("User: ${user.name}, Email: ${user.email}")
    }

    channel.shutdown()

}