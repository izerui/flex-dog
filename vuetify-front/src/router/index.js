import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        meta: { title: '主页' },
        component: () => import('../views/Home')
    },
    {
        path: '/dashboard',
        name: 'dashboard',
        meta: { title: '测试1' },
        component: () => import('../views/Dashboard/Layout'),
        children: [
            {
                path: 'indicators',
                name: 'dashboard.indicators',
                meta: { title: '测试1' },
                component: () => import('../views/Dashboard/Indicators')
            },
            {
                path: 'backup',
                name: 'dashboard.backup',
                meta: { title: '测试1' },
                component: () => import('../views/Dashboard/Backup')
            },
            {
                path: 'logs',
                name: 'dashboard.logs',
                meta: { title: '测试1' },
                component: () => import('../views/Dashboard/Logs')
            }
        ]
    },
    {
        path: '/home',
        name: 'home',
        meta: { title: 'Home' },
        component: () => import('../views/Home'),
    },
    {
        path: '/hello',
        name: 'hello',
        meta: { title: 'hello' },
        component: () => import('../views/HelloWorld'),
    },
    {
        path: '/ecs',
        name: 'ecs',
        meta: { title: '服务器列表' },
        component: () => import('../views/ecs/EcsList'),
    },
    {
        path: '/metrics',
        redirect: '/metrics/ecs',
        name: 'metrics',
        meta: { title: '数据监控' },
        component: () => import('../views/metrics/Layout'),
        children: [
            {
                path: '/metrics/ecs',
                name: 'ecs',
                meta: { title: '服务器监控' },
                component: () => import('../views/metrics/EcsMetricsList'),
            },
            {
                path: '/metrics/dbs',
                name: 'dbs',
                meta: { title: '数据库监控' },
                component: () => import('../views/metrics/DbMetricsList'),
            },
            {
                path: '/metrics/redis',
                name: 'redis',
                meta: { title: 'redis监控' },
                component: () => import('../views/metrics/RedisMetricsList'),
            }
        ]
    },
    {
        path: '/onlines',
        name: 'onlines',
        meta: { title: '在线用户' },
        component: () => import('../views/onlines/OnlineUserList'),
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})


router.afterEach((to, from, next) => {
    const rjson = {
        to,
        from,
        next
    }
    Vue.prototype.$events.dispatch(process.env.TYPE_ROUTER,rjson);
});


export default router;
