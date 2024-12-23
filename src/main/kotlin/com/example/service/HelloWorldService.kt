package com.example.service

import com.example.GreeterGrpcKt
import com.example.HelloRequest
import com.example.app.SayHelloAgainUseCase
import com.example.helloReply
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase(), KoinComponent {

    private val sayHelloAgainUseCase: SayHelloAgainUseCase by inject()

    override suspend fun sayHello(request: HelloRequest) =
        helloReply {
            message = "Hello ${request.name}"
        }

    override suspend fun sayHelloAgain(request: HelloRequest) = helloReply {
        message = sayHelloAgainUseCase.execute(request.name)
    }
}