package org.demo.vertx.dao;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.demo.vertx.models.Order;
import org.demo.vertx.models.OrderStatut;
import org.demo.vertx.utils.Utils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderDao {

    private final static Logger logger = LoggerFactory.getLogger(OrderDao.class);

    private List<Order> orders;

    public OrderDao() {
        this.orders = new ArrayList<>();
        this.orders.add(new Order("0a249a1e-528a-11ea-8d77-2e728ce88125", "PS4", "toto", OrderStatut.READY));
        this.orders.add(new Order("0a249c8a-528a-11ea-8d77-2e728ce88125", "Santoku 18cm","Christopher Devalez ", OrderStatut.READY));
        this.orders.add(new Order("0a249dd4-528a-11ea-8d77-2e728ce88125", "Headphone","Christopher Devalez", OrderStatut.READY));
        this.orders.add(new Order("0a24a0f4-528a-11ea-8d77-2e728ce88125", "Ipad","Christopher Devalez", OrderStatut.READY));
        this.orders.add(new Order("0a24a43c-528a-11ea-8d77-2e728ce88125", "dell xps 13","Christopher Devalez", OrderStatut.READY));
    }


    public List<Order> getOrders() {
        return this.orders;
    }

    public Order getOrderById(String id) {
        this.logger.info(Utils.getTimestamp() + " : getOrderById("+ id + ") was called");
        Optional<Order> order = this.orders.stream().filter(o -> o.getId().equals(id)).findFirst();
        return order.isPresent() ? order.get() : null;
    }

    public List<Order> getOrdersByClient(String client) {
        this.logger.info(Utils.getTimestamp() + " : getOrderByClient("+ client + ") was called");
        List<Order> orders = this.orders.stream().filter(o -> o.getClient().equalsIgnoreCase(client)).collect(Collectors.toList());
        return orders;
    }

    public Order createNewOrder(String client, String product) {
        this.logger.info(Utils.getTimestamp() + " : createNewOrder("+ client + ", " + product + ") was called");
        Order order = new Order(UUID.randomUUID().toString(), product, client, OrderStatut.READY);
        this.orders.add(order);
        return order;
    }

    public Order updateOrderToTransit(String id) {
        this.logger.info(Utils.getTimestamp() + " : updateOrderToTransit("+ id + ") was called");
        Optional<Order> order = this.orders.stream().filter(o -> o.getId().equals(id)).findFirst();
        if(order.isPresent()) {
            order.get().setStatut(OrderStatut.TRANSIT);
            return order.get();
        } else {
            return null;
        }
    }

    public Order updateOrderToDelivered(String id) {
        this.logger.info(Utils.getTimestamp() + " : updateOrderToDelivered("+ id + ") was called");
        Optional<Order> order = this.orders.stream().filter(o -> o.getId().equals(id)).findFirst();
        if(order.isPresent()) {
            order.get().setStatut(OrderStatut.DELIVERED);
            return order.get();
        } else {
            return null;
        }
    }
}
