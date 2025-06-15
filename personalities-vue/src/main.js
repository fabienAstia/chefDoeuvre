import 'bootstrap/dist/css/bootstrap.min.css'
import './assets/main.scss'
import'./assets/main.css'
import{Button, Collapse, Dropdown, Toast} from 'bootstrap'
import { createApp } from 'vue'
import i18n from './locales'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'

const pinia = createPinia();

const app = createApp(App)
app.use(router)
app.use(i18n)
app.use(pinia)
app.mount('#app')
