import Vue from 'vue'
import VueRouter from 'vue-router'
// import About from "@/views/About";

Vue.use(VueRouter)

const routes = [{
    path: '/work',
    component: () => import("../views/Work.vue"),
    children: [{path: "", redirect: 'add'}, {
        path: "add",
        component: () => import("../views/work/WorkInsert")
    }, {
        path: "*",
        component: () => import("../views/work/WorkList")
    }]
}, {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
}, {path: "*", redirect: '/work'}]

export default new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

