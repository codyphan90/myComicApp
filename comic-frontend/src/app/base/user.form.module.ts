import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserFormComponent} from "../base/user.form.component";
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        FormsModule
    ],
    declarations: [UserFormComponent],
    exports:[UserFormComponent],
    providers: []
})
export class UserFormModule {
}

