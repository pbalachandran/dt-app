import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LandingModule} from './components/landing/landing.module';
import {LandingComponent} from './components/landing/landing.component';
import {MaterialModule} from './modules/material.module';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HeaderModule} from './components/header/header.module';
import {FooterModule} from './components/footer/footer.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    MaterialModule,
    AppRoutingModule,
    HeaderModule,
    FooterModule,
    LandingModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    HeaderComponent,
    FooterComponent,
    LandingComponent
  ]
})
export class AppModule {
}
