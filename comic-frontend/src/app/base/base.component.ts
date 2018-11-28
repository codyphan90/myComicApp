import {NodeMenuItemAction} from "ng2-tree";

export class BaseComponent {

    tree_model_settings = {
        'cssClasses': {
            'expanded': 'fa fa-caret-down fa-lg',
            'collapsed': 'fa fa-caret-right fa-lg',
            'leaf': 'fa fa-lg',
            'empty': 'fa fa-caret-right disabled'
        },
        'templates': {
            'node': '<i class="fa fa-book fa-lg"></i>',
            'leaf': '<i class="fa fa-pencil-square-o fa-lg"></i>',
            'leftMenu': '<i class="fa fa-navicon fa-lg"></i>'
        },
        'menuItems': [
            { action: NodeMenuItemAction.NewFolder, name: 'Add node', cssClass: 'fa fa-arrow-right' },
            { action: NodeMenuItemAction.Remove, name: 'Delete node', cssClass: 'fa fa-arrow-right' },
            { action: NodeMenuItemAction.Rename, name: 'Rename', cssClass: 'fa fa-arrow-right' }
        ]
    };

    compType: any = {
        SAVE: "SAVE", UPDATE: "UPDATE"
    };

    errorAlert(message: string) {
        alert(message);
    }

    successAlert(message: string) {
        alert(message);
    }

    confirm(message: string): boolean {
        return window.confirm(message);
    }

    common_options: any = {
        processing: true,
        serverSide: true,
        ajax: {url:'',data:null},
        pageLength: 10,
        paginationLength: true,
        bFilter: false,
        columns: [],
        columnDefs: [{
            targets: [0, 1],
            orderable: false,
        }
        ],
        colReorder: true,
        lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]]
    };

    public getDTOptions(url: any, columns: any): any {
        var _options = this.common_options;
        _options.ajax.url = url;
        _options.columns = columns;
        return _options;
    }
    public getDTOptionsWithData(url: any, columns: any, data): any {
        var _options = this.common_options;
        _options.ajax.url = url;
        _options.ajax.data = data;
        _options.columns = columns;
        return _options;
    }
}