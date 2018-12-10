import {Component, OnInit, Input, TemplateRef} from '@angular/core';
import {Router} from "@angular/router";


import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {User} from "../bo/user";
import {UserService} from "../+service/user.service";
import {UserGroupService} from "../+service/user.group.service";
import {BaseComponent} from "./base.component";
import {AuthService} from "../core/_auth/auth.service";
import {FacebookService, InitParams, LoginOptions, LoginResponse} from "ngx-facebook";
import {JwtHelper, tokenNotExpired} from 'angular2-jwt';
import {ApiMethod} from "ngx-facebook/dist/esm/providers/facebook";
import {forEach} from "@angular/router/src/utils/collection";

@Component({
    selector: 'user-form',
    templateUrl: './user.form.component.html',
    styles: []
})
export class UserFormComponent extends BaseComponent implements OnInit {

    jwtHelper: JwtHelper = new JwtHelper();
    bsModalRef: BsModalRef;
    public termsAgreed = false;


    constructor(private us: UserService,
                private router: Router,
                private modalService: BsModalService, private  ugs: UserGroupService, private as: AuthService, private fb: FacebookService) {
        super();
    }
    changePass: boolean = false;

    @Input() type: any;
    user: User = {
        id: null,
        userName: null,
        password: null,
        firstName: null,
        lastName: null,
        groupId: 0,
        isActive: null,
        newPassword: null,
        passwordConfirm: null,
        facebookId: null
    };
    userGroups: any[];
    header: string = '';

    ngOnInit() {
        // console.log('Component type: ' + this.type);
        if (this.isSave()) {
            this.header = 'Registration';
        } else {
            this.initFB();
            this.header = 'Account detail';
        }
        this.ugs.getUserGroupList().subscribe(response => {
                console.log('response: ' + JSON.stringify(response));
                this.userGroups = response.dataResponse;
                if (!this.isSave()) {
                    this.us.getUserByName(this.as.getUsername()).subscribe(response => {
                            console.log('user: ' + JSON.stringify(response));
                            this.user = response.dataResponse;
                            this.user.passwordConfirm = '';
                            this.user.password = '';
                            this.user.newPassword = '';
                        },
                        error => {
                            this.errorAlert(error);
                        }
                    )
                }
            },
            error => {
                this.errorAlert(error.message);
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
            // console.log('res: ' + JSON.stringify(response));
            var registerResult = response.success;
            if (registerResult == true) {
                this.successAlert('Register success!');
                this.router.navigate(['/auth/login']);
            } else {
                this.errorAlert(response.exceptionMessage);
            }

        }, error => {
            this.errorAlert(error.message);
        })
    }

    isChangePassword(): boolean {
        return (this.user.password && this.user.password != '' && this.user.newPassword && this.user.newPassword != '');
    }

    update(event) {
        event.preventDefault();
        if (this.isChangePassword() && (this.user.newPassword != this.user.password)) {
            this.errorAlert("New Password must be different to current Password!");
            return;
        }
        if (this.isChangePassword() && (this.user.newPassword != this.user.passwordConfirm)) {
            this.errorAlert("Password and confirm password not matched!");
            return;
        }

        this.us.update(this.user).subscribe(response => {
            // console.log('res: ' + JSON.stringify(response));
            var updateResult = response.success;
            if (updateResult == true) {
                this.successAlert('Update user success!');
                this.resetChangePassForm();
            } else {
                this.errorAlert(response.exceptionMessage);
            }

        }, error => {
            this.errorAlert(error.message);
        })

    }

    resetChangePassForm() {
        (<HTMLInputElement>document.getElementById('password')).value = '';
        (<HTMLInputElement>document.getElementById('newPassword')).value = '';
        (<HTMLInputElement>document.getElementById('confirmPass')).value = '';
        (<HTMLInputElement>document.getElementById('ChangePassWordDiv')).style.display = "none";

    }

    doChangePass()  {
        // $(".ChangePassWordDiv").css("visibility", "visible");
        // document.getElementById('ChangePassWordDiv').style.display='inline';
        var x = document.getElementById("ChangePassWordDiv");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }

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

    isSave(): boolean {
        return (this.type == this.compType.SAVE);
    }



    verifyEmail() {
        if (this.confirm(`Do you want to send verify link to email ${this.user.userName}`)) {
            this.us.verifyEmail(this.user.userName).subscribe(response => {
                var sendVerifyEmailResult = response.success;
                if (sendVerifyEmailResult == true) {
                    this.successAlert('An verify email has sent to your email addrress!');
                } else {
                    this.errorAlert(response.exceptionMessage);
                }

            }, error => {
                this.errorAlert(error.message);
            })
        }
    }

    initFB() {
        let initParams: InitParams = {
            appId: '163456804604097',
            cookie: true,
            xfbml: true,
            version: 'v3.0',
        };
        this.fb.init(initParams);
    }

    connectFacebook() {
        const options: LoginOptions = {
            scope: 'public_profile,user_friends,email',
            return_scopes: true,
            enable_profile_selector: true
        };

        this.fb.login(options)
            .then((response: LoginResponse) => {
                // console.log(response);
                // console.log("status: " + response.status);
                if (response.status == 'connected') {
                    var token = response.authResponse.accessToken;
                    var facebookUserID = response.authResponse.userID;
                    // console.log('facebook id: ' + facebookUserID);
                    if (!this.user.facebookId) {
                        if (this.confirm("Do you want connect to this facebook user?")) {
                            this.us.connectFacebook(this.user.userName, facebookUserID).subscribe(response => {
                                // console.log('connect res: ' + JSON.stringify(response));
                                var connectFacebookResult = response.success;
                                if (connectFacebookResult == true) {
                                    this.successAlert('Connect facebook success!');
                                    this.user.facebookId = facebookUserID;
                                    this.getListFriendFacebook();
                                } else {
                                    this.errorAlert(response.exceptionMessage);
                                }

                            }, error => {
                                this.errorAlert(error.message);
                            })
                        }
                    } else {
                        this.getListFriendFacebook();
                    }
                }
            }).catch((error: any) => {
            this.errorAlert(error.message)
        });
    }

    getListFriendFacebook() {
        this.fb.api('/' + this.user.facebookId + '/friends',).then((res: any) => {
            var listFriendFacebookId = res.data.map(user => user.id);
            this.as.facebookFriendList = listFriendFacebookId;
        }).catch((error: any) => this.errorAlert(error.message));

    }

}
