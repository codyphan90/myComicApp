export class BaseComponent {
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
        buttons: [
            'copy', 'pdf'
        ],
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