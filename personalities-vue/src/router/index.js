import { createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_BASE_URL),
    routes: [
        {
            path: "/",
            name:"home",
            component: () => import("@/views/HomeView.vue")
        },
        {
            path:"/user-create",
            name:"user-create",
            component: () => import("@/views/UserCreateView.vue")
        },
        {
            path:"/authenticate",
            name:"authenticate",
            component: () => import("@/views/UserAuthenticateView.vue")
        },
        {
            path:"/admin/questions",
            name:"admin",
            component: () => import("@/views/AdminView.vue")
        },
        {
            path:"/test",
            name:"test",
            component: () => import("@/views/Test.vue")
        }, 
        {
            path:"/:pathMatch(.*)*", component: () => import('@/views/404.vue')
        }
    ] 
})

export default router