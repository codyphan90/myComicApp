import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserFormComponent} from "../base/user.form.component";
import { FormsModule } from '@angular/forms';
import {UserService} from "../+service/user.service";
import {UserGroupService} from "../+service/user.group.service";

@NgModule({
    imports: [
        CommonModule,
        FormsModule
    ],
    declarations: [UserFormComponent],
    exports:[UserFormComponent],
    providers: [UserService, UserGroupService]
})
export class UserFormModule {
}

