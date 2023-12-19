import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'app/model/user';
import { UserService } from 'app/service/user/user.service';


@Component({
  selector: 'app-table-list',
  templateUrl: './table-list.component.html',
  styleUrls: ['./table-list.component.css']
})
export class TableListComponent implements OnInit {
  
  @Input() users?: User[];
  @Output() selectedUser = new EventEmitter();

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers() : void {
    this.userService.getAllUsers().subscribe(data => 
      { this.users = data;     
      
      });
  }

  selectUser(user){
    this.selectedUser = user;
  }
}
