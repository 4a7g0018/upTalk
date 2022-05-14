<style scoped>
  @import '../assets/css/login.css';
</style>
<template>
  <body>
    <div>
      <div id="bgimg">
        <img scr="bgimg" id="bgimg">
      </div>
      <form @submit.prevent="userLogin" class="login-container" >
        <img src = "../assets/img/logo_icon.png"/>
        <transition name="fade">
          <div v-if="show">
            <div class="textbox">
              <label class="label_title">帳號</label>
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
              <button type="submit" class="l-button">登入</button>
            </form>
          </div>
          <br>
          
        </transition>
        <a @click="show = !show">還沒有帳號嗎?加入我們</a>
        <transition name="fade">
          <div v-if="!show">
            <div class="textbox">
              <label class="label_title">名稱</label>
              <input type="text" v-model="loginForm.username" placeholder="請輸入帳號"/>
            </div>
            <br>
            <div class="textbox">
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
            <br>
            <form style="width: 100%">
              <button type="submit" class="l-button">註冊</button>
            </form>
          </div>
        </transition>
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