import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationService} from '../services/notification.service';
import {MovieService} from '../services/movie.service';


@Component({
  selector: 'app-movie',
  templateUrl: './movie.component.html',
  styleUrls: ['./movie.component.css']
})
export class MovieComponent implements OnInit {

  form: FormGroup;

  constructor(private service: MovieService, private router: Router, private route: ActivatedRoute, private notifyService: NotificationService) {
  }

  ngOnInit(): void {
    this.createForm();
    // this.populateMask();
  }


  private createForm() {
    this.form = new FormGroup({
      createdBy: new FormControl(null),
      title: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(50)]),
      description: new FormControl(null, [Validators.required, Validators.minLength(3), Validators.maxLength(200)])
    });
  }


  formSubmit(form: FormGroup) {
    if (form.valid) {
      this.form.markAsPristine();
      this.form.controls.createdBy.setValue(sessionStorage.getItem('userId'));
      this.service.addMovie(form.getRawValue()).subscribe(response => {
        this.notifyService.showSuccess('New Movie added');
        this.router.navigate(['']);
      });


    } else {
      form.markAsDirty();
      this.notifyService.showError('Form is invalid');
    }
  }
}
