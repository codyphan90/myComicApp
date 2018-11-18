import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {CreateStoryComponent} from "./create-story.component";

const routes: Routes = [{
    path: '',
    component: CreateStoryComponent,
    data: {pageTitle: 'Create Story'}
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
    providers: []
})
export class CreateStoryRoutingModule {
}