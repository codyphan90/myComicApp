import {Component, OnInit, TemplateRef} from '@angular/core';
import {Router} from "@angular/router";


import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {User} from "../../bo/user";
import {UserService} from "../../+service/user.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styles: []
})
export class RegisterComponent implements OnInit {


    bsModalRef: BsModalRef;
    public termsAgreed = false

    constructor(private us: UserService,
        private router: Router,
        private modalService: BsModalService) {
    }

    username: string;
    password: string;
    passwordConfirm: string;
    firstname: string;
    lastname: string;

    ngOnInit() {
    }

    register(event) {
        event.preventDefault();
        if (this.password != this.passwordConfirm) {
            alert("Password and confirm password not matched!");
            return;
        }
        var user: User = {
            id: null, userName: this.username, password: this.password, firstName: this.firstname, lastName: this.lastname, groupId:1, isActive: null
        };
        this.us.register(user).subscribe(response => {
            console.log('res: ' + JSON.stringify(response));
            var registerResult = response.success;
            if (registerResult == true) {
                alert("Register success!");
            } else {
                alert(response.exceptionMessage);
            }

        }, error => {
            alert("Has error!");
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
