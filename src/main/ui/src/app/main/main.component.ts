import {Component, OnInit} from '@angular/core';
import {RssFeed} from "../model/rssFeed";
import {MainService} from "../service/main.service";
import {UserService} from "../service/user.service";
import {RssItem} from "../model/rssItem";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MySnackBar} from "../service/my-snack-bar";

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

  totalElements: number;
  pageNumber: number = 0;
  pageSize: number = 50;
  pageSizeOptions: number[] = [20,50,100]

  constructor(private mainService: MainService, private userService: UserService, public snackBar: MySnackBar) {
  }

  ngOnInit(): void {
    this.mainService.newFeed().subscribe(() => {
      this.getFeeds();
    })
    if (this.userService.authenticated) {
      this.getFeeds();
    }
  }

  addItemToSaved(itemId:number){
    this.mainService.addItemToSaved(itemId).subscribe(()=>{
      this.snackBar.openSnackBar("Added item to saved")
    })
  }
  getFeeds(): void {
    this.mainService.getFeeds().subscribe(response => {
      this.rssFeeds = response;
    })
  }

  clickOnFeed(feed: RssFeed){
    this.pageNumber = 0;
    this.chosenFeed = feed;
    this.isFeedChosen = true;
    this.updateLastOpenedDate(feed.id);
    this.getRssItems(feed);
  }

  getRssItems(feed: RssFeed) {
    this.mainService.getRssItems(feed.id, this.pageNumber, this.pageSize).subscribe(response => {
      this.totalElements = response.totalElements
      this.rssItems = response.content;
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

  changePage(event) {
    this.pageNumber = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getRssItems(this.chosenFeed);
    scroll(0,0)
  }
}
