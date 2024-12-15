package com.example

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

class HelloWorldServer(private val port: Int) {
    private val server: Server =
        ServerBuilder
            .forPort(port)
            .addService(HelloWorldService())
            .addService(ProtoReflectionService.newInstance())
            .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@HelloWorldServer.stop()
                println("*** server shut down")
            },
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    internal class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase() {
        override suspend fun sayHello(request: HelloRequest) =
            helloReply {
                message = "Hello ${request.name}"
            }

        override suspend fun sayHelloAgain(request: HelloRequest) = helloReply {
            message = "Hello again ${request.name}"
        }
    }
}
