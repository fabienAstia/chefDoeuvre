import { createRouter, createWebHistory} from "vue-router";
import { adminRole, userRole } from "@/guards/auth-guard";

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
            path:"/questions",
            name:"admin",
            component: () => import("@/views/AdminView.vue"),
            beforeEnter: adminRole
        },
        {
            path:"/answers",
            name:"answers",
            component: () => import("@/views/Test.vue") //need to rename component
        }, 
        {
            path:"/:pathMatch(.*)*", component: () => import('@/views/404.vue')
        }
    ] 
})

export default router