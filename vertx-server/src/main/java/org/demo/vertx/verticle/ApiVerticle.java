package org.demo.vertx.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.BridgeEventType;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.demo.vertx.models.CodecOrder;
import org.demo.vertx.models.Order;
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
        allowedHeaders.add("Access-Control-Allow-Credentials");
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

        router.route().handler(CorsHandler.create(".*.").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));

        final ApiResource apiResource = new ApiResource(vertx);
        final Router apiSubRouter = apiResource.getSubRouter(vertx);
        router.mountSubRouter("/api/v1/orders", apiSubRouter);


        SockJSHandler sockJSHandler = SockJSHandler.create(vertx);
        BridgeOptions options = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddress("event"))
                .addInboundPermitted(new PermittedOptions().setAddress("ready.to.go"))
                .addOutboundPermitted(new PermittedOptions().setAddress("ready.to.go"))
                .addOutboundPermitted(new PermittedOptions().setAddress("event"));
        router.mountSubRouter("/newOrder", sockJSHandler.bridge(options, be -> {
            if(be.type() == BridgeEventType.SOCKET_CREATED)
                System.out.println("A socket was created");

            be.complete(true);
        }));

        this.vertx.eventBus().registerDefaultCodec(Order.class, new CodecOrder());

        this.vertx.eventBus().consumer("delivered", res -> {
            System.out.println("Order : " + res.body() + " has been deliver.");
        });

        vertx.createHttpServer(new HttpServerOptions().setLogActivity(true))
                .requestHandler(router)
                .listen(8080);
    }

    @Override
    public void stop() throws Exception {
        this.logger.info(Utils.getTimestamp() + " : ApiVerticle is stopped");
    }
}
