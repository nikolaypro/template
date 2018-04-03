import { Component, OnInit } from '@angular/core';
import {LoginModel} from "./login-model";
import {NgForm} from "@angular/forms";
import {LoginService} from "./login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  // error: string = '';
  model = new LoginModel();

  constructor(private loginService: LoginService) {}

  ngOnInit() {
  }

  onSubmit(form: NgForm) {
    console.info("On submit: " + this.model);
    this.loginService.login(this.model.username, this.model.password);
  }

  get diagnostic() { return JSON.stringify(this.model); }

}
