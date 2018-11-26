import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {StoryComponent} from "./story.component";

const routes: Routes = [{
    path: '',
    component: StoryComponent,
    data: {pageTitle: 'Create Story'}
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class StoryRoutingModule {
}