/**
 * Created by CatFamily on 3/21/2018.
 */

import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";

@FadeInTop()
@Component({
    selector: 'account-detail',
    templateUrl: './account-detail.component.html',
})
export class AccountDetailComponent extends BaseComponent implements OnInit {

    COMP_TYPE= this.compType;
    constructor() {super(); }

    ngOnInit() {
    }

}
