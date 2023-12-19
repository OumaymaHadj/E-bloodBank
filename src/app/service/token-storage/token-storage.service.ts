import { Injectable } from '@angular/core';

const TOKEN_KEY = 'auth-token';
const AUTH_KEY = 'auth-athorities';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
   

  }

  public saveAuthorities(authorities : string[]): void {
   
   
    window.sessionStorage.removeItem(AUTH_KEY);
    window.sessionStorage.setItem(AUTH_KEY, JSON.stringify(authorities));

  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }
  

  public getAuthorities(): string[] | null {

   return JSON.parse(window.sessionStorage.getItem(AUTH_KEY));
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
     
      return JSON.parse(user);
    }

    return {};
  }
}
