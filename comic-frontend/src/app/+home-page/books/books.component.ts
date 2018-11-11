import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";

@FadeInTop()
@Component({
    selector: 'books',
    templateUrl: './books.component.html',
})
export class BooksComponent extends BaseComponent implements OnInit {
    listType: any = {
        MINE: {code:"MINE", des:"My books"}, FRIEND: {code:"FRIEND", des:"My friend's books"}, TRENDING: {code:"TRENDING", des:"Trending books"}
    };

    LIST_TYPE = this.listType;
    constructor() {super(); }

    ngOnInit() {
    }

}