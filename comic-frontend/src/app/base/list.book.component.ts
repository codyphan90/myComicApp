import {Component, Input, OnInit, ViewChild} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {BaseComponent} from "./base.component";
import {environment} from "../../environments/environment";
declare var $: any;
@Component({
    selector: 'book-list',
    templateUrl: './list.book.component.html'
})
export class ListBookComponent extends BaseComponent implements OnInit {

    @Input() type: any;
    title = '';


    id: string;
    options = this.getDTOptions(environment.book_service.get_page_endpoint, [{data: "name"},
        {
            data: null,
            render: function (data, type, row, meta) {
                return ListBookComponent.renderActionButton(row);
            }
        }
    ]);

    static renderActionButton(row): string {
        var temp = '<button name="Edit" href="javascript:void(0)"><i class="fa fa-edit"></i>Edit</button>'
            + '<button name="Delete" href="javascript:void(0)"><i class="fa fa-remove"></i>Delete</button>';
        return temp;
    }

    onActionButtonClicked(event) {
        const action = event.target.name;
        switch (action) {
            case  'Edit':
                this.update(event.rowData.id);

                break;
            case  'Delete':
                this.confirmDelete(event.rowData.id);

                break;
        }
    }

    update(id) {
    }


    confirmDelete(id) {
    }

    delete(id) {
    }

    constructor(private router: Router, private route: ActivatedRoute) {
        super();
    }


    @ViewChild('repLstDT') repLstDT;

    refreshDT() {
        console.log('refresh DT!');
        const jDataTable = this.repLstDT ? this.repLstDT.jQObject() : null;
        if (jDataTable) {
            jDataTable.ajax.reload();
        }
    }

    ngOnInit() {
        this.title = this.type.des;
    }

}
