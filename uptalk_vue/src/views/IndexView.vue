<style scoped>
  @import '../assets/css/login.css';
</style>
<template>
  <body>
    <div id="bgimg">
      <img scr="bgimg" id="bgimg">
    </div>
    <div >
      <h1 class="typing">我是文字自動效</h1>
    </div>
    <div id="container">
      <form @submit.prevent="userLogin" class="login-container" > 
        <img src = "../assets/img/logo_icon.png"/>
        <transition name="fade">
          <div v-if="show">
            <br>
            <div class="textbox">
              <label>帳號</label>
              <input type="text" v-model="loginForm.username" placeholder="請輸入帳號"/>
            </div>
            <br>
            <div class="textbox">
              <label>密碼</label>
              <input type="password" v-model="loginForm.password" placeholder="請輸入密碼"/>
            </div>
            <br>
            <br>
            <br>
            <form style="width: 100%">
              <button type="submit" class="l-btn">登入</button>
            </form>
          </div>
          <br>         
        </transition>
        <transition name="fade">
          <div v-if="!show">
            <p>加入我們</p>
            <div class="textbox">
              <label>名稱</label>
              <input type="text" v-model="loginForm.username" placeholder="請輸入帳號"/>
            </div>
            <br>
            <div class="textboxeng">
              <label>Email</label>
              <input type="email" v-model="loginForm.password" placeholder="請輸入Email"/>
            </div>
            <br>
            <div class="textbox">
              <label>密碼</label>
              <input type="password" v-model="loginForm.password" placeholder="請輸入密碼"/>
            </div>
            <br>
            <div class="textbox">
              <label>密碼驗證</label>
              <input type="password" v-model="loginForm.password" placeholder="請再次輸入密碼"/>
            </div>
            <br>
            <br>
            <form style="width: 100%">
              <button type="submit" class="l-btn">註冊</button>
            </form>
          </div>
          <br>
        </transition>
        <a v-if="show" @click="show = !show">還沒有帳號嗎?加入我們</a>
        <br>
        <a v-if="!show" @click="show = !show" class="back-btn">﹤</a>
      </form>
    </div>
    
  </body>
</template>

<script>
export default {
  name: "IndexView",
  data() {  
    return {
      loginForm: {
        username: "",
        password: "",
      },
      responseResult: [],
      show: true,
    };
  },
  methods: {
   
   },
}
</script>