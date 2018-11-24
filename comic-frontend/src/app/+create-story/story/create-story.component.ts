import { Component, OnInit } from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";
import {FacebookService, InitParams} from "ngx-facebook";

@FadeInTop()
@Component({
    selector: 'create-story',
    templateUrl: './create-story.component.html',
    styleUrls: ['./create-story.component.css']
})
export class CreateStoryComponent extends BaseComponent implements OnInit {
    constructor(private fb: FacebookService) {super(); }

    demo2: any = [
        {"content": "<span><i class=\"fa fa-lg fa-book\"></i>Parent/span>", "expanded": true, "children": [
                {"content": "<span> Administrators</span>", "expanded": true, "children": [
                        {"content": "<span> <label class=\"checkbox inline-block\"><input type=\"checkbox\" name=\"checkbox-inline\"><i></i>Michael.Jackson</label> </span>"},
                        {"content": "<span> <label class=\"checkbox inline-block\"><input type=\"checkbox\" checked=\"checked\" name=\"checkbox-inline\"><i></i>Sunny.Ahmed</label> </span>"},
                        {"content": "<span> <label class=\"checkbox inline-block\"><input type=\"checkbox\" checked=\"checked\" name=\"checkbox-inline\"><i></i>Jackie.Chan</label> </span>"}
                    ]},
                {"content": "<span> <label class=\"checkbox inline-block\"><input type=\"checkbox\" name=\"checkbox-inline\"><i></i>Michael.Jackson</label> </span>"},
                {"content": "<span> <label class=\"checkbox inline-block\"><input type=\"checkbox\" checked=\"checked\" name=\"checkbox-inline\"><i></i>Sunny.Ahmed</label> </span>"},
                {"content": "<span> <label class=\"checkbox inline-block\"><input type=\"checkbox\" checked=\"checked\" name=\"checkbox-inline\"><i></i>Jackie.Chan</label> </span>"},
                {"content": "<span> Child</span>", "expanded": true, "children": [
                        {"content": "<span><i class=\"icon-leaf\"></i> Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Grand Child</span>"},
                        {"content": "<span> Grand Child</span>",  "children": [
                                {"content": "<span> Great Grand Child</span>", "children": [
                                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"}
                                    ]},
                                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                                {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"},
                                {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"}
                            ]},
                        {"content": "<span> Great Grand Child</span>", "children": [
                                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"}
                            ]},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"}
                    ]},
                {"content": "<span><i class=\"icon-leaf\"></i> Grand Child</span>"},
                {"content": "<span><i class=\"icon-leaf\"></i> Grand Child</span>"},
                {"content": "<span> Grand Child</span>", "children": [
                        {"content": "<span> Great Grand Child</span>", "children": [
                                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"}
                            ]},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"}
                    ]},
                {"content": "<span> Great Grand Child</span>", "children": [
                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                        {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"}
                    ]},
                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                {"content": "<span><i class=\"icon-leaf\"></i> Great great Grand Child</span>"},
                {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"},
                {"content": "<span><i class=\"icon-leaf\"></i> Great Grand Child</span>"}
            ]},
        {"content": "<span><i class=\"fa fa-lg fa-folder-open\"></i> Parent2</span>", "children": [
                {"content": "<span><i class=\"icon-leaf\"></i> Child</span>"}
            ]}
    ];

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



    changeListener(payload) {
        console.log('change payload', payload)
    }
}