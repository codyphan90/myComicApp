/**
 * Created by CatFamily on 3/21/2018.
 */

import {NgModule} from '@angular/core';

import {AccountDetailRoutingModule} from './account-detail-routing.module';
import {AccountDetailComponent} from './account-detail.component';
import {UserFormModule} from "../../base/user.form.module";
import {UserService} from "../../+service/user.service";
import {UserGroupService} from "../../+service/user.group.service";


@NgModule({
    imports: [
        AccountDetailRoutingModule, UserFormModule
    ],
    declarations: [
        AccountDetailComponent
    ],
    providers: [],
})
export class AccountDetailModule {

}
