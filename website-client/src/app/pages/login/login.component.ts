import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ClientService } from 'src/app/services/client.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private clientService: ClientService,
    private router: Router) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      lastName: new FormControl(undefined, Validators.required),
      firstName: new FormControl(undefined, Validators.required)
    })
  }

  login() {
    this.clientService.setClient(this.loginForm.get("lastName").value, this.loginForm.get("firstName").value);
    this.router.navigate(['home'])
  }

}
