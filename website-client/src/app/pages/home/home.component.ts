import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Order } from 'src/app/models/order';
import { map, tap } from 'rxjs/operators';
import { Client } from 'src/app/models/client';
import { ClientService } from 'src/app/services/client.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public orders: Array<Order> = new Array();
  public client: Client = undefined;

  constructor(private httpClient: HttpClient,
    private clientService: ClientService) { }

  ngOnInit(): void {

    this.client = this.clientService.getClient();

    this.httpClient.get("http://localhost:8080/api/v1/orders")
      .pipe(
        map((data:any) => data.orders)
      ).subscribe(
        (orders: Array<Order>) => this.orders = orders,
        (error: any) => alert("Enable to retrieve orders")
      );
  }

}
