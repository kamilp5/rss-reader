import {Component, OnInit} from '@angular/core';
import {UserService} from "../service/user.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {MyErrorStateMatcher} from "../login/login.component";
import {User} from "../model/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm;
  errorMessage = null;

  constructor(private userService: UserService, private router: Router) {

  }

  ngOnInit(): void {

    this.registerForm = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
      password: new FormControl(''),
    });
  }

  register() {
    let user: User = {
      email: this.registerForm.value.email,
      password: this.registerForm.value.password
    };
    this.userService.register(user).subscribe(response => {
      this.router.navigateByUrl('/');
    }, (error) => {
      this.errorMessage = error.error
    });
  }

  matcher = new MyErrorStateMatcher();


  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }
}
