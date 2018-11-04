import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {routing} from "./auth.routing";
import {AuthComponent} from './auth.component';
import {UserService} from "../+service/user.service";
import {UserGroupService} from "../+service/user.group.service";
import {UserFormComponent} from "../base/user.form.component";


@NgModule({
    imports: [
        CommonModule,

        routing,
    ],
    declarations: [AuthComponent],
    providers: [UserService, UserGroupService]
})
export class AuthModule {
}
