import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MainService} from "../service/main.service";

@Component({
  selector: 'app-new-feed-dialog',
  templateUrl: './new-feed-dialog.component.html',
  styleUrls: ['./new-feed-dialog.component.css']
})
export class NewFeedDialogComponent {

  newFeedUrl: string;

  constructor(
    private mainService: MainService,
    public newFeedDialog: MatDialogRef<NewFeedDialogComponent>,
      @Inject(MAT_DIALOG_DATA) public data: any){
  }

  onSaveClick(): void {
    this.mainService.saveRssFeed(this.newFeedUrl);
    this.newFeedDialog.close();
  }

  onCancelClick(): void {
    this.newFeedDialog.close();
  }
}
