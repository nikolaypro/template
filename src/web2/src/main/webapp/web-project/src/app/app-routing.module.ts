import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AppComponent} from "./app.component";
import {Path1Component} from "./path1/path1.component";
import {LoginComponent} from "./login/login.component";
import {Path2Component} from "./path2/path2.component";

const routes: Routes = [
  {path: '',redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: 'path1', component: Path1Component},
  {path: 'path2', component: Path2Component}
];

@NgModule({
  exports: [RouterModule],
  imports: [
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule { }
