package org.demo.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.demo.vertx.resource.ApiResource;
import org.demo.vertx.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public class ApiVerticle extends AbstractVerticle {

    private final static Logger logger = LoggerFactory.getLogger(ApiVerticle.class);

    public ApiVerticle() {
    }

    @Override
    public void start() throws Exception {
        this.logger.info(Utils.getTimestamp() + " : ApiVerticle is started");

        final Router router = Router.router(vertx);

        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("origin");
        allowedHeaders.add("Content-Type");
        allowedHeaders.add("accept");
        allowedHeaders.add("X-PINGARUNER");

        Set<HttpMethod> allowedMethods = new HashSet<>();
        allowedMethods.add(HttpMethod.GET);
        allowedMethods.add(HttpMethod.POST);
        allowedMethods.add(HttpMethod.OPTIONS);
        allowedMethods.add(HttpMethod.DELETE);
        allowedMethods.add(HttpMethod.PATCH);
        allowedMethods.add(HttpMethod.PUT);

        router.route().handler(CorsHandler.create("http://localhost:4200").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));

        final ApiResource apiResource = new ApiResource(vertx);
        final Router apiSubRouter = apiResource.getSubRouter(vertx);
        router.mountSubRouter("/api/v1/orders", apiSubRouter);

        vertx.createHttpServer(new HttpServerOptions().setLogActivity(true))
                .requestHandler(router)
                .listen(8080);
    }

    @Override
    public void stop() throws Exception {
        this.logger.info(Utils.getTimestamp() + " : ApiVerticle is stopped");
    }
}
