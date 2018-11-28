import {AfterViewInit, Component, Input, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {BaseComponent} from "./base.component";
import {environment} from "../../environments/environment";
import {AuthService} from "../core/_auth/auth.service";
import {listType} from "../../environments/constants";
import {BookService} from "../+service/book.service";
import {APP_EVENT, EventService} from "../+service/event.service";

declare var $: any;

@Component({
    selector: 'book-list',
    templateUrl: './list.book.component.html'
})
export class ListBookComponent extends BaseComponent implements OnInit, AfterViewInit {

    @Input() type: any;
    title = '';


    id: string;
    options: any;

    renderActionButton(row): string {
        var temp = '';
        if (this.type == listType.MINE) {
            temp = '<button name="Edit" href="javascript:void(0)"><i class="fa fa-edit"></i></button>'
                + '<button name="Delete" href="javascript:void(0)"><i class="fa fa-remove"></i></button>';
        } else if (row.permission == 2) {
            temp = '<button name="Copy" href="javascript:void(0)"><i class="fa fa-copy"></i></button>';
        }
        return temp;
    }

    onActionButtonClicked(event) {
        const action = event.target.name;
        switch (action) {
            case  'Edit':
                this.update(event.rowData.id);

                break;
            case  'Delete':
                this.delete(event.rowData.id);

                break;
            case  'Copy':
                this.copyBook(event.rowData.id);

                break;
        }
    }

    onActionDoubleClicked(event) {
        this.update(event.rowData.id);
    }

    update(id) {
        this.router.navigate(['create-story/story/', id]);
    }

    delete(id) {
        if (this.confirm("Do you want to delete this book?")) {
            this.bs.delete(id).subscribe(response => {
                    console.log('res: ' + JSON.stringify(response));
                    var deleteResult = response.success;
                    if (deleteResult == true) {
                        this.successAlert("Delete book success!");
                        this.refreshDT();
                    } else {
                        this.errorAlert(response.exceptionMessage);
                    }
                },
                error => {
                    this.errorAlert("Has error!");
                })
        }
    }

    constructor(private router: Router, private route: ActivatedRoute, private  as: AuthService, private bs: BookService, private es: EventService) {
        super();
    }


    @ViewChild('bookLstDT') bookLstDT;

    refreshDT() {
        console.log('refresh DT!');
        const jDataTable = this.bookLstDT ? this.bookLstDT.jQObject() : null;
        if (jDataTable) {
            jDataTable.ajax.reload();
        }
    }

    ngOnInit() {
        this.title = this.type.des;
        var type_data;
        if (this.type == listType.FRIEND) {
            type_data = this.as.facebookFriendList;
        } else {
            type_data = this.as.getUserId();
        }
        var data = {type: this.type.code, type_data: type_data};
        var self = this;
        this.options = this.getDTOptionsWithData(environment.book_service.get_page_endpoint, [{data: "name"},
            {
                data: null,
                render: function (data, type, row, meta) {
                    return self.renderActionButton(row);
                }
            }
        ], data);

        if (this.type == listType.MINE) {
            this.es.ofEvent(APP_EVENT.COPY_BOOK).subscribe(event => {
                var self = this;
                self.refreshDT();
            })
        }
    }

    ngAfterViewInit() {

    }

    private copyBook(id: any) {
        if (this.confirm("Do you want to copy this book?")) {
            this.bs.copy(id, this.as.getUserId()).subscribe(response => {
                    console.log('res: ' + JSON.stringify(response));
                    var copyResult = response.success;
                    if (copyResult == true) {
                        this.successAlert("Copy book success!");
                        this.es.copyBook({bookId: id});
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
