<script setup>
import Header from '@/components/Header.vue';
import { useRouter } from 'vue-router'
const route = useRouter()
</script>

<template>
  <Header/> 
  <div class="general_container" :class="{no_layout: route.name === 'home'}">
    <router-view /> 
  </div> 


</template>

<style scoped>
.general_container{
  margin: 2rem auto; 
  max-width: 95%;
} 
.no_layout{
  margin: 0;
  padding: 0;
  max-width: none;
}

</style>
