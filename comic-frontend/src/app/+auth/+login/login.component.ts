import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../core/_auth/auth.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  constructor(private router: Router, private as: AuthService) { }
  password:string;
  username:string;

  ngOnInit() {
      if (this.as.isUserAuthenticated()) {
          window.location.href = environment.authen.home_url;
      }
  }

  login(event) {
      event.preventDefault();
      this.as.login(this.username, this.password).subscribe(response => {
          console.log('res: ' + JSON.stringify(response));
          var loginResult = response.success;
          if (loginResult == true) {
            this.as.loginSuccess(response);
          } else {
            alert(response.exceptionMessage);
          }
      })
  }

}
