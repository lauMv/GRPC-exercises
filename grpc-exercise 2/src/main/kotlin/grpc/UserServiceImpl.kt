package grpc

import UserServiceOuterClass.ListUsersRequest
import UserServiceOuterClass.UserActionRequest
import UserServiceOuterClass.UserResponse
import UserServiceOuterClass.UserUpdateResponse
import io.grpc.stub.StreamObserver

class UserServiceImpl : UserServiceGrpc.UserServiceImplBase() {
    override fun listUsers(request: ListUsersRequest, responseObserver: StreamObserver<UserResponse>) {
        val users = listOf(
            UserResponse.newBuilder().setUserId("1").setName("Alice").setEmail("alice@example.com").build(),
            UserResponse.newBuilder().setUserId("2").setName("Bob").setEmail("bob@example.com").build(),
            UserResponse.newBuilder().setUserId("3").setName("Charlie").setEmail("charlie@example.com").build()
        )

        users.filter { it.name.contains(request.filter, ignoreCase = true) }
            .forEach {
                responseObserver.onNext(it)
                Thread.sleep(500)
            }

        responseObserver.onCompleted()
    }

    override fun streamUserActions(responseObserver: StreamObserver<UserUpdateResponse>): StreamObserver<UserActionRequest> {
        return object : StreamObserver<UserActionRequest> {
            override fun onNext(request: UserActionRequest) {
                println("Received action: ${request.action} from user: ${request.userId}")

                val response = UserUpdateResponse.newBuilder()
                    .setMessage("Action '${request.action}' received for user ${request.userId}")
                    .build()

                responseObserver.onNext(response)
            }

            override fun onError(t: Throwable) {
                println("Error in streamUserActions: ${t.message}")
            }

            override fun onCompleted() {
                responseObserver.onCompleted()
            }
        }
    }

}
