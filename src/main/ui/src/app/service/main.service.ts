import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, ReplaySubject} from "rxjs";
import {RssFeed} from "../model/rssFeed";

@Injectable({
  providedIn: 'root'
})
export class MainService {

  newFeedStream: ReplaySubject<RssFeed> = new ReplaySubject();


  constructor(private http: HttpClient) {
  }


  getFeeds(): Observable<any> {
    return this.http.get('/api/rssFeeds');
  }

  getRssItems(feedId: number, page: number, size: number): Observable<any> {
    const url = `/api/rssFeeds/${feedId}?page=${page}&size=${size}`;
    return this.http.get(url)
  }

  saveRssFeed(newFeedUrl: string) {
    this.http.post<RssFeed>('/api/rssFeeds', newFeedUrl).subscribe(response => {
      this.newFeedStream.next(response);
    })
  }

  unsubscribeRssFeed(feedId): Observable<any> {
    const url = `/api/rssFeeds/${feedId}`;
    return this.http.delete(url, null)
  }

  newFeed(): Observable<RssFeed> {
    return this.newFeedStream.asObservable();
  }

  updateLastOpenedDate(feedId: number): Observable<any> {
    const url = `/api/rssFeeds/${feedId}`;
    return this.http.put(url, null)
  }
}
