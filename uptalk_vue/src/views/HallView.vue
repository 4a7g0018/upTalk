<style scoped>
  @import '../assets/css/hall.css';
</style>
<template>
  <div>
    <form class="hall-container">
      <img src = "../assets/img/logo_icon.png"/>
      <br>
      <br>
      <button type="submit" class="btn">創立聊天室</button>
      <br>
      <button type="submit" class="btn">加入聊天室</button>
      <br>
      <button type="submit" class="btn">隨機開聊</button>
      <br>
      <button type="submit" class="btn">設定</button>
      <br>
      <button type="submit" class="exit-btn">離開</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "HallView"
}
</script>
