import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'story', pathMatch: 'full'
    },
    {
        path: 'story',
        loadChildren: './story/create-story.module#CreateStoryModule'
    },
    {
        path: 'story/:id',
        loadChildren: './story/create-story.module#CreateStoryModule',
        data: {pageTitle: 'Book detail'}
    }

];

export const routing = RouterModule.forChild(routes);
