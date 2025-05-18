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
import identify_yourself from '@/assets/pictos/identify_yourself.svg'
import { jwtDecode } from 'jwt-decode';

const { t, locale } = useI18n();
const sharedState = useSharedState();

const changeLanguage = (lang) => {
  locale.value = lang;
};

const isLoggedIn = computed(() => {
  const token = localStorage.getItem('jwt');
  if (token) {
    const decodedToken = jwtDecode(token);
    const current_time = new Date().getTime() / 1000;
    if(current_time < decodedToken.exp){
      sharedState.value = 'logged'; 
      return true;
    }
  }
  return false;
});

const clearLocalStorage = () => {
  localStorage.clear();
}
</script>

<template>
     <nav id="navbar" class="navbar nav navbar-expand-sm" >
      <div class="container-fluid">
        <router-link to="/" class="navbar-brand p-0">
          <img id="logo" src="../assets/images/autres-images/logo.png" alt="logo" class="header">
        </router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
  
        <div class="collapse navbar-collapse  fs-md-6 fs-lg-5 " id="navbarNavDropdown">
          <ul class="navbar-nav me-auto gap-1">
            <li class="nav-item hover">
              <router-link to="/answers" class=" nav-link d-flex gap-1 justify-content-start fw-bold">
                <img :src="clipBoard" width="20px">
                {{$t('header.test')}}
              </router-link>
            </li>
            <li class="nav-item hover">
              <router-link to="/personalities" class="nav-link d-flex gap-1 justify-content-start fw-bold">
                <img :src="filePerson" width="20px">
                {{$t('header.personalities')}}
              </router-link>
            </li>
            <li class="nav-item hover">
              <a class="nav-link d-flex gap-1 justify-content-start fw-bold" href="#">
                <img :src="book" width="20px">
                {{$t('header.documentation')}}
              </a>
            </li>
          </ul>
        
          

          <ul class="navbar-nav ms-auto">

            <li v-if="sharedState !=='logged'" class="nav-item dropdown d-flex">
              <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <li class="nav-item hover">
                  <span class="nav-link d-flex gap-1 justify-content-start fw-bold">
                    <img :src="identify_yourself" width="20px">
                    {{$t('header.account')}}
                  </span>
                </li>
              </a>
              
              <ul class="dropdown-menu dropdown-menu-end dropdown-menu-start" style="width: 20px;">
                <li class="nav-item hover">
                  <router-link to="/user-create" class="nav-link d-flex gap-1 justify-content-start fw-bold">
                    <img :src="personAdd" width="20px">
                    {{$t('header.registration')}}
                  </router-link>
                </li>
                <li class="nav-item hover">
                  <RouterLink to="/authenticate" class="nav-link d-flex gap-1 justify-content-start fw-bold">
                    <img :src="personGear" width="20px">
                    {{$t('header.authentication')}}
                  </RouterLink>
                </li>
              </ul>
            </li>

            <li v-if="sharedState ==='logged'" class="nav-item dropdown d-flex">
              <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <li class="nav-item hover">
                  <span class="nav-link d-flex gap-1 justify-content-start fw-bold">
                    <img :src="personCheck" width="20px">{{$t('header.welcome')}} 
                  </span>
                </li>
              
              </a>
              <ul class="dropdown-menu dropdown-menu-end dropdown-menu-start" style="width: 20px;">
                <li class="hover nav-item fw-bold gap-1">
                  <a @click="clearLocalStorage()" class="nav-link">{{$t('header.logout')}}</a>
                </li>
              </ul>
            </li>
           
            <li class="nav-item dropdown d-flex">
              <a class="nav-link dropdown-toggle d-flex align-items-center gap-2" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img v-if="locale === 'fr'" src="@/assets/images/frFlag.svg.png" width="35px" class="flag"> 
                <img v-if="locale === 'en'" src="@/assets/images/ukFlag.svg" width="35px" class="flag">
                <img v-if="locale === 'po'" src="@/assets/images/Flag_of_Portugal.svg.png" width="35px" class="flag">
              </a>
          
              <ul class="dropdown-menu dropdown-menu-end dropdown-menu-start" style="width: 20px;">
                <li>
                  <a class="dropdown-item d-flex align-items-center gap-2" href="#" @click="changeLanguage('fr')">
                    <img src="@/assets/images/frFlag.svg.png" width="20px" alt="french_flag" class="flag"> FR 
                  </a>
                </li>
                <li>
                  <a class="dropdown-item d-flex align-items-center gap-2" href="#" @click="changeLanguage('en')">
                    <img src="@/assets/images/ukFlag.svg" width="20px" alt="uk_flag" class="flag"> EN 
                  </a>
                </li>
                <li>
                  <a class="dropdown-item d-flex align-items-center gap-2" href="#" @click="changeLanguage('po')">
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
  z-index: 1250; 
  background-color: #addee4;
  }
  .dropdown-menu {
    z-index: 1070; 
    background-color: #addee4; 
  }
  .hover :hover{
    background-color: #0077b6;
    color: white;
    cursor: pointer;
  }
 
  </style> 