import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'home',
        meta: { title: '主页' },
        redirect: '/console',
        // component: () => import('../views/Home')
    },
    {
        path: '/console',
        name: 'console',
        meta: { title: '访问日志' },
        component: () => import('../views/console/ConsoleLogs'),
    },
    {
        path: '/files',
        name: 'files',
        meta: { title: '服务列表' },
        component: () => import('../views/file/FileList'),
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
    {
        path: '/mrp',
        name: 'mrp',
        meta: { title: 'MRP数据' },
        component: () => import('../views/mrp/DemandList'),
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
