import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {routing} from "./auth.routing";
import {AuthComponent} from './auth.component';
import {UserService} from "../+service/user.service";


@NgModule({
    imports: [
        CommonModule,

        routing,
    ],
    declarations: [AuthComponent],
    providers: [UserService]
})
export class AuthModule {
}
