import {NgModule} from '@angular/core';
import {CreateStoryRoutingModule} from './create-story-routing.module';
import {CreateStoryComponent} from './create-story.component';


@NgModule({
    imports: [
        CreateStoryRoutingModule
    ],
    declarations: [
        CreateStoryComponent
    ],
    providers: [],
})
export class CreateStoryModule {

}
