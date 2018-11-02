import {Component, OnInit, TemplateRef} from '@angular/core';
import {Router} from "@angular/router";


import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {User} from "../../bo/user";
import {UserService} from "../../+service/user.service";
import {UserGroupService} from "../../+service/user.group.service";
import {BaseComponent} from "../base/BaseComponent";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styles: []
})
export class RegisterComponent extends BaseComponent implements OnInit {


    bsModalRef: BsModalRef;
    public termsAgreed = false;

    constructor(private us: UserService,
                private router: Router,
                private modalService: BsModalService, private  ugs: UserGroupService) {
        super();
    }

    user: User = {id: null, userName: null, password: null, firstName: null, lastName: null, groupId:0, isActive: null, passwordConfirm: null};
    userGroups: any[];

    ngOnInit() {
        this.ugs.getUserGroupList().subscribe(response => {
                console.log('response: ' + JSON.stringify(response));
                this.userGroups = response.dataResponse;
            },
            error => {
                this.errorAlert('Has error!');
            }
        )
    }

    register(event) {
        event.preventDefault();
        if (this.user.password != this.user.passwordConfirm) {
            this.errorAlert("Password and confirm password not matched!");
            return;
        }
        this.us.register(this.user).subscribe(response => {
            console.log('res: ' + JSON.stringify(response));
            var registerResult = response.success;
            if (registerResult == true) {
                this.successAlert('Register success!');
                this.router.navigate(['/auth/login']);
            } else {
                this.errorAlert(response.exceptionMessage);
            }

        }, error => {
            this.errorAlert('Has error!');
        })
    }

    openModal(event, template: TemplateRef<any>) {
        event.preventDefault();
        this.bsModalRef = this.modalService.show(template);
    }

    onTermsAgree() {
        this.termsAgreed = true;
        this.bsModalRef.hide()
    }

    onTermsClose() {
        this.bsModalRef.hide()
    }


}
