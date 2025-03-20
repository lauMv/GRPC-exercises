package grpc.grpc

import UserServiceOuterClass.UserActionRequest
import UserServiceOuterClass.UserUpdateResponse
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import java.util.concurrent.CountDownLatch

fun main() {
    val channel = ManagedChannelBuilder.forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val stub = UserServiceGrpc.newStub(channel)
    val latch = CountDownLatch(1)
    val responseObserver = object : StreamObserver<UserUpdateResponse> {
        override fun onNext(value: UserUpdateResponse) {
            println("Server Response: ${value.message}")
        }

        override fun onError(t: Throwable) {
            println("Error: ${t.message}")
            latch.countDown()
        }

        override fun onCompleted() {
            println("Stream completed")
            latch.countDown()
        }
    }

    val requestObserver = stub.streamUserActions(responseObserver)

    val actions = listOf("LOGIN", "LOGOUT", "UPDATE_PROFILE")

    actions.forEach { action ->
        requestObserver.onNext(UserActionRequest.newBuilder().setUserId("123").setAction(action).build())
        Thread.sleep(500)
    }

    requestObserver.onCompleted()

    latch.await()
    channel.shutdown()
}