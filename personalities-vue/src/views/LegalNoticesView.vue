<script setup>
import { useI18n } from 'vue-i18n';
const {t} = useI18n();
</script>

<template>
    <div class="wrapper">
        <h1 class="text-center mb-3">{{t('legalNotices.title')}}</h1>
        <div class="layout">
            <div><b>{{t('legalNotices.appName')}} :</b> Personalities</div>
            <div><b>{{t('legalNotices.editor')}} :</b> Fabien Astiasaran</div>
            <div><b>{{t('legalNotices.contextKey')}} :</b> {{t('legalNotices.contextValue')}}</div>
            <div><b>{{t('legalNotices.contact')}} :</b> <a href="mailto:fabien.astiasaran@gmail.com">fabien.astiasaran@gmail.com</a> </div>
            <div><b>{{t('legalNotices.host')}} :</b> OVH - France</div>
        </div>
    </div>
 

</template>

<style scoped>
.wrapper{
    display: flex;
    flex-direction: column;
    flex: 1;
    max-width: 790px;
}
.layout{
    margin: 0;
}
@media (min-width: 768px) {
    .layout{
         margin: auto 25%;
    }
}
</style>