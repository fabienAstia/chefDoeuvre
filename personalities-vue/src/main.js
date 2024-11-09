
import 'bootstrap/dist/js/bootstrap';
import 'bootstrap/dist/css/bootstrap.css'

import { createApp } from 'vue'
import { createI18n } from 'vue-i18n'

import App from './App.vue'
import router from './router'

// Détection de la langue du navigateur
// const browserLanguage = navigator.language.startsWith('fr') ? 'fr' : 'en';

// Configuration de Vue I18n
// const i18n = createI18n({
//   locale: browserLanguage,
//   fallbackLocale: 'en',
//   messages: {
//     en,
//     fr
//   }
// });

const app = createApp(App)
app.use(router)
// app.use(i18n)
app.mount('#app')
