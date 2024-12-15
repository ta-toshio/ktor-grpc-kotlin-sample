@file:OptIn(DelicateCoroutinesApi::class)

package com.example

import io.ktor.server.application.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// パターン1: デフォルト
//fun main(args: Array<String>) {
//    io.ktor.server.netty.EngineMain.main(args)
//}
//fun Application.module() {
//    configureRouting()
//}


// パターン2: gRPCサーバーを起動する
// ktor.server.nettyは起動していない
// Application.module() も実行されない
//fun main(args: Array<String>) {
//    val port = System.getenv("PORT")?.toInt() ?: 50051
//    val server = HelloWorldServer(port)
//    server.start()
//    server.blockUntilShutdown()
//}
//fun Application.module() {
//    println("hello world")
//    configureRouting()
//}

// パターン3: ktor.server.nettyもgRPCサーバーも起動試みる
// ktor.server.nettyが起動され、gRPCサーバーは起動されない
//fun main(args: Array<String>) {
//    io.ktor.server.netty.EngineMain.main(args)
//
//    val port = System.getenv("PORT")?.toInt() ?: 50051
//    val server = HelloWorldServer(port)
//    server.start()
//    server.blockUntilShutdown()
//}
//fun Application.module() {
//    println("hello world")
//    configureRouting()
//}

// パターン4: ktor.server.nettyもgRPCサーバーも起動試みる
// runBlockingを使う
// ktor.server.nettyは起動したが、gRPCサーバーは起動されなかった
//fun main(args: Array<String>) {
//    runBlocking {
//        launch {
//            io.ktor.server.netty.EngineMain.main(args)
//        }
//
//        launch {
//            val port = System.getenv("PORT")?.toInt() ?: 50051
//            val server = HelloWorldServer(port)
//            server.start()
//            server.blockUntilShutdown()
//        }
//    }
//}
//
//fun Application.module() {
//    println("hello world")
//    configureRouting()
//}


// パターン5: ktor.server.nettyもgRPCサーバーも起動試みる
// GlobalScope.launchを使う
// @file:OptIn(DelicateCoroutinesApi::class)を使うよう推奨してくる
fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    println("hello world")
    configureRouting()

    val port = System.getenv("PORT")?.toInt() ?: 50051
    val server = HelloWorldServer(port)

    GlobalScope.launch {
        server.start()
        server.blockUntilShutdown()
    }
}

//// パターン6: gRPCサーバーを起動
//fun main(args: Array<String>) {
//    val port = System.getenv("PORT")?.toInt() ?: 50051
//    val server = HelloWorldServer(port)
//    server.start()
//    server.blockUntilShutdown()
//}

//// パターン7: ktor.server.nettyもgRPCサーバーも起動試みる
//// runBlockingを使う
//// embeddedServerを使う。moduleも呼び出す
//fun main(args: Array<String>) {
//    runBlocking {
//        launch {
//            val port = System.getenv("PORT")?.toInt() ?: 50051
//            val server = HelloWorldServer(port)
//            server.start()
//            server.blockUntilShutdown()
//        }
//
//        launch {
//            embeddedServer(
//                Netty,
//                port = 50051,
//            ) {
//                module()
//            }.start(wait = true)
////            io.ktor.server.netty.EngineMain.main(args)
//        }
//    }
//}
//fun Application.module() {
//    println("hello world")
//    configureRouting()
//}

//// パターン8: ktor.server.nettyもgRPCサーバーも起動試みる
//// runBlockingを使う
//// ktor.server.nettyが起動しない
//fun main(args: Array<String>) {
//    runBlocking {
//        launch {
//            io.ktor.server.netty.EngineMain.main(args)
//        }
//    }
//}
//
//fun Application.module() {
//    println("hello world")
//    configureRouting()
//
//    launch {
//        val port = System.getenv("PORT")?.toInt() ?: 50051
//        val server = HelloWorldServer(port)
//        server.start()
//        server.blockUntilShutdown()
//    }
//}
