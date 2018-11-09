import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListBookComponent} from './list.book.component';
import { FormsModule } from '@angular/forms';
import {I18nModule} from "../shared/i18n/i18n.module";
import {SmartadminDatatableModule} from "../shared/ui/datatable/smartadmin-datatable.module";

@NgModule({
    imports: [
        CommonModule,
        FormsModule, I18nModule, SmartadminDatatableModule
    ],
    declarations: [ListBookComponent],
    exports:[ListBookComponent],
    providers: []
})
export class ListBookModule {
}

