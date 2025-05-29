package com.dr.expensesapp.data.repository

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// special alias to the host loopback interface (aka 127.0.0.1)
private val PORT : String = "8080"
private val BACKEND_URL: String = "http://10.0.2.2:$PORT/"

object NetworkModule {
    val httpClient: HttpClient by lazy {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            defaultRequest {
                headers.append("Content-Type", "application/json")
            }
        }
    }

    val ktorfit: Ktorfit by lazy {
        Ktorfit.Builder()
            .baseUrl(BACKEND_URL)
            .httpClient(httpClient)
            .build()
    }
}