import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HeaderComponent} from './header.component';
import {MaterialModule} from '../../modules/material.module';

@NgModule({
  declarations: [HeaderComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [HeaderComponent]
})
export class HeaderModule { }
