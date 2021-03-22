import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from "@angular/forms";
import {ErrorStateMatcher} from "@angular/material/core";
import {Router} from "@angular/router";


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm;
  loginError = false;

  constructor(private userService: UserService, private router: Router) {

  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
      password: new FormControl(''),
    });

  }

  login() {
    const credentials = {
      username: this.loginForm.value.email,
      password: this.loginForm.value.password
    }
    this.userService.authenticate(credentials,
      () => this.router.navigateByUrl('/'),
      () => this.loginError = true);
  }

  matcher = new MyErrorStateMatcher();


  get email() {
    return this.loginForm.get('email');
  }

  get password() {
    return this.loginForm.get('password');
  }
}
