import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../core/_auth/auth.service";
import {environment} from "../../../environments/environment";
import {BaseComponent} from "../../base/base.component";

const USER_NAME_REMEMBER_KEY='username_remember';
const PASSWORD_REMEMBER_KEY='password_remember';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html'
})

export class LoginComponent extends BaseComponent implements OnInit {

    constructor(private router: Router, private as: AuthService) {
        super();
    }


    password: string;
    username: string;
    rememberMe: boolean = true;

    ngOnInit() {
        if (this.as.isUserAuthenticated()) {
            window.location.href = environment.authen.home_url;
        } else {
            this.username = localStorage.getItem(USER_NAME_REMEMBER_KEY);
            this.password = localStorage.getItem(PASSWORD_REMEMBER_KEY);
            console.log('remember me: ' + this.rememberMe);
        }
    }

    login(event) {
        event.preventDefault();
        this.as.login(this.username, this.password).subscribe(response => {
                console.log('res: ' + JSON.stringify(response));
                var loginResult = response.success;
                if (loginResult == true) {
                    this.as.loginSuccess(response);
                    this.rememberLogin();
                } else {
                    this.errorAlert(response.exceptionMessage);
                }
            },
            error => {
                this.errorAlert(error.message);
            })
    }

    rememberLogin() {
        if (this.rememberMe) {
            console.log('remember me');
            localStorage.setItem(USER_NAME_REMEMBER_KEY, this.username);
            localStorage.setItem(PASSWORD_REMEMBER_KEY, this.password);
        } else {
            localStorage.removeItem(USER_NAME_REMEMBER_KEY);
            localStorage.removeItem(PASSWORD_REMEMBER_KEY);
        }
    }

}
