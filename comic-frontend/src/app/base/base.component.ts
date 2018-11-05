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

    confirm(message: string): boolean {
        return window.confirm(message);
    }
}