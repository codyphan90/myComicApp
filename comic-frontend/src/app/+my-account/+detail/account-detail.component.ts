/**
 * Created by CatFamily on 3/21/2018.
 */

import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';

@FadeInTop()
@Component({
    selector: 'test1',
    template: '<b>test 1111111111111111111</b>',
})
export class AccountDetailComponent implements OnInit {

    constructor() { }

    ngOnInit() {
    }

}
