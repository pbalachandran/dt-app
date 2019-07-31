import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatFormFieldModule, MatInputModule, MatNativeDateModule, MatToolbarModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

@NgModule({
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule
    // MatDividerModule,
    // MatFormFieldModule,
    // MatIconModule,
    // MatInputModule,
    // MatNativeDateModule,
    // MatOptionModule,
    // MatSelectModule,
    // MatExpansionModule,
    // MatButtonModule,
    // MatCheckboxModule,
    // MatDialogModule

  ],
  declarations: [],
  exports: [
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule
    // MatDividerModule,
    // MatFormFieldModule,
    // MatIconModule,
    // MatInputModule,
    // MatNativeDateModule,
    // MatOptionModule,
    // MatSelectModule,
    // MatExpansionModule,
    // MatButtonModule,
    // MatCheckboxModule,
    // MatDialogModule
    // MatToolbar
  ]
})
export class MaterialModule {
}
