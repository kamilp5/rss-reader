import { Component } from '@angular/core';
import {UserService} from "./service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ui';

  constructor(private userService: UserService){}
  logout() {
    this.userService.logout()
  }

  authenticated(){
    return this.userService.authenticated
  }


}
