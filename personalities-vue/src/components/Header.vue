<script setup>
import { useI18n } from 'vue-i18n';
import { RouterLink } from 'vue-router';
import {useSharedState} from '@/composables/useState'
import{computed} from 'vue';

import clipBoard from '@/assets/pictos/clipBoard.svg';
import filePerson from '@/assets/pictos/filePerson.svg';
import book from '@/assets/pictos/book.svg';
import personAdd from '@/assets/pictos/personAdd.svg';
import personCheck from '@/assets/pictos/personCheck.svg';
import personGear from '@/assets/pictos/personGear.svg';

const { t, locale } = useI18n();
const sharedState = useSharedState();

const changeLanguage = (lang) => {
  locale.value = lang;
};

const isLoggedIn = computed(() => {
  const token = localStorage.getItem('jwt');
  if (token) {
    sharedState.value = 'logged'; 
    return true;
  }
  return false;
});
</script>

<template>
     <nav id="navbar" class="navbar nav navbar-expand-sm" >
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand p-0">
          <img id="logo" src="../assets/images/logo.JPG" alt="logo" class="header">
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
  
        <div class="collapse navbar-collapse  fs-sm-6 fs-md-5 " id="navbarNavDropdown">
          <ul class="navbar-nav me-auto gap-1">
            <li class="nav-item">
              <router-link to="/answers" class="nav-link d-flex gap-1 justify-content-start fw-bold">
                <img :src="clipBoard" width="20px">
                {{$t('header.test')}}
              </router-link>
            </li>
            <li class="nav-item">
              <a class="nav-link d-flex gap-1 justify-content-start fw-bold " href="#">
                <img :src="filePerson" width="20px">
                {{$t('header.personalities')}}
              </a>
            </li>
            <li class="nav-item ">
              <a class="nav-link d-flex gap-1 justify-content-start fw-bold" href="#">
                <img :src="book" width="20px">
                {{$t('header.documentation')}}
              </a>
            </li>
          </ul>
        
  
          <ul class="navbar-nav ms-auto">
            <li v-if="!isLoggedIn && sharedState !== 'registered' && sharedState !=='logged'" class="nav-item ">
              <router-link to="/user-create" class="nav-link d-flex gap-1 justify-content-start fw-bold">
                <img :src="personAdd" width="20px">
                {{$t('header.registration')}}
              </router-link>
            </li>
            <li v-if="sharedState === 'registered' && sharedState !=='logged'" class="nav-item">
              <RouterLink to="/authenticate" class="nav-link d-flex gap-1 justify-content-start">
                <img :src="personGear" width="20px">
                {{$t('header.authentication')}}
              </RouterLink>
            </li>
            <li v-if="sharedState === 'logged'" class="nav-item">
              <a class="nav-link d-flex gap-1 justify-content-start" href="#login" aria-expanded="false">
                <img :src="personCheck" width="20px">
                {{$t('header.welcome')}}  
              </a>
            </li>

            <li class="nav-item dropdown d-flex">
              <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img v-if="locale === 'fr'" src="@/assets/images/frFlag.svg.png" width="35px" class="flag"> 
                <img v-if="locale === 'en'" src="@/assets/images/ukFlag.svg" width="35px" class="flag">
                <img v-if="locale === 'po'" src="@/assets/images/Flag_of_Portugal.svg.png" width="35px" class="flag">
              </a>
          
              <ul class="dropdown-menu dropdown-menu-end dropdown-menu-start" style="width: 20px;">
                <li>
                  <a class="dropdown-item d-flex align-items-start gap-2" href="#" @click="changeLanguage('fr')">
                    <img src="@/assets/images/frFlag.svg.png" width="20px" alt="french_flag" class="flag"> FR 
                  </a>
                </li>
                <li>
                  <a class="dropdown-item d-flex align-items-start gap-2" href="#" @click="changeLanguage('en')">
                    <img src="@/assets/images/ukFlag.svg" width="20px" alt="uk_flag" class="flag"> EN 
                  </a>
                </li>
                <li>
                  <a class="dropdown-item d-flex align-items-start gap-2" href="#" @click="changeLanguage('po')">
                    <img src="@/assets/images/Flag_of_Portugal.svg.png" width="20px" alt="portugal_flag" class="flag"> PO 
                  </a>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
</template>
  
  <style scoped>
  .header {
    height: 60px;
  }
  #logo {
    max-width: 100%;
    max-height: 100%;
  }
  .flag{
    border: 1px solid grey;
  }
  #navbar {
  position: sticky;
  top: 0;
  z-index: 1050; 
  background-color: #addee4;
  }
  .dropdown-menu {
    z-index: 1070; 
    background-color: #addee4; 
  }
 
  </style> 