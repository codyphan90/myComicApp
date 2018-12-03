import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ColorPickerModule } from 'ngx-color-picker';

import { AppComponent } from './app.component';
import {CommonModule} from "@angular/common";
import {FacebookModule} from "ngx-facebook";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ColorPickerModule, FacebookModule.forRoot()
  ],
  exports :[AppComponent],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
