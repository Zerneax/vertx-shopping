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

public class OrderDao {

    private final static Logger logger = LoggerFactory.getLogger(OrderDao.class);

    private List<Order> orders;

    public OrderDao() {
        this.orders = new ArrayList<>();
        this.orders.add(new Order("0a249a1e-528a-11ea-8d77-2e728ce88125", "Mr Devalez Christopher", "PS4", OrderStatut.READY));
        this.orders.add(new Order("0a249c8a-528a-11ea-8d77-2e728ce88125", "Mr Devalez Christopher", "PS4", OrderStatut.READY));
        this.orders.add(new Order("0a249dd4-528a-11ea-8d77-2e728ce88125", "Mr Devalez Christopher", "PS4", OrderStatut.READY));
        this.orders.add(new Order("0a24a0f4-528a-11ea-8d77-2e728ce88125", "Mr Devalez Christopher", "PS4", OrderStatut.READY));
        this.orders.add(new Order("0a24a43c-528a-11ea-8d77-2e728ce88125", "Mr Devalez Christopher", "PS4", OrderStatut.READY));
    }


    public List<Order> getOrders() {
        return this.orders;
    }

    public Order getOrderById(String id) {
        this.logger.info(Utils.getTimestamp() + " : getOrderById("+ id + ") was called");
        Optional<Order> order = this.orders.stream().filter(o -> o.getId().equals(id)).findFirst();
        return order.isPresent() ? order.get() : null;
    }

    public Order createNewOrder(String client, String product) {
        this.logger.info(Utils.getTimestamp() + " : createNewOrder("+ client + ", " + product + ") was called");
        Order order = new Order(UUID.randomUUID().toString(), client, product, OrderStatut.READY);
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
