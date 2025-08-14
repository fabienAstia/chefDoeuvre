<script setup>
import { useI18n } from 'vue-i18n';
const {t} = useI18n();
</script>

<template>
    <h1>{{t('privacyPolicy.title')}}</h1>
    <div>{{t('privacyPolicy.paragraph1')}}</div>
    <div class="mt-2">{{t('privacyPolicy.paragraph2')}}</div>
    <div class="mt-2">
        {{t('privacyPolicy.paragraph3')}}
        <a href="mailto:fabien.astiasaran@gmail.com">fabien.astiasaran@gmail.com</a>
    </div>
</template>

<style scoped>

</style>