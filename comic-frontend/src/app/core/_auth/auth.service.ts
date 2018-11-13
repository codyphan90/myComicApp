import {Injectable} from '@angular/core';
import {JwtHelper, tokenNotExpired} from 'angular2-jwt';
import {AbstractService} from '../../+service/abstract.service';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {environment} from '../../../environments/environment';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";

@Injectable()
export class AuthService extends AbstractService {
    jwtHelper: JwtHelper = new JwtHelper();
    isUserLogedIn$ = new BehaviorSubject<boolean>(this.isUserAuthenticated());

    constructor(private http: HttpClient) {
        super();
    }

    public login(username: string, password: string): any {
        console.log("user name: " + username);
        const headers = new HttpHeaders({
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        });
        const options = {
            headers: headers
        };

        return this.http.post(environment.user_service.user_validation_endpoint, {
            userName: username,
            password: password
        }, options);
    }

    private getDecodedToken(): any {
        const token = localStorage.getItem('id_token');
        return this.jwtHelper.decodeToken(token);
    }

    public logout() {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('id_token');
    }

    public isUserAuthenticated() {
        return tokenNotExpired('id_token');
    }

    public getIdToken(): string {
        return tokenNotExpired('id_token') ? localStorage.getItem('id_token') : null;
    }

    public getUsername(): string {
        const dtk = this.getDecodedToken();
        return dtk.sub;
    }

    public getUserId(): string {
        const dtk = this.getDecodedToken();
        return dtk.sub;
    }


    public getGroupName(): string {
        return '';
    }

    public getUserRoles(): string[] {
        const dtk = this.getDecodedToken();
        return dtk.scopes;
    }

    public loginSuccess(user) {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        localStorage.setItem('id_token', user.dataResponse);
        this.isUserLogedIn$.next(true);
        const dtk = this.getDecodedToken();
        console.log('token: ' + JSON.stringify(dtk));
        window.location.href = environment.authen.home_url;
    }

}
