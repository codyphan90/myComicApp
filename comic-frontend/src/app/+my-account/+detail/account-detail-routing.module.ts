import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AccountDetailComponent} from "./account-detail.component";

const routes: Routes = [{
    path: '',
    component: AccountDetailComponent,
    data: {pageTitle: 'Account detail'}
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class AccountDetailRoutingModule {
}