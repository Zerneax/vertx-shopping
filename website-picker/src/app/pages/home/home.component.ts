import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public orders: Array<any> = new Array();

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {

    this.httpClient.get("http://localhost:8080/api/v1/orders/ready").subscribe(
      (response: any) => this.orders= response.orders,
      (error) => console.log("KO")
    )
  }

}
