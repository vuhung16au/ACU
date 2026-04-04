import { Component, OnInit } from '@angular/core';
import {
  PracticeMessageRequest,
  PracticeMessageResponse,
  SpaDemoService,
  SpaModuleOverview
} from './services/spa-demo.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  public overview: SpaModuleOverview | null = null;
  public practiceMessage: PracticeMessageResponse | null = null;
  public loadError = '';
  public submitError = '';
  public isLoadingOverview = true;
  public isSubmitting = false;
  public formModel: PracticeMessageRequest = {
    studentName: 'Ava',
    currentTopic: 'Angular service calls'
  };
  public readonly title = 'SPA Integration Demo';

  constructor(private spaDemoService: SpaDemoService) {}

  ngOnInit() {
    this.loadOverview();
  }

  loadOverview() {
    this.isLoadingOverview = true;
    this.loadError = '';

    this.spaDemoService.getOverview().subscribe(
      (result) => {
        this.overview = result;
        this.isLoadingOverview = false;
      },
      (error) => {
        console.error(error);
        this.loadError = 'The Angular app could not load the starter content from ASP.NET Core.';
        this.isLoadingOverview = false;
      }
    );
  }

  submitPracticeMessage() {
    this.submitError = '';
    this.practiceMessage = null;

    if (!this.formModel.studentName.trim() || !this.formModel.currentTopic.trim()) {
      this.submitError = 'Enter both a student name and a topic before sending the request.';
      return;
    }

    this.isSubmitting = true;

    this.spaDemoService.createPracticeMessage(this.formModel).subscribe(
      (result) => {
        this.practiceMessage = result;
        this.isSubmitting = false;
      },
      (error) => {
        console.error(error);
        this.submitError = 'The backend returned an error. Check the form values and try again.';
        this.isSubmitting = false;
      }
    );
  }
}
