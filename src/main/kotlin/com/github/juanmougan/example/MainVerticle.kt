package com.github.juanmougan.example

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

/**
 * Created by jmougan on 8/10/17.
 */

class MainVerticle : AbstractVerticle() {
    // Mock data
    private val MOCK_ISLANDS by lazy {
        listOf(
                Player(1L, "Lucas Alario", Team(1L, "River Plate")),
                Player(2L, "Leonardo Ponzio", Team(1L, "River Plate")),
                Player(3L, "Lionel Messi", Team(2L, "Barcelona")),
                Player(4L, "Luis Suárez", Team(2L, "Barcelona")),
                Player(5L, "Cristiano Ronaldo", Team(3L, "Real Madrid C.F.")),
                Player(6L, "Neymar Jr.", Team(4L, "Paris St. Germain"))
        )
    }

    override fun start(startFuture: Future<Void>?) {
        val server = vertx.createHttpServer()
        val router = Router.router(vertx)
        val getRouter = router.route().method(HttpMethod.GET)

        val playerByIdRoute = getRouter.path("/players/:playerid/").produces("application/json")
        playerByIdRoute.handler({ routingContext ->

            val playerId = routingContext.request().getParam("playerid").toInt()
            val player = MOCK_ISLANDS[playerId]
            val response = routingContext.response()
            response.putHeader("content-type", "application/json")
            response.end(Json.encodePrettily(player))
        })

        server.requestHandler({ router.accept(it) }).listen(8080) { result ->
            if (result.succeeded()) {
                startFuture?.complete()
            } else {
                startFuture?.fail(result.cause())
            }
        }
    }
}
