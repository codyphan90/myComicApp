import {NgModule} from '@angular/core';
import {BooksRoutingModule} from './books-routing.module';
import {BooksComponent} from './books.component';
import { ListBookModule } from 'app/base/list.book.module';


@NgModule({
    imports: [
        BooksRoutingModule,
        ListBookModule
    ],
    declarations: [
        BooksComponent
    ],
    providers: [],
})
export class BooksModule {

}
