import {Component, OnInit} from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";
import {listType} from "../../../environments/constants";
import {Router} from "@angular/router";

@FadeInTop()
@Component({
    selector: 'books',
    templateUrl: './books.component.html',
    styleUrls: ['./books.component.css']
})
export class BooksComponent extends BaseComponent implements OnInit {


    LIST_TYPE = listType;

    constructor(private router: Router) {
        super();
    }

    ngOnInit() {
    }

    createBook() {
        this.router.navigate(['create-story/story/']);
    }
}