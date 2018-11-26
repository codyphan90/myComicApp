import {Component, OnInit} from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";
import {FacebookService, InitParams} from "ngx-facebook";
import {TreeModel} from 'ng2-tree';
import {NodeMenuItemAction} from 'ng2-tree';

@FadeInTop()
@Component({
    selector: 'create-story',
    templateUrl: './create-story.component.html',
    styleUrls: ['./create-story.component.css']
})
export class CreateStoryComponent extends BaseComponent implements OnInit {
    constructor(private fb: FacebookService) {
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