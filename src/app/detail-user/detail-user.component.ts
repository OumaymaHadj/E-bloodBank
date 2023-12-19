import { Component, Input, OnInit } from '@angular/core';
import { User } from 'app/model/user';
import { UserService } from 'app/service/user/user.service';

@Component({
  selector: 'app-detail-user',
  templateUrl: './detail-user.component.html',
  styleUrls: ['./detail-user.component.scss']
})
export class DetailUserComponent implements OnInit {

  @Input() user: User;
  constructor(private userService: UserService,
   // private route : Route
   ) { }

  ngOnInit(): void {
    
  }
}
