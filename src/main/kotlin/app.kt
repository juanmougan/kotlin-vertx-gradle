import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import io.vertx.core.json.Json
import io.vertx.ext.web.Router

/**
 * Starting point
 *
 * Created by juanmougan@gmail.com on 8/10/17.
 */

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

fun main(args: Array<String>) {
    val vertx = Vertx.vertx()
    val server = vertx.createHttpServer()
    val getRouter = Router.router(vertx).route().method(HttpMethod.GET)

    val playerByIdRoute = getRouter.path("/players/:playerid/").produces("application/json")
    playerByIdRoute.handler({ routingContext ->

        val playerId = routingContext.request().getParam("playerid").toInt()
        val player = MOCK_ISLANDS[playerId]
        var response = routingContext.response()
        response.putHeader("content-type", "application/json")
        response.end(Json.encodePrettily(player))
    })
}

