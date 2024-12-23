package com.example.app

import com.example.domain.repository.ItemRepository

class SayHelloAgainUseCase(private val itemRepository: ItemRepository) {

    fun execute(name: String): String {
        val result = itemRepository.save()
        return "Hello again $name, result=$result"
    }
}