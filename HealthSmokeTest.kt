package com.alogram.payrisk.v1 // <-- match your generated package

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class HealthSmokeTest : StringSpec({

  "health check returns 200" {
    val server = MockWebServer()
    server.enqueue(MockResponse().setResponseCode(200).setBody("""{"status":"ok"}"""))
    server.start()
    try {
      // Adjust these 3 lines to your actual generated types/names:
      val cfg = Configuration()
      cfg.basePath = server.url("/").toString().removeSuffix("/")
      val api = PayriskAPIService(ApiClient(cfg))  // e.g., DefaultApi(ApiClient(cfg))

      // Also adjust the call name if different:
      val resp = api.healthCheckWithHttpInfo()
      resp.statusCode shouldBe 200
    } finally {
      server.shutdown()
    }
  }
})
