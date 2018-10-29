import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {AuthService} from './auth.service';
import {environment} from '../../../environments/environment';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router, private authService: AuthService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        // return true;
        console.log('state.url: ' + state.url);
        if (this.authService.isUserAuthenticated()) {
            console.log('AuthGuard - checked! Current user: ' + this.authService.getUsername());
            console.log('state.url: ' + state.url);
            if (state.url == environment.authen.login_route) {
                window.location.href = environment.authen.home_url;
            }
            return true;
        } else {
            this.redirectToLoginPage(state);
            return false;
        }
    }

    private redirectToLoginPage(state: RouterStateSnapshot) {
        const relativeUrl = state.url;
        window.location.href = environment.authen.login_url;
    }
}
