import {Component, OnInit} from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";
import {FacebookService, InitParams} from "ngx-facebook";
import {TreeModel} from 'ng2-tree';
import {ActivatedRoute} from "@angular/router";
import {BookService} from "../../+service/book.service";

@FadeInTop()
@Component({
    selector: 'story',
    templateUrl: './story.component.html',
    styleUrls: ['./story.component.css']
})
export class StoryComponent extends BaseComponent implements OnInit {
    id: any;
    type: any;
    constructor(private fb: FacebookService, private route: ActivatedRoute, private bs: BookService) {
        super();
    }

    public tree: TreeModel = {
        value: 'Programming languages by programming paradigm',
        settings: this.tree_model_settings,
        children: [
            {
                value: 'Object-oriented programming',
                children: [{value: 'Java'}, {value: 'C++'}, {value: 'C#'}]
            },
            {
                value: 'Prototype-based programming',
                children: [{value: 'JavaScript'}, {value: 'CoffeeScript'}, {value: 'Lua'}]
            }
        ]
    };

    ngOnInit() {
        this.initFB();
        this.id = this.route.snapshot.paramMap.get('id');
        if (this.id) {
            this.type = this.compType.UPDATE;
            this.bs.getBookById(this.id).subscribe(response => {
                    console.log('res: ' + JSON.stringify(response));
                    var getResult = response.success;
                    if (getResult == true) {

                    } else {
                        this.errorAlert(response.exceptionMessage);
                    }
                },
                error => {
                    this.errorAlert("Has error!");
                })

        } else {
            this.type = this.compType.SAVE;
        }
    }

    initFB() {
        let initParams: InitParams = {
            appId: '163456804604097',
            cookie: true,
            xfbml: true,
            version: 'v2.9',
        };
        this.fb.init(initParams);
    }


    handleRemoved($event) {
        console.log('tree model after remove: ' + JSON.stringify(this.tree));

    }

    handleRenamed($event) {

    }

    handleNextLevel($event) {

    }

    handleCollapsed($event) {

    }

    handleExpanded($event) {

    }

    handleCreated($event) {

    }

    handleMoved($event) {

    }

    handleSelected($event) {
        console.log('selected');
    }


}