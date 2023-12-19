import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'app/service/auth/auth.service';
import { TokenStorageService } from 'app/service/token-storage/token-storage.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string[];
  formdata : FormGroup;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private route:Router) { }

  ngOnInit(): void {

    const body = document.querySelector("body");
    const modal = document.querySelector(".modal");
    
    
   const modalButton = document.querySelector(".modal-button");
    const closeButton = document.querySelector(".close-button");
   
    const scrollDown = document. getElementById(".scroll-down") as HTMLElement ;

    let isOpened = false;
    
    const openModal = () => {
      modal?.classList.add("is-open");
     // body!.style.overflow = "hidden";
    };
    
    const closeModal = () => {
      modal?.classList.remove("is-open");
      body!.style.overflow = "initial";
    };
    
   window.addEventListener("scroll", () => {
      if (window.scrollY > window.innerHeight / 3 && !isOpened) {
        isOpened = true;
        scrollDown!.style.display = "none";
        openModal();
      }
    });
    
    modalButton?.addEventListener("click", openModal);
    closeButton?.addEventListener("click", closeModal);
    
   /* document.onkeydown = evt => {
    //  evt = evt || window.event;
      evt.keyCode === 27 ? closeModal() : false;
    };*/
    
    

    this.formdata = new FormGroup({
     
      username : new FormControl('', [Validators.required]),
      password : new FormControl('', [Validators.required])
   });

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.role = this.tokenStorage.getUser().role;
    }
  }

  onSubmit(): void {
    const { username, password } = this.form;
    
      
      this.authService.login(this.form).subscribe(
    
        data => {
          this.tokenStorage.saveToken(data.token);
          this.tokenStorage.saveAuthorities(data.authorities);
          this.tokenStorage.saveUser(data);
          this.route.navigate(['dashboard']);
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.role = this.tokenStorage.getUser().role;
          //this.reloadPage();
        },
        err => {
          this.errorMessage = err.error.message;
          this.isLoginFailed = true;
        }
        
      );
      
    }
    public checkError = (controlName: string, errorName: string) => {
      return this.formdata.controls[controlName].hasError(errorName);
    }

  reloadPage(): void {
    window.location.reload();
  }
}
