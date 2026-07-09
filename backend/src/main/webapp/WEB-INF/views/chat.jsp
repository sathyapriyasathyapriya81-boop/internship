<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <title>PastelChat 💬</title>
  <link href="https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;700&display=swap" rel="stylesheet"/>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body { font-family: 'Nunito', sans-serif; background: #f3e8ff; height: 100vh; display: flex; }
    .sidebar { width: 300px; background: white; border-right: 1px solid #e9d5ff; display: flex; flex-direction: column; }
    .sidebar-header { padding: 20px; background: #a78bfa; color: white; font-size: 20px; font-weight: 700; }
    .user-info { padding: 12px 16px; background: #ede9fe; font-size: 13px; color: #6d28d9; }
    .search-box { padding: 10px; border-bottom: 1px solid #f3e8ff; }
    .search-box input { width: 100%; padding: 8px 12px; border: 1px solid #e9d5ff; border-radius: 20px; font-size: 14px; outline: none; }
    .contacts-list { flex: 1; overflow-y: auto; }
    .contact-item { display: flex; align-items: center; gap: 10px; padding: 12px 16px; cursor: pointer; border-bottom: 1px solid #faf5ff; transition: background 0.2s; }
    .contact-item:hover, .contact-item.active { background: #f3e8ff; }
    .avatar { width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: white; font-weight: 700; font-size: 16px; flex-shrink: 0; }
    .contact-name { font-weight: 600; font-size: 14px; color: #374151; }
    .contact-status { font-size: 12px; color: #9ca3af; }
    .logout-btn { padding: 12px 16px; background: #fce7f3; color: #be185d; border: none; cursor: pointer; font-size: 14px; font-weight: 600; width: 100%; }
    .chat-area { flex: 1; display: flex; flex-direction: column; }
    .chat-header { padding: 16px 20px; background: white; border-bottom: 1px solid #e9d5ff; display: flex; align-items: center; gap: 12px; }
    .chat-header-name { font-weight: 700; font-size: 16px; color: #374151; }
    .chat-header-status { font-size: 12px; color: #10b981; }
    .messages-box { flex: 1; overflow-y: auto; padding: 20px; display: flex; flex-direction: column; gap: 12px; background: #faf5ff; }
    .msg { max-width: 65%; padding: 10px 14px; border-radius: 18px; font-size: 14px; line-height: 1.5; word-break: break-word; }
    .msg.sent { align-self: flex-end; background: #a78bfa; color: white; border-radius: 18px 18px 4px 18px; }
    .msg.received { align-self: flex-start; background: white; color: #374151; border-radius: 18px 18px 18px 4px; box-shadow: 0 1px 4px rgba(0,0,0,0.08); }
    .msg-time { font-size: 11px; margin-top: 4px; opacity: 0.7; }
    .input-row { padding: 12px 16px; background: white; border-top: 1px solid #e9d5ff; display: flex; align-items: center; gap: 8px; }
    .input-row input[type=text] { flex: 1; padding: 10px 16px; border: 1px solid #e9d5ff; border-radius: 24px; font-size: 14px; outline: none; font-family: 'Nunito', sans-serif; }
    .input-row input[type=text]:focus { border-color: #a78bfa; }
    .send-btn { background: #a78bfa; border: none; width: 38px; height: 38px; border-radius: 50%; cursor: pointer; color: white; font-size: 18px; }
    .send-btn:hover { background: #7c3aed; }
    .attach-btn { background: #f3e8ff; border: none; width: 34px; height: 34px; border-radius: 50%; cursor: pointer; font-size: 16px; flex-shrink: 0; }
.attach-btn:hover { background: #e9d5ff; }
.attach-btn.recording { background: #fee2e2; animation: pulse 1s infinite; }
@keyframes pulse { 0%,100%{opacity:1} 50%{opacity:0.5} }
    .empty-chat { flex: 1; display: flex; flex-direction: column; align-items: center; justify-content: center; color: #c4b5fd; font-size: 18px; gap: 10px; }
    .auth-screen { width: 100vw; height: 100vh; display: flex; align-items: center; justify-content: center; background: linear-gradient(135deg, #f3e8ff, #fce7f3); }
    .auth-box { background: white; padding: 40px; border-radius: 20px; box-shadow: 0 8px 32px rgba(167,139,250,0.2); width: 380px; }
    .auth-box h2 { color: #7c3aed; font-size: 24px; margin-bottom: 6px; }
    .auth-box p { color: #9ca3af; font-size: 14px; margin-bottom: 24px; }
    .auth-box input { width: 100%; padding: 11px 14px; border: 1.5px solid #e9d5ff; border-radius: 10px; font-size: 14px; margin-bottom: 12px; outline: none; font-family: 'Nunito', sans-serif; }
    .auth-box input:focus { border-color: #a78bfa; }
    .auth-box button { width: 100%; padding: 12px; border: none; border-radius: 10px; font-size: 15px; font-weight: 700; cursor: pointer; margin-bottom: 8px; font-family: 'Nunito', sans-serif; }
    .btn-primary { background: #a78bfa; color: white; }
    .btn-secondary { background: #f3e8ff; color: #7c3aed; }
    .err { color: #ef4444; font-size: 13px; margin-top: 4px; min-height: 18px; }
    .toggle-link { text-align: center; font-size: 13px; color: #9ca3af; margin-top: 8px; cursor: pointer; }
    .toggle-link span { color: #7c3aed; font-weight: 700; }
    .hidden { display: none !important; }
  </style>
</head>
<body>

<div class="auth-screen" id="authScreen">
  <div class="auth-box">
    <div id="loginForm">
      <h2>Welcome back 👋</h2>
      <p>Login to PastelChat</p>
      <input type="email" id="loginEmail" placeholder="Email address"/>
      <input type="password" id="loginPassword" placeholder="Password"/>
      <div class="err" id="loginErr"></div>
      <button class="btn-primary" onclick="doLogin()">Login</button>
      <div class="toggle-link" onclick="showRegister()">Don't have an account? <span>Register</span></div>
    </div>
    <div id="registerForm" class="hidden">
      <h2>Create account ✨</h2>
      <p>Join PastelChat today</p>
      <input type="text" id="regName" placeholder="Full name"/>
      <input type="email" id="regEmail" placeholder="Email address"/>
      <input type="text" id="regPhone" placeholder="Phone (10 digits)"/>
      <input type="password" id="regPassword" placeholder="Password (Hello@1234 format)"/>
      <div class="err" id="registerErr"></div>
      <button class="btn-primary" onclick="doRegister()">Create Account</button>
      <div class="toggle-link" onclick="showLogin()">Already have an account? <span>Login</span></div>
    </div>
  </div>
</div>

<div id="chatScreen" class="hidden" style="display:flex;width:100%;height:100vh;">
  <div class="sidebar">
    <div class="sidebar-header">💬 PastelChat</div>
    <div class="user-info" id="currentUserInfo"></div>
    <div class="search-box">
      <input type="text" placeholder="Search contacts..." oninput="filterContacts(this.value)"/>
    </div>
    <div class="contacts-list" id="contactsList"></div>
    <button class="logout-btn" onclick="doLogout()">🚪 Logout</button>
  </div>
  <div class="chat-area">
    <div class="empty-chat" id="emptyChat">
      <div style="font-size:48px;">💜</div>
      <div>Select a contact to start chatting</div>
    </div>
    <div id="activeChat" class="hidden" style="display:flex;flex-direction:column;height:100%;">
      <div class="chat-header">
        <div class="avatar" id="chatAvatar" style="width:36px;height:36px;font-size:14px;">?</div>
        <div>
          <div class="chat-header-name" id="chatName">-</div>
          <div class="chat-header-status" id="chatStatus">-</div>
        </div>
      </div>
      <div class="messages-box" id="messagesBox"></div>
     <div class="input-row">
  <input type="file" id="imageInput" accept="image/*" style="display:none" onchange="sendFile(this,'image')"/>
  <input type="file" id="videoInput" accept="video/*" style="display:none" onchange="sendFile(this,'video')"/>
  <input type="file" id="docInput" accept=".pdf,.doc,.docx,.txt,.xls,.xlsx" style="display:none" onchange="sendFile(this,'file')"/>
  <button class="attach-btn" onclick="document.getElementById('imageInput').click()" title="Send Image">🖼️</button>
  <button class="attach-btn" onclick="document.getElementById('videoInput').click()" title="Send Video">🎥</button>
  <button class="attach-btn" onclick="document.getElementById('docInput').click()" title="Send Document">📄</button>
  <button class="attach-btn" id="voiceBtn" onclick="toggleVoice()" title="Voice Message">🎤</button>
  <input type="text" id="msgInput" placeholder="Type a message..." onkeydown="if(event.key==='Enter')sendMsg()"/>
  <button class="send-btn" onclick="sendMsg()">➤</button>
</div>
    </div>
  </div>
</div>

<script>
  const API = 'http://localhost:8080/api';
  let me = null;
  let allUsers = [];
  let activeUser = null;
  let pollInterval = null;

  function showRegister() {
    document.getElementById('loginForm').classList.add('hidden');
    document.getElementById('registerForm').classList.remove('hidden');
  }
  function showLogin() {
    document.getElementById('registerForm').classList.add('hidden');
    document.getElementById('loginForm').classList.remove('hidden');
  }

  async function doLogin() {
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;
    const res = await fetch(API + '/users/login', {
      method: 'POST', headers: {'Content-Type':'application/json'},
      body: JSON.stringify({email, password})
    });
    const data = await res.json();
    if (!data.success) { document.getElementById('loginErr').textContent = data.errors ? data.errors[0] : 'Login failed.'; return; }
    me = data.user;
    enterChat();
  }

  async function doRegister() {
    const name = document.getElementById('regName').value;
    const email = document.getElementById('regEmail').value;
    const phone = document.getElementById('regPhone').value;
    const password = document.getElementById('regPassword').value;
    const res = await fetch(API + '/users/register', {
      method: 'POST', headers: {'Content-Type':'application/json'},
      body: JSON.stringify({name, email, phone, password})
    });
    const data = await res.json();
    if (!data.success) { document.getElementById('registerErr').textContent = data.errors ? data.errors[0] : 'Registration failed.'; return; }
    me = data.user;
    enterChat();
  }

  async function doLogout() {
    if (me) await fetch(API + '/users/' + me.id + '/logout', {method:'POST'});
    me = null; activeUser = null;
    clearInterval(pollInterval);
    document.getElementById('chatScreen').classList.add('hidden');
    document.getElementById('authScreen').classList.remove('hidden');
  }

  async function enterChat() {
    document.getElementById('authScreen').classList.add('hidden');
    document.getElementById('chatScreen').classList.remove('hidden');
    document.getElementById('currentUserInfo').textContent = '👤 ' + me.name;
    await loadContacts();
    pollInterval = setInterval(async () => {
      await loadContacts();
      if (activeUser) await loadMessages();
    }, 3000);
  }

  async function loadContacts() {
    const res = await fetch(API + '/users');
    allUsers = await res.json();
    renderContacts(allUsers);
  }

  function renderContacts(users) {
    const list = document.getElementById('contactsList');
    list.innerHTML = '';
    users.filter(u => u.id !== me.id).forEach(u => {
      const div = document.createElement('div');
      div.className = 'contact-item' + (activeUser && activeUser.id === u.id ? ' active' : '');
      div.onclick = () => openChat(u);
      div.innerHTML =
        '<div class="avatar" style="background:' + u.avatarColor + '">' + u.name.charAt(0).toUpperCase() + '</div>' +
        '<div><div class="contact-name">' + u.name + '</div>' +
        '<div class="contact-status">' + (u.online ? '🟢 Online' : '⚫ Offline') + '</div></div>';
      list.appendChild(div);
    });
  }

  function filterContacts(query) {
    renderContacts(allUsers.filter(u => u.id !== me.id && u.name.toLowerCase().includes(query.toLowerCase())));
  }

  async function openChat(user) {
    activeUser = user;
    document.getElementById('messagesBox').innerHTML = '';
    document.getElementById('emptyChat').classList.add('hidden');
    document.getElementById('activeChat').classList.remove('hidden');
    document.getElementById('chatAvatar').textContent = user.name.charAt(0).toUpperCase();
    document.getElementById('chatAvatar').style.background = user.avatarColor;
    document.getElementById('chatName').textContent = user.name;
    document.getElementById('chatStatus').textContent = user.online ? '🟢 Online' : '⚫ Offline';
    renderContacts(allUsers);
    await loadMessages();
  }

  function formatTime(sentAt) {
    try {
      if (Array.isArray(sentAt)) {
        return new Date(sentAt[0], sentAt[1]-1, sentAt[2], sentAt[3], sentAt[4]).toLocaleTimeString([], {hour:'2-digit', minute:'2-digit'});
      }
      return new Date(sentAt).toLocaleTimeString([], {hour:'2-digit', minute:'2-digit'});
    } catch(e) { return ''; }
  }

  async function loadMessages() {
    if (!activeUser) return;
    const talkingTo = activeUser.id;
    const res = await fetch(API + '/messages/conversation?userA=' + me.id + '&userB=' + talkingTo);
    const msgs = await res.json();
    if (!Array.isArray(msgs)) return;
    if (activeUser.id !== talkingTo) return;
    const box = document.getElementById('messagesBox');
    box.innerHTML = '';
    msgs.forEach(m => {
      const div = document.createElement('div');
      div.className = 'msg ' + (m.senderId === me.id ? 'sent' : 'received');
      let html = '';
      if (m.content) html += '<div>' + m.content + '</div>';
if (m.mediaType === 'image') html += '<img src="' + m.mediaUrl + '" style="max-width:200px;border-radius:8px;display:block;margin-top:4px;"/>';
if (m.mediaType === 'video') html += '<video src="' + m.mediaUrl + '" controls style="max-width:200px;border-radius:8px;display:block;margin-top:4px;"></video>';
if (m.mediaType === 'file' && m.mediaUrl && m.mediaUrl.includes('.webm')) html += '<audio src="' + m.mediaUrl + '" controls style="margin-top:4px;"></audio>';
if (m.mediaType === 'file' && m.mediaUrl && !m.mediaUrl.includes('.webm')) html += '<a href="' + m.mediaUrl + '" target="_blank" style="color:inherit;display:block;margin-top:4px;">📄 ' + (m.mediaName || 'File') + '</a>';
      html += '<div class="msg-time">' + formatTime(m.sentAt) + '</div>';
      div.innerHTML = html;
      box.appendChild(div);
    });
    box.scrollTop = box.scrollHeight;
  }

  async function sendMsg() {
    const input = document.getElementById('msgInput');
    const text = input.value.trim();
    if (!text || !activeUser) return;
    input.value = '';
    const fd = new FormData();
    fd.append('senderId', me.id);
    fd.append('receiverId', activeUser.id);
    fd.append('content', text);
    await fetch(API + '/messages/send', {method:'POST', body:fd});
    await loadMessages();
  }
  async function sendFile(input, type) {
    if (!input.files[0] || !activeUser) return;
    const fd = new FormData();
    fd.append('senderId', me.id);
    fd.append('receiverId', activeUser.id);
    fd.append('file', input.files[0]);
    await fetch(API + '/messages/send', {method:'POST', body:fd});
    input.value = '';
    await loadMessages();
}

let mediaRecorder = null;
let audioChunks = [];
let isRecording = false;

async function toggleVoice() {
    const btn = document.getElementById('voiceBtn');
    if (!isRecording) {
        try {
            const stream = await navigator.mediaDevices.getUserMedia({audio: true});
            mediaRecorder = new MediaRecorder(stream);
            audioChunks = [];
            mediaRecorder.ondataavailable = e => audioChunks.push(e.data);
            mediaRecorder.onstop = async () => {
                const blob = new Blob(audioChunks, {type: 'audio/webm'});
                const file = new File([blob], 'voice_' + Date.now() + '.webm', {type: 'audio/webm'});
                const fd = new FormData();
                fd.append('senderId', me.id);
                fd.append('receiverId', activeUser.id);
                fd.append('file', file);
                await fetch(API + '/messages/send', {method:'POST', body:fd});
                await loadMessages();
                stream.getTracks().forEach(t => t.stop());
            };
            mediaRecorder.start();
            isRecording = true;
            btn.textContent = '⏹️';
            btn.classList.add('recording');
        } catch(e) {
            alert('Microphone access denied!');
        }
    } else {
        mediaRecorder.stop();
        isRecording = false;
        btn.textContent = '🎤';
        btn.classList.remove('recording');
    }
}
</script>
</body>
</html>