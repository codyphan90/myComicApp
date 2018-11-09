import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListBookComponent} from './list.book.component';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        FormsModule
    ],
    declarations: [ListBookComponent],
    exports:[ListBookComponent],
    providers: []
})
export class ListBookModule {
}

