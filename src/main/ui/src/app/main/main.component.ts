import {Component, OnInit} from '@angular/core';
import {RssFeed} from "../model/rssFeed";
import {MainService} from "../service/main.service";
import {UserService} from "../service/user.service";
import {RssItem} from "../model/rssItem";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  rssFeeds: RssFeed[] = [];
  rssItems: RssItem[] = [];
  isFeedChosen: boolean = false;
  chosenFeed: RssFeed = new RssFeed();

  constructor(private mainService: MainService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.mainService.newFeed().subscribe(() => {
      this.getFeeds();
    })
    if (this.userService.authenticated) {
      this.getFeeds();
    }
  }

  getFeeds(): void {
    this.mainService.getFeeds().subscribe(response => {
      this.rssFeeds = response;
    })
  }

  getRssItems(feed: RssFeed) {
    console.log("click")
    this.updateLastOpenedDate(feed.id)
    this.chosenFeed = feed;
    this.isFeedChosen = true;
    this.mainService.getRssItems(feed.id).subscribe(response => {
      this.rssItems = response;
    })
  }

  unsubscribeFeed(feedId: number) {
    this.mainService.unsubscribeRssFeed(feedId).subscribe()
    this.getFeeds()
  }

  authenticated() {
    return this.userService.authenticated;
  }

  updateLastOpenedDate(feedId: number) {
    this.rssFeeds.find(f => f.id == feedId).hasNewItems = false;
    this.mainService.updateLastOpenedDate(feedId).subscribe()
  }
}
