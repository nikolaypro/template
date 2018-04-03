import { Component } from '@angular/core';
import {LoginService} from "./login/login.service";
import {MascotHttpService} from "./common/mascot-http.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [LoginService, MascotHttpService, HttpClient]
})
export class AppComponent {
  title = 'app';
}
