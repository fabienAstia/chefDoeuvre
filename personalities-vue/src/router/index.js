import { createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(import.meta.env.VITE_BASE_URL),
    routes: [
        {
            path: "/",
            name:"home",
            component: () => import("@/views/Home.vue")
        },
        {
            path:"/login",
            name:"login",
            component: () => import("@/components/Register.vue")
        },
        {
            path:"/admin/questions",
            name:"admin",
            component: () => import("@/views/Admin.vue")
        },
        {
            path:"/test",
            name:"test",
            component: () => import("@/views/Test.vue")
        }
    ] 
})

export default router