import {Component, OnInit,Input, TemplateRef} from '@angular/core';
import {Router} from "@angular/router";


import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {User} from "../bo/user";
import {UserService} from "../+service/user.service";
import {UserGroupService} from "../+service/user.group.service";
import {BaseComponent} from "./base.component";
import {AuthService} from "../core/_auth/auth.service";

@Component({
    selector: 'user-form',
    templateUrl: './user.form.component.html',
    styles: []
})
export class UserFormComponent extends BaseComponent implements OnInit {


    bsModalRef: BsModalRef;
    public termsAgreed = false;

    constructor(private us: UserService,
                private router: Router,
                private modalService: BsModalService, private  ugs: UserGroupService, private as: AuthService) {
        super();
    }
    @Input() type: any;
    user: User = {id: null, userName: null, password: null, firstName: null, lastName: null, groupId:0, isActive: null, passwordConfirm: null};
    userGroups: any[];
    header: string = '';
    ngOnInit() {
        console.log('Component type: ' + this.type);
        if (this.isSave()) {
            this.header = 'Registration';
        } else {
            this.header =  'Account detail';
        }
        this.ugs.getUserGroupList().subscribe(response => {
                console.log('response: ' + JSON.stringify(response));
                this.userGroups = response.dataResponse;
                if (!this.isSave()) {

                }
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

    update(event) {
        event.preventDefault();

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

    isSave() : boolean {
        return (this.type == this.compType.SAVE);
    }

}
