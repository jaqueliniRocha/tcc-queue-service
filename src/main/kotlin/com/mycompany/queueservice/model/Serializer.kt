package com.mycompany.queueservice.model

interface Serializer {
    fun serialize(var1: Any): String
    fun <T> deserialize(var1: String, var2: Class<T>): T
}