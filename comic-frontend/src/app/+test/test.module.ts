/**
 * Created by CatFamily on 3/21/2018.
 */
import {NgModule} from '@angular/core';

import {SmartadminModule} from '../shared/smartadmin.module'

import {routing} from './test.routing';


@NgModule({
    imports: [
        SmartadminModule,
        routing,
    ],
    declarations: [],
    providers: [],
})
export class TestModule {

}
