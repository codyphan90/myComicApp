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

    settings: {
        'static': true,
        'rightMenu': true,
        'leftMenu': true,
        'cssClasses': {
            'expanded': 'fa fa-caret-down fa-lg',
            'collapsed': 'fa fa-caret-right fa-lg',
            'leaf': 'fa fa-lg',
            'empty': 'fa fa-caret-right disabled'
        },
        'templates': {
            'node': '<i class="fa fa-folder-o fa-lg"></i>',
            'leaf': '<i class="fa fa-file-o fa-lg"></i>',
            'leftMenu': '<i class="fa fa-navicon fa-lg"></i>'
        },
        'menuItems': [
            { action: NodeMenuItemAction.Custom, name: 'Foo', cssClass: 'fa fa-arrow-right' },
            { action: NodeMenuItemAction.Custom, name: 'Bar', cssClass: 'fa fa-arrow-right' },
            { action: NodeMenuItemAction.Custom, name: 'Baz', cssClass: 'fa fa-arrow-right' }
            ]
    };

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