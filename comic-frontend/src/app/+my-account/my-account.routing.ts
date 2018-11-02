import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'analytics', pathMatch: 'full'
    },
    {
        path: 'detail',
        loadChildren: './+detail/account-detail.module#AccountDetailModule'
    }
    ,
    {
        path: 'test2',
        loadChildren: './+test2/test2.module#Test2Module',
    }
];

export const routing = RouterModule.forChild(routes);
