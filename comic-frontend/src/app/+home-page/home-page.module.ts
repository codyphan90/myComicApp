import {NgModule} from '@angular/core';

import {SmartadminModule} from '../shared/smartadmin.module'

import {routing} from './home-page.routing';


@NgModule({
    imports: [
        SmartadminModule,
        routing,
    ],
    declarations: [],
    providers: [],
})
export class HomePageModule {

}
