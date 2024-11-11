<script setup>
import Header from '@/components/Header.vue';

</script>

<template>
  <Header/> 
  <div class="container">
    <router-view /> 
  </div> 


</template>

<style scoped>
.container{
  margin: 2rem auto; 
  max-width: 95%;
} 


</style>
