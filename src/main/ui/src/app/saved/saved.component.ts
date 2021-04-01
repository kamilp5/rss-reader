import { Component, OnInit } from '@angular/core';
import {RssItem} from "../model/rssItem";
import {MainService} from "../service/main.service";
import {UserService} from "../service/user.service";
import {MySnackBar} from "../service/my-snack-bar";

@Component({
  selector: 'app-saved',
  templateUrl: './saved.component.html',
  styleUrls: ['./saved.component.css']
})
export class SavedComponent implements OnInit {

  rssItems: RssItem[] = [];

  totalElements: number;
  pageNumber: number = 0;
  pageSize: number = 50;
  pageSizeOptions: number[] = [20,50,100]

  constructor(private mainService: MainService, private userService: UserService, public snackBar: MySnackBar) {
  }

  ngOnInit(): void {
    this.getSavedItems()
  }

  getSavedItems(){
    this.mainService.getSavedItems(this.pageNumber, this.pageSize).subscribe(response => {
      this.totalElements = response.totalElements
      this.rssItems = response.content;
    })
  }

  authenticated() {
    return this.userService.authenticated;
  }

  changePage(event) {
    this.pageNumber = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getSavedItems();
    scroll(0,0)
  }

  deleteItemFromSaved(itemId: number) {
    this.mainService.removeItemFromSaved(itemId).subscribe(()=>{
      this.snackBar.openSnackBar("Item removed from saved")
      this.rssItems = this.rssItems.filter(i => i.id!==itemId);
    })
  }
}
