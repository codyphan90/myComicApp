import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {Test2Component} from "./tes2.component";

const routes: Routes = [{
    path: '',
    component: Test2Component,
    data: {pageTitle: 'Test 2'}
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class Test2RoutingModule {
}