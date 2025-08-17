<script setup>
import { useI18n } from 'vue-i18n';
const {t} = useI18n();
</script>

<template>
     <div class="wrapper">
        <h1 class="text-center mb-3">{{t('privacyPolicy.title')}}</h1>
        <div class="layout">
            <div>{{t('privacyPolicy.paragraph1')}}</div>
            <div class="mt-2">{{t('privacyPolicy.paragraph2')}}</div>
            <div class="mt-2">
                {{t('privacyPolicy.paragraph3')}}
                <a href="mailto:fabien.astiasaran@gmail.com">fabien.astiasaran@gmail.com</a>
            </div>
        </div>
     </div>
   
 
</template>

<style scoped>
.wrapper{
    display: flex;
    flex-direction: column;
    flex: 1;
    max-width: 9000px;
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