import 'bootstrap/dist/css/bootstrap.min.css'
import './assets/main.scss'
import'./assets/main.css'
import{Button, Collapse, Dropdown, Toast} from 'bootstrap'
import { createApp } from 'vue'
import { createI18n } from 'vue-i18n'
import {createPinia} from 'pinia'

import FR from './locales/fr.json'
import EN from './locales/en.json'
import PO from './locales/po.json'

import App from './App.vue'
import router from './router'

// Détection de la langue du navigateur
const browserLanguage = navigator.language.startsWith('fr') ? 'fr' :
                        navigator.language.startsWith('en') ? 'en' : 'po'


const i18n = createI18n({
    legacy:false,
    locale: browserLanguage,
    fallbackLocale: 'fr',
    messages: {
        fr: FR,
        en: EN,
        po: PO
    }
});

const pinia = createPinia();

const app = createApp(App)
app.use(router)
app.use(i18n)
app.use(pinia)
app.mount('#app')
