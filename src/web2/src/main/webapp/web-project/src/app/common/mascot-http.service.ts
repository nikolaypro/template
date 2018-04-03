import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Location} from "@angular/common";
import {DefaultUrlSerializer, UrlTree} from "@angular/router";

@Injectable()
export class MascotHttpService {
  HTTP_REQUEST_HEADER: HttpHeaders = new HttpHeaders({
    'Accept': 'application/json',
    'Content-Type': 'application/json; charset=UTF-8'
  });
  API_URL = "template";
  USE_BASE_URL = true;
  BASE_URL = "http://localhost:8180";
  LONG_URLS = ['/logout', '/reports/'];

  constructor(private http: HttpClient, private location: Location) {

  }

  url(url): string {
    // let asd = window.location.hostname;
    // location = window.location;
    if (this.USE_BASE_URL) {
      return this.BASE_URL + '/' + this.API_URL + '/' + url;
    }
    return location.protocol + '//' + location.hostname + ':' + location.port + '/' + this.API_URL + '/' + url;
    // return location.protocol() + '://' + $location.host() + ':' + $location.port() + '/' + this.BASE_URL + '/' + url;
  }

  isApiUrl(url): boolean {
    return typeof url != 'undefined' &&  url.indexOf('/' + this.API_URL + '/') > -1;
  }

  isShowLongRequest(url): boolean {
    for (let i = 0; i < this.LONG_URLS.length; i++) {
      if (url.indexOf(this.LONG_URLS[i]) > -1) {
        return true;
      }
    }
    return false;
  }

  getPromise(url: string): Promise<any> {
    return new Promise<any>((resolve: any, reject: any) => {
      try {
        this.http.get(url, {headers: this.HTTP_REQUEST_HEADER})
          .map((response: any) => response)
          // .catch(e => {
          //   reject(e);
          //   return Observable.throw(e);
          // })
          .subscribe((response: any) => resolve(response));
      } catch (e) {
        console.error(e);
        reject(e);
      }
    })
  }

  postPromise(url: string): Promise<any> {
    return new Promise<any>((resolve: any) => {
      this.http.post(url, {headers: this.HTTP_REQUEST_HEADER})
        .map((response: any) => response)
        .subscribe((response: any) => resolve(response));

    })
  }

}
