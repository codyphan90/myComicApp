
import {Injectable} from "@angular/core";
import {Observable, Subject} from "rxjs/Rx";
import {EMAIL_EVENT} from "../constants";

interface Event {
    event: string;
    data: any;
}

export const APP_EVENT = {
    COPY_BOOK: 'COPY_BOOK',
};

export const TARGET = {
    CreateReceiptComponent: 'CreateReceiptComponent'
};

@Injectable()
export class EventService {
    private eventSubject$: Subject<Event>;

    constructor() {
        this.eventSubject$ = new Subject<Event>();
    }

    public publish(event: Event): void {
        console.log("publish: " + JSON.stringify(event));
        this.eventSubject$.next(event);
    }

    public ofEvent(eventId: any): Observable<any> {
        return this.eventSubject$.filter(event => event.event === eventId).map(event => event);
    }


    public copyBook(data) {
        var eventData = {data: data};
        this.publish({event: APP_EVENT.COPY_BOOK, data: eventData});
    }

}
