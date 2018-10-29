import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {Test1Component} from "./test1.component";

const routes: Routes = [{
    path: '',
    component: Test1Component,
    data: {pageTitle: 'Test 1'}
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class Test1RoutingModule {
}