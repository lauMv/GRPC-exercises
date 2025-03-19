package grpc

import io.grpc.stub.StreamObserver

class UserServiceImpl : UserServiceGrpc.UserServiceImplBase() {
    override fun getUser(
        request: UserServiceOuterClass.GetUserRequest,
        responseObserver: StreamObserver<UserServiceOuterClass.UserResponse>
    ) {
        println("Received request for user ID: ${request.userId}")

        val response = UserServiceOuterClass.UserResponse.newBuilder()
            .setUserId(request.userId)
            .setName("Alice")
            .setEmail("alice@example.com")
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}
