import { Routes } from '@angular/router';
import { ClientChatComponent } from './client-chat/client-chat.component';
import { SupportChatComponent } from './support-chat/support-chat.component';

export const routes: Routes = [
  { path: 'client', component: ClientChatComponent },
  { path: 'support', component: SupportChatComponent },
  { path: '', redirectTo: 'client', pathMatch: 'full' }
];