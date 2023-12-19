import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { User } from 'app/model/user';
import { TokenStorageService } from '../token-storage/token-storage.service';

const AUTH_API = 'http://localhost:8080/api/auth/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  //public loggedUser:string| undefined;
  public isloggedIn: Boolean = false;
 

  users: any;
    constructor(private router: Router, private http: HttpClient,
      private tokenStorageService :  TokenStorageService) { } 

    logout() { 

      //localStorage.removeItem('loggedUser'); 
      localStorage.setItem('isloggedIn',String(this.isloggedIn)); 
      this.router.navigate(['']); 
    } 
    
    login(user:User): Observable<any> {

      
      this.isloggedIn = true;
      localStorage.setItem('isloggedIn',String(this.isloggedIn)); 
      return this.http.post(AUTH_API + 'login', user);
    }

    isAdmin(): Boolean{ 

    const auth = this.tokenStorageService.getAuthorities();
    console.log(auth);

    
    function admin(role) {
      return role.authority === 'ADMIN';
    }

    console.log(auth.find(admin));

    if ( auth.find(admin) != null ) return true
  
    
    return false;
      
    }
}