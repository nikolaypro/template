import { Injectable } from '@angular/core';
import {MascotHttpService} from "../common/mascot-http.service";

@Injectable()
export class LoginService {

  constructor(private httpService: MascotHttpService) { }

  public login(login: string, psw: string): void  {
    let url = this.httpService.url('api/authenticate');
    console.info("URL: " + url);
    let encodedPassword = window.btoa(psw);
    this.httpService.postPromise(url).then((result: any) => {
      console.info(result);
    });
/*
    var encodedPassword = UserService.base64().encode(UserService.encode_utf8(password));
    $http.post(url, { login: username, password: encodedPassword })
      .success(function (response) {
        response.success = true;
        callback(response);
      })
      .error(function (data, status, headers, config) {
        var message = 'Unknown error';
        if (status == 404) {
          message = 'Server not found';
        }
        if (status == 401 && data.error && data.error.indexOf('trial version') != -1 ) {
          message = data.error;
        } else if (status == 401 && data.error) {
          message = 'Username or password is incorrect';
        }
        var response = { success: false, message: message };
        callback(response);
      });
*/

  }

}
