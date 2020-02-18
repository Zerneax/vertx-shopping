package org.demo.vertx;

import io.vertx.core.Vertx;
import org.demo.vertx.verticle.ApiVerticle;

public class Application {

    public static void main(String[] args) {
        final Vertx vertx = Vertx.vertx();

        vertx.deployVerticle(new ApiVerticle());
    }
}
