
import 'bootstrap/dist/js/bootstrap';
import 'bootstrap/dist/css/bootstrap.css'

import { createApp } from 'vue'
import { createI18n } from 'vue-i18n'
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

const app = createApp(App)
app.use(router)
app.use(i18n)
app.mount('#app')
