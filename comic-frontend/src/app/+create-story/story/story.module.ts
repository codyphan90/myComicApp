import {NgModule} from '@angular/core';
import {StoryRoutingModule} from './story-routing.module';
import {StoryComponent} from './story.component';
import {SmartadminWidgetsModule} from "../../shared/widgets/smartadmin-widgets.module";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FacebookModule} from "ngx-facebook";
import { TreeModule } from 'ng2-tree';
import {AppModule} from "../../../fabric/app.module";


@NgModule({
    imports: [
        StoryRoutingModule, SmartadminWidgetsModule, CommonModule,
        FormsModule, TreeModule,
        FacebookModule.forRoot(), AppModule
    ],
    declarations: [
        StoryComponent
    ],
    providers: [],
})
export class StoryModule {

}
