import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface ApiRouteInfo {
  httpMethod: string;
  route: string;
  purpose: string;
}

export interface SpaModuleOverview {
  title: string;
  description: string;
  localDevelopmentFlow: string;
  publishedDeploymentFlow: string;
  apiRoutes: ApiRouteInfo[];
  learningChecklist: string[];
}

export interface PracticeMessageRequest {
  studentName: string;
  currentTopic: string;
}

export interface PracticeMessageResponse {
  heading: string;
  message: string;
  nextStep: string;
  reminderItems: string[];
}

@Injectable({
  providedIn: 'root'
})
export class SpaDemoService {
  constructor(private http: HttpClient) {}

  getOverview(): Observable<SpaModuleOverview> {
    return this.http.get<SpaModuleOverview>('/api/spa-integration/overview');
  }

  createPracticeMessage(request: PracticeMessageRequest): Observable<PracticeMessageResponse> {
    return this.http.post<PracticeMessageResponse>('/api/spa-integration/practice-message', request);
  }
}
