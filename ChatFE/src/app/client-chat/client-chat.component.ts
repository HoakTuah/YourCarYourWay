import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import SockJS from 'sockjs-client';
import { Client, Message } from '@stomp/stompjs';

/**
 * ClientChatComponent
 * Handles the client-side chat interface and communication with the backend via WebSocket (STOMP).
 */

@Component({
  selector: 'app-client-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './client-chat.component.html',
  styleUrls: ['./client-chat.component.scss']
})
export class ClientChatComponent implements OnInit {
  private stompClient!: Client;
  public messages: {sender: string, contenu: string, dateEnvoi?: string}[] = [];
  public messageContent: string = '';

  ngOnInit() {
    this.messages = [];
    this.connectToWebSocket();
  }

  /**
   * Establishes the WebSocket connection and subscribes to incoming messages
   */
  private connectToWebSocket() {
    this.stompClient = new Client({
      brokerURL: undefined, // Use SockJS fallback
      webSocketFactory: () => new SockJS('http://localhost:8080/chat'),
      reconnectDelay: 5000,
    });

    this.stompClient.onConnect = () => {
      // Subscribe to the public topic to receive messages
      this.stompClient.subscribe('/topic/public', (message: Message) => {
        const parsed = JSON.parse(message.body);
        this.messages.push(parsed);
      });
    };

    this.stompClient.activate();
  }

  /**
   * Sends the current message to the backend if not empty
   */
  public sendMessage() {
    if (this.messageContent && this.stompClient.connected) {
      const outgoingMessage = {
        contenu: this.messageContent,
        type: 'USER_TO_SUPPORT',
        statut: 'envoyé'
      };
      this.stompClient.publish({
        destination: '/app/chat.sendMessage',
        body: JSON.stringify(outgoingMessage)
      });
      this.messageContent = '';
    }
  }
}