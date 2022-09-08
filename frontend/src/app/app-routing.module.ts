import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {AboutComponent} from "./about/about.component";
import {UserComponent} from "./user/user.component";
import {EditUserComponent} from "./user/edit-user/edit-user.component";
import {LoginComponent} from "./login/login.component";
import {AuthGaurdService} from "./services/auth-gaurd.service";
import {MovieComponent} from './movie/movie.component';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'movie', component: MovieComponent, canActivate: [AuthGaurdService]},
  {path: 'newUser', component: EditUserComponent},
  {path: 'user', component: UserComponent, canActivate: [AuthGaurdService]},
  {path: 'editUser/:id', component: EditUserComponent, canActivate: [AuthGaurdService]},
  {path: 'about', component: AboutComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
