package org.demo.vertx.resource;

import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.demo.vertx.dao.OrderDao;
import org.demo.vertx.models.CodecOrder;
import org.demo.vertx.models.Order;

import java.util.List;

public class ApiResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResource.class);
    private OrderDao orderDao;
    private Vertx vertx;

    public ApiResource(final Vertx vertx) {
        this.orderDao = new OrderDao();
        this.vertx = vertx;
    }

    public Router getSubRouter(final Vertx vertx) {
        final Router subRouter = Router.router(vertx);
        subRouter.get("/").handler(this::getAllOrders);
        subRouter.route("/").handler(BodyHandler.create());
        subRouter.post("/").handler(this::createOrder);
        subRouter.put("/:id/transit").handler(this::changeOrderStatutToTransit);
        subRouter.put("/:id/delivered").handler(this::changeOrderStatutToDelivered);
        subRouter.get("/ready").handler(this::getOrdersReady);
        subRouter.get("/:client").handler(this::getOrdersOfClient);

        return subRouter;
    }

    public void getAllOrders(RoutingContext routingContext) {
        List<Order> orders = this.orderDao.getOrders();

        final JsonObject jsonResponse = new JsonObject();
        jsonResponse.put("orders", orders);

        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encode(jsonResponse));
    }

    public void getOrdersOfClient(RoutingContext routingContext) {
        final String client = routingContext.pathParam("client");

        List<Order> orders = this.orderDao.getOrdersByClient(client);

        final JsonObject jsonResponse = new JsonObject();
        jsonResponse.put("orders", orders);

        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encode(jsonResponse));
    }

    public void getOrdersReady(RoutingContext routingContext) {

        List<Order> orders = this.orderDao.getOrdersReady();

        final JsonObject jsonResponse = new JsonObject();
        jsonResponse.put("orders", orders);

        routingContext.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(Json.encode(jsonResponse));
    }

    public void createOrder(RoutingContext routingContext) {
        this.LOGGER.info("Creating new order....");
        final JsonObject body = routingContext.getBodyAsJson();
        final String client = body.getString("client");
        final String product = body.getString("product");
        final Order order = this.orderDao.createNewOrder(client, product);

        final JsonObject orderJson = new JsonObject();
        orderJson.put("id", order.getId());
        orderJson.put("product", order.getProduct());
        orderJson.put("client", order.getClient());
        orderJson.put("statut", order.getStatut());

        this.vertx.eventBus().publish("event", orderJson);
        this.LOGGER.info("publish ok....");
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json")
                .end(Json.encode(order));

    }

    public void changeOrderStatutToTransit(RoutingContext routingContext) {
        final String id = routingContext.pathParam("id");

        this.orderDao.updateOrderToTransit(id);
        routingContext.response()
                .setStatusCode(202)
                .end();
    }

    public void changeOrderStatutToDelivered(RoutingContext routingContext) {
        final String id = routingContext.pathParam("id");

        this.orderDao.updateOrderToDelivered(id);
        routingContext.response()
                .setStatusCode(202)
                .end();
    }
}
