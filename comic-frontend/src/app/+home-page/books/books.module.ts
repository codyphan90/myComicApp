/**
 * Created by CatFamily on 3/21/2018.
 */

import {NgModule} from '@angular/core';

import {BooksRoutingModule} from './books-routing.module';
import {BooksComponent} from './books.component';
import {ListBookComponent} from '../../base/list.book.component'
import {UserFormModule} from "../../base/user.form.module";
import {UserService} from "../../+service/user.service";
import {UserGroupService} from "../../+service/user.group.service";
import { ListBookModule } from 'app/base/list.book.module';


@NgModule({
    imports: [
        BooksRoutingModule, UserFormModule, ListBookModule
    ],
    declarations: [
        BooksComponent
    ],
    providers: [],
})
export class BooksModule {

}
