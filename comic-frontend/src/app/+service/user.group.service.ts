import {Injectable} from "@angular/core";
import {AbstractService} from "./abstract.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable()
export class UserGroupService extends AbstractService {
    constructor(private http: HttpClient) {
        super();
    }

    public getUserGroupList(): any {
        console.log("get user group list!");
        const headers = new HttpHeaders({
            'X-Requested-With': 'XMLHttpRequest',
            'Content-Type': 'application/json',
            'Cache-Control': 'no-cache'
        });

        const options = {
            headers: headers
        };

        return this.http.get(environment.user_group_service.get_user_group_list_endpoint, options);
    }
}
