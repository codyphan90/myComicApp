import {Component, OnInit} from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";
import {FacebookService, InitParams} from "ngx-facebook";
import {TreeModel} from 'ng2-tree';
import {ActivatedRoute} from "@angular/router";
import {BookService} from "../../+service/book.service";
import {BookNode} from "../../bo/book.node";

@FadeInTop()
@Component({
    selector: 'story',
    templateUrl: './story.component.html',
    styleUrls: ['./story.component.css']
})
export class StoryComponent extends BaseComponent implements OnInit {
    id: any;
    type: any;
    book: any;

    constructor(private fb: FacebookService, private route: ActivatedRoute, private bs: BookService) {
        super();
    }

    public tree: TreeModel;

    buildTreeModelFromBook() {
        let treeModel: any = {};
        treeModel.value = this.book.name;
        let bookChildren: BookNode[] = [];
        treeModel.children = bookChildren;
        for (let chapter of this.book.chapterEntityList) {
            let chapterChildren: BookNode[] = [];

            let chapterNode: BookNode = {
                value: chapter.name,
                children: []
            };
            chapterNode.children = chapterChildren;
            bookChildren.push(chapterNode);
            for (let topic of chapter.topicEntityList) {
                let topicChildren: BookNode[] = [];

                let topicNode: BookNode = {
                    value: topic.name,
                    children: []
                };
                topicNode.children = topicChildren;
                chapterChildren.push(topicNode);
                for (let subTopic of topic.subTopicEntityList) {

                    let subTopicNode: BookNode = {
                        value: subTopic.name,
                        children: []
                    };
                    topicChildren.push(subTopicNode);
                }
            }
        }
        treeModel.settings = this.tree_model_settings;
        this.tree =  treeModel;
        console.log('tree model: ' + JSON.stringify(this.tree));
    }

    ngOnInit() {
        this.initFB();
        this.id = this.route.snapshot.paramMap.get('id');
        if (this.id) {
            this.type = this.compType.UPDATE;
            this.bs.getBookById(this.id).subscribe(response => {
                    console.log('res: ' + JSON.stringify(response));
                    var getResult = response.success;
                    if (getResult == true) {
                        this.book = response.dataResponse;
                        this.buildTreeModelFromBook();
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

    saveBook() {
        alert(JSON.stringify(this.tree._status));
    }


}