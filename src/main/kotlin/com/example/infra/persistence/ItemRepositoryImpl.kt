package com.example.infra.persistence

import com.example.domain.repository.ItemRepository

class ItemRepositoryImpl : ItemRepository {
    override fun save(): Boolean {
        return true
    }
}