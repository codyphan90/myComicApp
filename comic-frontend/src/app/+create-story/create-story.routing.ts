import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'story', pathMatch: 'full'
    },
    {
        path: 'story',
        loadChildren: './story/create-story.module#CreateStoryModule'
    }
];

export const routing = RouterModule.forChild(routes);
