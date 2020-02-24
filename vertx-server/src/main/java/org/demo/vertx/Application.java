package org.demo.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.demo.vertx.verticle.ApiVerticle;
import org.demo.vertx.verticle.BusVerticle;

public class Application {

    public static void main(String[] args) {
        //final Vertx vertx = Vertx.vertx();
        ClusterManager mgr = new HazelcastClusterManager();

        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                EventBus eventBus = vertx.eventBus();
                System.out.println("We now have a clustered event bus: " + eventBus);
                vertx.deployVerticle(new ApiVerticle());
            } else {
                System.out.println("Failed: " + res.cause());
            }
        });
        //vertx.deployVerticle(new ApiVerticle());
        //vertx.deployVerticle("transport.js");

    }
}
