/**
 * Created by CatFamily on 3/21/2018.
 */

import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';

@FadeInTop()
@Component({
    selector: 'test2',
    template: '<b>test 22222222222</b>',
})
export class Test2Component implements OnInit {

    constructor() { }

    ngOnInit() {
    }

}
