import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as EventBus from 'vertx3-eventbus-client';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public orders: Array<any> = new Array();
  public eb;

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    this.initEventBus();
    this.getOrdersReady();

  }

  pickOrder(id: string) {
    this.httpClient.put(`http://localhost:8080/api/v1/orders/${id}/transit`, {}).subscribe(
      (response: any) => this.getOrdersReady(),
      (error) => alert("ko")
    );
  }

  getOrdersReady() {
    this.httpClient.get("http://localhost:8080/api/v1/orders/ready").subscribe(
      (response: any) => this.orders= response.orders,
      (error) => console.log("KO")
    )
  }

  initEventBus() {
    this.eb = new EventBus("http://localhost:8080/newOrder/", {"vertxbus_ping_interval": 3000});
    this.eb.onopen = () => {
      this.eb.registerHandler("event", {}, (error, message) => {
          console.log("Message: " + message.body);
          let newOrder = JSON.parse(JSON.stringify(message.body));
          console.log("object : ", newOrder);
          this.orders.push(newOrder);
      });
    }

    // this.eb.enableReconnect(true);

  }

  send(idOrder: string) {
    this.eb.publish('ready.to.go', idOrder, {}, (err, message) => {
      console.log("ok");
    });
  }
}
