import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MainService {

  constructor(private http: HttpClient) { }


  getFeeds(): Observable<any>{
    return this.http.get('/api/rssFeeds');
  }

  getRssItems(feedId: number): Observable<any>{
    const url = `/api/rssFeeds/${feedId}`;
    return this.http.get(url)
  }
}
