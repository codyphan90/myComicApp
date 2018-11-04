export class BaseComponent {
    compType: any = {
        SAVE: "SAVE", UPDATE:"UPDATE"
    };

    errorAlert(message: string) {
        alert(message);
    }
    successAlert(message: string) {
        alert(message);
    }
}