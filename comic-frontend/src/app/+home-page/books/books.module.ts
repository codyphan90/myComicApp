/**
 * Created by CatFamily on 3/21/2018.
 */

import {NgModule} from '@angular/core';

import {BooksRoutingModule} from './books-routing.module';
import {BooksComponent} from './books.component';
import {UserFormModule} from "../../base/user.form.module";
import {UserService} from "../../+service/user.service";
import {UserGroupService} from "../../+service/user.group.service";


@NgModule({
    imports: [
        BooksRoutingModule, UserFormModule
    ],
    declarations: [
        BooksComponent
    ],
    providers: [],
})
export class BooksModule {

}
