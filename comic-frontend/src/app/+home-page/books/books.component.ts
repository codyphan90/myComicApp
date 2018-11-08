/**
 * Created by CatFamily on 3/21/2018.
 */

import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";

@FadeInTop()
@Component({
    selector: 'books',
    templateUrl: './books.component.html',
})
export class BooksComponent extends BaseComponent implements OnInit {

    COMP_TYPE= this.compType;
    constructor() {super(); }

    ngOnInit() {
    }

}
