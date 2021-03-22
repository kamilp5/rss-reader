import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  authenticated = false;

  constructor(private http: HttpClient) {
    console.log('elo')
    this.authenticate({username: "user",password: "pass" },null)
  }

  authenticate(credentials, callback){
    const headers = new HttpHeaders(credentials? {
      authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    }: {});

    this.http.get('login', {headers: headers}).subscribe(response => {
      console.log(response);
      if(response['username']) {
        this.authenticated = true;
      }else{
        this.authenticated = false;
      }
      return callback && callback();
    })

  }


}
