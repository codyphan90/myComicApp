import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'story', pathMatch: 'full'
    },
    {
        path: 'story',
        loadChildren: './story/story.module#StoryModule'
    },
    {
        path: 'story/:id',
        loadChildren: './story/story.module#StoryModule',
        data: {pageTitle: 'Book detail'}
    }

];

export const routing = RouterModule.forChild(routes);
