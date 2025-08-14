<script setup>
import { useI18n } from 'vue-i18n';
const {t} = useI18n();
</script>

<template>
    <h1>{{t('legalNotices.title')}}</h1>
    <div><b>{{t('legalNotices.appName')}} :</b> Personalities</div>
    <div><b>{{t('legalNotices.editor')}} :</b> Fabien Astiasaran</div>
    <div><b>{{t('legalNotices.contextKey')}} :</b> {{t('legalNotices.contextValue')}}</div>
    <div><b>{{t('legalNotices.contact')}} :</b> <a href="mailto:fabien.astiasaran@gmail.com">fabien.astiasaran@gmail.com</a> </div>
    <div><b>{{t('legalNotices.host')}} :</b> OVH - France</div>
</template>

<style scoped>

</style>