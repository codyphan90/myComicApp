import {RouterModule, Routes} from '@angular/router';


export const routes: Routes = [
    {
        path: '', redirectTo: 'books', pathMatch: 'full'
    },
    {
        path: 'books',
        loadChildren: './books/books.module#BooksModule'
    }
];

export const routing = RouterModule.forChild(routes);
