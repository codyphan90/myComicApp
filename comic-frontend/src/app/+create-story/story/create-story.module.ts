import {NgModule} from '@angular/core';
import {CreateStoryRoutingModule} from './create-story-routing.module';
import {CreateStoryComponent} from './create-story.component';
import {TreeViewModule} from "../../shared/ui/tree-view/tree-view.module";
import {SmartadminWidgetsModule} from "../../shared/widgets/smartadmin-widgets.module";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";


@NgModule({
    imports: [
        CreateStoryRoutingModule, TreeViewModule, SmartadminWidgetsModule, CommonModule,
        FormsModule,
    ],
    declarations: [
        CreateStoryComponent
    ],
    providers: [],
})
export class CreateStoryModule {

}
