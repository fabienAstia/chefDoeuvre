import { createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: "/",
            name:"home",
            component: () => import("@/views/Admin.vue")
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