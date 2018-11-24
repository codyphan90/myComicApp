import {NgModule} from '@angular/core';
import {CreateStoryRoutingModule} from './create-story-routing.module';
import {CreateStoryComponent} from './create-story.component';
import {TreeViewModule} from "../../shared/ui/tree-view/tree-view.module";
import {SmartadminWidgetsModule} from "../../shared/widgets/smartadmin-widgets.module";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FacebookModule} from "ngx-facebook";


@NgModule({
    imports: [
        CreateStoryRoutingModule, TreeViewModule, SmartadminWidgetsModule, CommonModule,
        FormsModule,
        FacebookModule.forRoot()
    ],
    declarations: [
        CreateStoryComponent
    ],
    providers: [],
})
export class CreateStoryModule {

}
