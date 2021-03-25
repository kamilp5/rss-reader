import {Component} from '@angular/core';
import {UserService} from "./service/user.service";
import {MatDialog} from "@angular/material/dialog";
import {NewFeedDialogComponent} from "./new-feed-dialog/new-feed-dialog.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  newFeedUrl: string;

  constructor(private userService: UserService, public dialog: MatDialog) {
  }


  logout() {
    this.userService.logout()
  }

  authenticated() {
    return this.userService.authenticated
  }

  onAddRssFeedClick(): void {
    this.dialog.open(NewFeedDialogComponent)
  }
}
