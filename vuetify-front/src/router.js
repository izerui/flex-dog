import Vue from 'vue'
import Router from 'vue-router'
import Main from "./layout/Main";

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'main',
            component: Main,
            redirect: '/home',
            children: [
                {
                    path: '/home',
                    name: 'home',
                    component: () => import('./views/Home.vue')
                },
                {
                    path: '/about',
                    name: 'about',
                    component: () => import('./views/About.vue')
                },
                {
                    path: '/demo',
                    name: 'demo',
                    component: () => import('./views/Demo.vue')
                }
            ]
        }
    ]
})
