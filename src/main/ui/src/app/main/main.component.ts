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
  constructor(private mainService: MainService, private userService: UserService) {
  }

  ngOnInit(): void {
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
    this.mainService.getRssItems(feed.id).subscribe(response =>{
      this.rssItems = response;
    })
  }
}
