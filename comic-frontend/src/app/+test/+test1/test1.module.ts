/**
 * Created by CatFamily on 3/21/2018.
 */

import {NgModule} from '@angular/core';

import {Test1RoutingModule} from './test1-routing.module';
import {Test1Component} from './test1.component';


@NgModule({
    imports: [
        Test1RoutingModule
    ],
    declarations: [
        Test1Component
    ],
    providers: [],
})
export class Test1Module {

}
