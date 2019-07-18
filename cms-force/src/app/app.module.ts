import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { HttpClientModule } from '@angular/common/http';
import { NgSelectModule } from '@ng-select/ng-select';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContentCreatorPageComponent } from './components/content-creator-page/content-creator-page.component';
import { ContentFinderPageComponent } from './components/content-finder-page/content-finder-page.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ModuleCreatorPageComponent } from './components/module-creator-page/module-creator-page.component';
import { ModuleIndexPageComponent } from './components/module-index-page/module-index-page.component';
import { ReportsPageComponent } from './components/reports-page/reports-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReportsTimeGraphComponent } from './components/reports-time-graph/reports-time-graph.component';


@NgModule({
   declarations: [
      AppComponent,
      ContentCreatorPageComponent,
      ContentFinderPageComponent,
      NavbarComponent,
      ModuleCreatorPageComponent,
      ModuleIndexPageComponent,
      ReportsPageComponent,
      ReportsTimeGraphComponent
   ],
   imports: [
      BrowserModule,
      AppRoutingModule,
      HttpClientModule,
      NgSelectModule,
      FormsModule,
      NgxChartsModule,
      BrowserAnimationsModule
   ],
   providers: [],
   bootstrap: [AppComponent]
})
export class AppModule { }
