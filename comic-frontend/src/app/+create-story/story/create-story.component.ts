import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";

@FadeInTop()
@Component({
    selector: 'create-story',
    templateUrl: './create-story.component.html',
    styleUrls: ['./create-story.component.css']
})
export class CreateStoryComponent extends BaseComponent implements OnInit {
    // listType: any = {
    //     MINE: {code:"MINE", des:"My books"}, FRIEND: {code:"FRIEND", des:"My friend's books"}, TRENDING: {code:"TRENDING", des:"Trending books"}
    // };
    //
    // LIST_TYPE = this.listType;
    constructor() {super(); }

    ngOnInit() {
    }

}