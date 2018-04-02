import { Component, OnInit } from '@angular/core';
import {LoginModel} from "./login-model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  // error: string = '';
  model = new LoginModel();

  ngOnInit() {
  }

  get diagnostic() { return JSON.stringify(this.model); }

}
