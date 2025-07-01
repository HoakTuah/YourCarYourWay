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
  public messages: {from: string, content: string}[] = [];
  public messageContent: string = '';

  ngOnInit() { this.connect(); }

  connect() {
    this.stompClient = new Client({
      brokerURL: undefined,
      webSocketFactory: () => new SockJS('http://localhost:8080/chat'),
      reconnectDelay: 5000,
    });
    this.stompClient.onConnect = () => {
      this.stompClient.subscribe('/topic/public', (message: Message) => {
        this.messages.push(JSON.parse(message.body));
      });
    };
    this.stompClient.activate();
  }

  sendMessage() {
    if (this.messageContent && this.stompClient.connected) {
      this.stompClient.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify({from: 'Support', content: this.messageContent})
      });
      this.messageContent = '';
    }
  }
}