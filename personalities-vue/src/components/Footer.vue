<script setup>
import { useI18n } from 'vue-i18n';
const {t} = useI18n();
</script>

<template>
    <nav id="footer" class="navbar navbar-expand-sm py-2 py-sm-0" >
        <div class="container-fluid justify-content-center flex-column flex-sm-row">
          <span class="p-1">© 2025 Personalities </span>
          <router-link to="/legalNotices" class="p-1" :aria-label="t('legalNotices.title')">
            <span>{{ t('legalNotices.title') }}</span>
          </router-link>
          <router-link to="/privacyPolicy" class="p-1" :aria-label="t('privacyPolicy.title')">
            <span> {{t('privacyPolicy.title')}}</span>
          </router-link>
      </div>
    </nav>
</template>

<style scoped>
#footer{
    bottom: 0;
    z-index: 1250; 
    background-color: #addee4;
}

</style>