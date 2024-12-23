package com.example

import com.example.app.SayHelloAgainUseCase
import com.example.domain.repository.ItemRepository
import com.example.infra.persistence.ItemRepositoryImpl

import org.koin.core.context.startKoin
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


fun di() {
    startKoin {
        modules(
            module {
                // use-case
                singleOf(::SayHelloAgainUseCase)

                // repository
                singleOf(::ItemRepositoryImpl) { bind<ItemRepository>() }
            }
        )
    }
}