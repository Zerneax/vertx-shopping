import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ClientService } from 'src/app/services/client.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-buy',
  templateUrl: './buy.component.html',
  styleUrls: ['./buy.component.scss']
})
export class BuyComponent implements OnInit {

  public buyForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private httpClient: HttpClient,
    private clientService: ClientService,
    private router: Router) { }

  ngOnInit(): void {
    this.buyForm = this.formBuilder.group(
      {product: new FormControl(undefined, Validators.required)}
    );
  }

  buy() {
    const body: any = {
      "client": this.clientService.getClient().name,
      "product": this.buyForm.get("product").value
    };

    this.httpClient.post("http://localhost:8080/api/v1/orders", body).subscribe(
      (response) => {this.router.navigate(['home'])},
      (error) => {console.log("KO")}
    )
  }

}
