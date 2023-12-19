import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { User } from 'app/model/user';
import { UserService } from 'app/service/user/user.service';

@Component({
  selector: 'app-info-user',
  templateUrl: './info-user.component.html',
  styleUrls: ['./info-user.component.scss']
})
export class InfoUserComponent implements OnInit {

  @Input() users: User[];
  selected? : User ; 

  constructor(private userService : UserService) { }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers() : void {
    this.userService.getAllUsers().subscribe(data => 
      {
        this.users = data;
      });
    } 
  selectUser(user:any){
    this.selected = user ; 
  }
  deleteUser(id:any){
      
      this.userService.deleteUser(id).subscribe((result)=>{
        console.log(result)
      })
  }

}
