/**
 * Created by CatFamily on 3/21/2018.
 */
import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'analytics', pathMatch: 'full'
    },
    {
        path: 'test1',
        loadChildren: './+test1/test1.module#Test1Module'
    }
    ,
    {
        path: 'test2',
        loadChildren: './+test2/test2.module#Test2Module',
    }
];

export const routing = RouterModule.forChild(routes);
