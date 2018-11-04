import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActiveRoutingModule} from './active-routing.module';
import {ActiveComponent} from './active.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  imports: [
    CommonModule,
      FormsModule,
    ActiveRoutingModule
  ],
  declarations: [ActiveComponent]
})
export class ActiveModule { }
