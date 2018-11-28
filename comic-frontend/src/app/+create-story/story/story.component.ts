import {Component, OnInit, ViewChild} from '@angular/core';
import {FadeInTop} from '../../shared/animations/fade-in-top.decorator';
import {BaseComponent} from "../../base/base.component";
import {FacebookService, InitParams} from "ngx-facebook";
import {NodeEvent, TreeModel} from 'ng2-tree';
import {ActivatedRoute} from "@angular/router";
import {BookService} from "../../+service/book.service";
import {BookNode} from "../../bo/book.node";
import {AuthService} from "../../core/_auth/auth.service";
import {treeNodeType} from "../../../environments/constants";
import {AppComponent} from "../../../fabric/app.component";

@Component({
    selector: 'story',
    templateUrl: './story.component.html',
    styleUrls: ['./story.component.css']
})
export class StoryComponent extends BaseComponent implements OnInit {
    id: any;
    type: any;
    book: any;

    @ViewChild('fabric') fabricComponent : AppComponent;

    constructor(private fb: FacebookService, private route: ActivatedRoute, private bs: BookService, private as: AuthService) {
        super();
    }

    public tree: TreeModel;
    @ViewChild('bookTree') bookTree;
    showFabric: boolean = true;

    buildTreeModelFromBook() {
        let treeModel: any = {};
        treeModel.value = this.book.name;
        let bookChildren: BookNode[] = [];
        treeModel.children = bookChildren;
        treeModel.icon = "fa fa-book";
        treeModel.type = treeNodeType.BOOK;
        for (let chapter of this.book.chapterEntityList) {
            let chapterChildren: BookNode[] = [];

            let chapterNode: BookNode = {
                value: chapter.name,
                children: [],
                icon : "fa fa-pencil",
                type : treeNodeType.CHAPTER
            };
            chapterNode.children = chapterChildren;
            bookChildren.push(chapterNode);
            for (let topic of chapter.topicEntityList) {
                let topicChildren: BookNode[] = [];

                let topicNode: BookNode = {
                    value: topic.name,
                    children: [],
                    icon : "fa fa-pencil",
                    type:treeNodeType.TOPIC
                };
                topicNode.children = topicChildren;
                chapterChildren.push(topicNode);
                for (let subTopic of topic.subTopicEntityList) {
                    let subTopicNode = {
                        value: subTopic.name,
                        children: [],
                        icon : "fa fa-pencil",
                        type: treeNodeType.SUB_TOPIC,
                        content: subTopic.content
                    };
                    topicChildren.push(subTopicNode);
                }
            }
        }
        treeModel.settings = this.tree_model_settings;
        if (!this.isMyBook()) {
            treeModel.settings.menuItems = [];
        }
        this.tree = treeModel;
        console.log('tree model: ' + JSON.stringify(this.tree));
    }

    isMyBook() {
        return ((!this.book)  || (this.book.userEntity.id == this.as.getUserId()));
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
            let treeModel: any = {};
            treeModel.value = '...';
            treeModel.type = treeNodeType.BOOK;
            let bookChildren: BookNode[] = [];
            treeModel.children = bookChildren;
            treeModel.settings = this.tree_model_settings;
            this.tree = treeModel;
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

    handleCreated(e: NodeEvent) {
        var parentNodeType = e.node.parent.toTreeModel().type;
        console.log('parent node type: ' + parentNodeType);
        var  newTreeModel: TreeModel = e.node.toTreeModel();
        newTreeModel.type = parentNodeType + 1;
        const treeController = this.bookTree.getControllerByNodeId(e.node.id);

    }

    handleMoved($event) {

    }

    handleSelected(e: NodeEvent) {
        console.log('node type: ' + e.node.toTreeModel().type);
        var nodeType = e.node.toTreeModel().type;
        if (nodeType == treeNodeType.SUB_TOPIC) {
            this.showFabric = true;
            var content = e.node.toTreeModel().content;
            if (this.fabricComponent) {
                console.log('set canvas content: ' + content);
                localStorage.setItem('Kanvas', content);
                this.fabricComponent.loadCanvasFromJSON();
            }
        }
    }

    buildBookEntityFromModel(treeModel: TreeModel): any {
        var bookEntity: any = {};
        bookEntity.name = treeModel.value;
        bookEntity.userEntity = {id: this.as.getUserId()};
        bookEntity.chapterEntityList = [];
        if (this.type == this.compType.UPDATE) {
            bookEntity.id = this.id;
        }

        for (let chapterModel of treeModel.children) {
            var chapterEntity: any = {};
            chapterEntity.name = chapterModel.value;
            bookEntity.chapterEntityList.push(chapterEntity);
            chapterEntity.topicEntityList = [];
            for (let topicModel of chapterModel.children) {
                var topicEntity: any = {};
                topicEntity.name = topicModel.value;
                chapterEntity.topicEntityList.push(topicEntity);
                topicEntity.subTopicEntityList = [];
                for (let subTopicModel of topicModel.children) {
                    var subTopicEntity: any = {};
                    subTopicEntity.name = subTopicModel.value;
                    subTopicEntity.content = '{"test_canvas_json":123456}';
                    topicEntity.subTopicEntityList.push(subTopicEntity);
                }
            }

        }

        return bookEntity;
    }

    saveOrUpdateBook() {
        var bookEntity: any = this.buildBookEntityFromModel(this.bookTree.getController().toTreeModel());
        console.log('save book: ' + JSON.stringify(bookEntity));

        if (this.type == this.compType.UPDATE) {
            this.bs.update(bookEntity).subscribe(response => {
                    console.log('res: ' + JSON.stringify(response));
                    var updateResult = response.success;
                    if (updateResult == true) {
                        this.successAlert("Update book success!");
                    } else {
                        this.errorAlert(response.exceptionMessage);
                    }
                },
                error => {
                    this.errorAlert("Has error!");
                })
        } else {
            this.bs.save(bookEntity).subscribe(response => {
                    console.log('res: ' + JSON.stringify(response));
                    var createResult = response.success;
                    if (createResult == true) {
                        this.successAlert("Create book success!");
                    } else {
                        this.errorAlert(response.exceptionMessage);
                    }
                },
                error => {
                    this.errorAlert("Has error!");
                })
        }
    }

}