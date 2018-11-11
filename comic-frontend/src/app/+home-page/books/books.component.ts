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
    styleUrls: ['./books.component.css']
})
export class BooksComponent extends BaseComponent implements OnInit {

    constructor() {super(); }

    ngOnInit() {
    }

}
