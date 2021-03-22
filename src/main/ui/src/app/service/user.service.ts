import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, finalize} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  authenticated = false;

  constructor(private http: HttpClient) {
  }

  authenticate(credentials, successCallback, errorCallback) {
    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get('login', {headers: headers}).pipe(catchError (() => {
      errorCallback && errorCallback();
      return Observable.prototype;
    })).subscribe(response => {
      if (response !== null) {
        this.authenticated = true;
      } else {
        this.authenticated = false;
      }
      return successCallback && successCallback();
    })
  }

  logout() {
    this.http.post('logout', {}).pipe(finalize(() => {
      this.authenticated = false;
    })).subscribe();
  }


}
