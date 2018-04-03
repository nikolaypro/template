import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";


import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { Path1Component } from './path1/path1.component';
import { LoginComponent } from './login/login.component';
import { Path2Component } from './path2/path2.component';
import { TopMenuComponent } from './top-menu/top-menu.component';
import {AccordionModule} from "ngx-bootstrap";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent,
    Path1Component,
    LoginComponent,
    Path2Component,
    TopMenuComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    AccordionModule.forRoot(),
    HttpClientModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
