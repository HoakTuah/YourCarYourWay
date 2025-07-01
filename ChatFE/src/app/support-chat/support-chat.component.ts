import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import SockJS from 'sockjs-client';
import { Client, Message } from '@stomp/stompjs';

@Component({
  selector: 'app-support-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './support-chat.component.html',
  styleUrls: ['./support-chat.component.scss']
})
export class SupportChatComponent implements OnInit {
  private stompClient!: Client;
  public messages: {sender: string, contenu: string}[] = [];
  public messageContent: string = '';

  ngOnInit() {
    this.messages = [];
    this.connect();
  }

  connect() {
    this.stompClient = new Client({
      brokerURL: undefined,
      webSocketFactory: () => new SockJS('http://localhost:8080/chat'),
      reconnectDelay: 5000,
    });
    this.stompClient.onConnect = () => {
      this.stompClient.subscribe('/topic/public', (message: Message) => {
        console.log('Message reçu du backend :', message.body);
        this.messages.push(JSON.parse(message.body));
      });
    };
    this.stompClient.activate();
  }

  sendMessage() {
    console.log('Message envoyé au backend :', {from: 'Support', content: this.messageContent});
    if (this.messageContent && this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify({
          contenu: this.messageContent,
          type: 'SUPPORT_TO_USER',
          statut: 'envoyé'
        })
      });
      this.messageContent = '';
    }
  }
}