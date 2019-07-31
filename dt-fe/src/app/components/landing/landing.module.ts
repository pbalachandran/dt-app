import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {LandingComponent} from './landing.component';
import {MaterialModule} from '../../modules/material.module';

@NgModule({
  declarations: [LandingComponent],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [LandingComponent]
})
export class LandingModule { }
