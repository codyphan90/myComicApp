import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {BaseComponent} from "../../base/base.component";
import {UserService} from "../../+service/user.service";

@Component({
    selector: 'app-active',
    templateUrl: './active.component.html',
    styles: []
})
export class ActiveComponent extends BaseComponent implements OnInit {

    constructor(private router: Router, private us: UserService, private activeRoute: ActivatedRoute) {
        super();
    }

    token: '';

    message: '';

    ngOnInit() {
        console.log('token map: ' + JSON.stringify(this.activeRoute.snapshot.paramMap));
        console.log('token: ' + this.activeRoute.snapshot.paramMap.get('token'));

        this.activeRoute.queryParams.subscribe(params => {
            console.log('param: ' + JSON.stringify(params));
            this.token = params['token'];
            this.us.activeUser(this.token).subscribe(response => {
                console.log('verify res: ' + JSON.stringify(response));
                var activeResult = response.success;
                this.message = response.exceptionMessage;
            }, error => {
                this.errorAlert('Has error!');
            })

        })
    }
}
