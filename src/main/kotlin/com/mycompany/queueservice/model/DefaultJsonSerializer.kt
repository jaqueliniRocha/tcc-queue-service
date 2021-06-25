package com.mycompany.queueservice.model

import com.google.gson.Gson
import org.springframework.stereotype.Component

@Component
class DefaultJsonSerializer(private val gson: Gson) : Serializer {

    override fun serialize(var1: Any): String {
        return gson.toJson(var1)
    }

    override fun <T> deserialize(serializedObject: String, serializedClass: Class<T>): T {
        return gson.fromJson(
            serializedObject,
            serializedClass
        )
    }
}