import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminLayoutRoutes } from './admin-layout.routing';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TypographyComponent } from '../../typography/typography.component';
import { IconsComponent } from '../../icons/icons.component';
import { MapsComponent } from '../../maps/maps.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { UpgradeComponent } from '../../upgrade/upgrade.component';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatNativeDateModule, MatRippleModule} from '@angular/material/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSelectModule} from '@angular/material/select';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import { InfoUserComponent } from 'app/info-user/info-user.component';
import { DetailUserComponent } from 'app/detail-user/detail-user.component';
import {MatSidenavModule} from '@angular/material/sidenav';
import { MatCardModule } from '@angular/material/card';
import { EventComponent } from 'app/event/event.component';
import { MatTimepickerModule } from 'mat-timepicker';
import {MatDialogModule} from '@angular/material/dialog';
import { MatCofnirmDialogComponent } from 'app/mat-cofnirm-dialog/mat-cofnirm-dialog.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import { ForbiddenComponent } from 'app/forbidden-component/forbidden.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AdminLayoutRoutes),
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatRippleModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTooltipModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatSidenavModule,
    MatCardModule,
    MatTimepickerModule,
    MatDialogModule,
    MatSnackBarModule,
  
  ],
  declarations: [
    DashboardComponent,
    EventComponent,
    TypographyComponent,
    IconsComponent,
    MapsComponent,
    NotificationsComponent,
    UpgradeComponent,
    InfoUserComponent, 
    DetailUserComponent,
    TableListComponent,
    MatCofnirmDialogComponent,
    ForbiddenComponent,
    
    
  ],
  entryComponents : [MatCofnirmDialogComponent],
})

export class AdminLayoutModule {}
