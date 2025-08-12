// src/i18n/index.js
import { createI18n } from 'vue-i18n';
import FR from './fr.json';
import EN from './en.json';
import PO from './po.json';

// Détection de la langue du navigateur
const browserLanguage = navigator.language.startsWith('fr') ? 'fr' :
                        navigator.language.startsWith('en') ? 'en' : 'po';

//set initial <html lang="...">
document.documentElement.setAttribute('lang', browserLanguage);

const i18n = createI18n({
    legacy: false,
    locale: browserLanguage,
    fallbackLocale: 'fr',
    messages: {
        fr: FR,
        en: EN,
        po: PO
    }
});

//update <html lang="..."> when user changes language
i18n.global.localeWatcher = i18n.global.watchLocale?.((newLocale) => {
    document.documentElement.setAttribute('lang', newLocale);
});

export default i18n;

