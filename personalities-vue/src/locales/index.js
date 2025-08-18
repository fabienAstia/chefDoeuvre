// src/i18n/index.js
import { watch } from 'vue';
import { createI18n } from 'vue-i18n';
import FR from './fr.json';
import EN from './en.json';
import PT from './pt.json';

// Détection de la langue du navigateur
const saved = localStorage.getItem('lang');
const browserLanguage = () => {
    if(saved) return saved
    if(navigator.language.startsWith('fr')) return 'fr'
    if(navigator.language.startsWith('en')) return 'en'
    if(navigator.language.startsWith('pt')) return 'pt'
    return 'fr'
}

//set initial <html lang="...">
document.documentElement.setAttribute('lang', browserLanguage);

const i18n = createI18n({
    legacy: false,
    locale: browserLanguage(),
    fallbackLocale: 'fr',
    messages: {
        fr: FR,
        en: EN,
        pt: PT
    }
});

//update <html lang="..."> when user changes language
watch(i18n.global.locale, (newLocale) => {
    document.documentElement.setAttribute('lang', newLocale);
    localStorage.setItem('lang', newLocale);
});

export default i18n;

