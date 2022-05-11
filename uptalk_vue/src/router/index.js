import Vue from 'vue'
import VueRouter from 'vue-router'
import Index from '../views/IndexView.vue'

Vue.use(VueRouter)

const routes = [
    {
        //登入&註冊
        path: '/',
        name: 'index',
        component: Index
    },
    {
        //大廳
        path: '/hallView',
        name: 'hallView',
        component: () => import(/* webpackChunkName: "hall" */'../views/HallView.vue')
    },
    {
        //聊天室
        path: '/room/:roomId?',
        name: 'room',
        component: () => import(/* webpackChunkName: "room" */'../views/RoomView.vue')
    },
    {
        //信箱驗證
        path: '/mail/:authenticationCode?',
        name: 'MailAuth',
        component: () => import(/* webpackChunkName: " mail" */ '../views/MailAuthentication.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
