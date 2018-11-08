import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BooksComponent} from "./books.component";

const routes: Routes = [{
    path: '',
    component: BooksComponent,
    data: {pageTitle: 'Account detail'}
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class BooksRoutingModule {
}