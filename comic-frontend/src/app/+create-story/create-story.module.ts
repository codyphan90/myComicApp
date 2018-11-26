import {NgModule} from '@angular/core';

import {SmartadminModule} from '../shared/smartadmin.module'

import {routing} from './create-story.routing';
import {BookService} from "../+service/book.service";


@NgModule({
    imports: [
        SmartadminModule,
        routing,
    ],
    declarations: [],
    providers: [BookService],
})
export class CreateStoryModule {

}
