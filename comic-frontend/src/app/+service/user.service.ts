import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {AbstractService} from "./abstract.service";

@Injectable()
export class UserService extends AbstractService {
    constructor(private http: HttpClient) {
        super();
    }

    public register(user): any {
        console.log("user: " + JSON.stringify(user));
        const headers = new HttpHeaders({
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        });

        const options = {
            headers: headers
        };

        return this.http.post(environment.user_service.user_register_endpoint, user, options);
    }

    public resetPassword(email): any {
        const headers = new HttpHeaders({
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        });

        const options = {
            headers: headers
        };

        return this.http.post(environment.user_service.user_reset_password_endpoint, {userName:email}, options);
    }

    public getUserById(userId): any {
        console.log("get user by id: " + userId);
        const headers = new HttpHeaders({
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        });

        const options = {
            headers: headers
        };

        return this.http.get(environment.user_service.user_get_endpoint + '/' + userId, options);
    }

}