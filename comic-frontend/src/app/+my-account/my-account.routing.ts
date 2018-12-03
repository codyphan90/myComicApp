import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'detail', pathMatch: 'full'
    },
    {
        path: 'detail',
        loadChildren: './+detail/account-detail.module#AccountDetailModule'
    }
];

export const routing = RouterModule.forChild(routes);
