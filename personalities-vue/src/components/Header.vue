<script setup lang="ts">
import {ref, watch} from 'vue';
import { RouterLink } from 'vue-router';
import Modal from '../../node_modules/bootstrap/js/src/modal'; 
import Register from '@/components/Register.vue';
import Login from '@/components/Login.vue'

let registered = ref(false);


const goToConnection = () => {
  registered.value = true;
};

watch(registered, (newVal) => {
  if (newVal) {
    const myModal =  Modal.getInstance(document.getElementById('register'));
    if(myModal) {
      myModal.hide();
    }
    const backdrops = document.querySelectorAll('.modal-backdrop');
    backdrops.forEach(backdrop => backdrop.remove());
      
  
    setTimeout(() => {
      const loginModal = new Modal(document.getElementById('login'));
      loginModal.show();
    }, 50);
  }
});
</script>

<template>
    <nav id="navbar" class="navbar navbar-expand-sm bg-body-tertiary fs-5" data-bs-theme="dark">
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand p-0">
          <img id="logo" src="../assets/images/logo.JPG" alt="logo" class="header">
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
  
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <router-link to="/test" class="nav-link active">Test</router-link>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Personnalités</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Articles</a>
            </li>
          </ul>
        
  
          <ul class="navbar-nav">
            <li v-if="!registered" class="nav-item ">
              <a class="nav-link" href="#register" data-bs-toggle="modal" aria-expanded="false">
                S'enregistrer
              </a>
            </li>
            <li v-else class="nav-item">
              <a class="nav-link" href="#login" data-bs-toggle="modal" aria-expanded="false">
                Se connecter
              </a>
            </li>

            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Langues<!-- Flag icon can be inserted here if needed -->
              </a>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">FR</a></li>
                <li><a class="dropdown-item" href="#">UK</a></li>
              </ul>
            </li>

          </ul>
        </div>
      </div>
    </nav>
    <Register @registered="goToConnection" v-if="!registered"/>
    <Login v-if="registered"/>
</template>
  
  <style scoped>
  .header {
    height: 60px;
  }
  #logo {
    max-width: 100%;
    max-height: 100%;
  }
  </style> 