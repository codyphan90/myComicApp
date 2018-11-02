/**
 * Created by CatFamily on 3/21/2018.
 */

import {NgModule} from '@angular/core';

import {AccountDetailRoutingModule} from './account-detail-routing.module';
import {AccountDetailComponent} from './account-detail.component';


@NgModule({
    imports: [
        AccountDetailRoutingModule
    ],
    declarations: [
        AccountDetailComponent
    ],
    providers: [],
})
export class AccountDetailModule {

}
