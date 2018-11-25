import {NgModule} from '@angular/core';
import {CreateStoryRoutingModule} from './create-story-routing.module';
import {CreateStoryComponent} from './create-story.component';
import {SmartadminWidgetsModule} from "../../shared/widgets/smartadmin-widgets.module";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FacebookModule} from "ngx-facebook";
import { TreeModule } from 'ng2-tree';


@NgModule({
    imports: [
        CreateStoryRoutingModule, SmartadminWidgetsModule, CommonModule,
        FormsModule, TreeModule,
        FacebookModule.forRoot()
    ],
    declarations: [
        CreateStoryComponent
    ],
    providers: [],
})
export class CreateStoryModule {

}
