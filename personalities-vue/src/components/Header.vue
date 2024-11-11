<script setup>
import { useI18n } from 'vue-i18n';
import {ref, watch} from 'vue';
import { RouterLink } from 'vue-router';
import Modal from '../../node_modules/bootstrap/js/src/modal'; 
import Register from '@/components/Register.vue';
import Login from '@/components/Login.vue';
import clipBoard from '@/assets/clipBoard.svg';
import filePerson from '@/assets/filePerson.svg';
import book from '@/assets/book.svg';
import personAdd from '@/assets/personAdd.svg';
import personCheck from '@/assets/personCheck.svg';
import personGear from '@/assets/personGear.svg';



let registered = ref(false);
let logged = ref(false);

const { t, locale } = useI18n();

const changeLanguage = (lang) => {
  locale.value = lang;
};

const goToConnection = () => {
  registered.value = true;
};

const closeModal = () => {
  logged.value = true;
}

watch(registered, (newVal) => {
  if (newVal) {
    const myModal =  Modal.getInstance(document.getElementById('register'));
    console.log(myModal);
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

watch(logged, (newVal) => {
  if(newVal){
    const myLoginModal = Modal.getInstance(document.getElementById('login'));
    if(myLoginModal){
      myLoginModal.hide();
    }
  }
})
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
          <ul class="navbar-nav me-auto gap-1">
            <li class="nav-item">
              <router-link to="/test" class="nav-link d-flex gap-1 justify-content-center">
                <img :src="clipBoard" width="20px">
                {{$t('header.test')}}
              </router-link>
            </li>
            <li class="nav-item">
              <a class="nav-link d-flex gap-1 justify-content-center" href="#">
                <img :src="filePerson" width="20px">
                {{$t('header.personalities')}}
              </a>
            </li>
            <li class="nav-item ">
              <a class="nav-link d-flex gap-1 justify-content-center" href="#">
                <img :src="book" width="20px">
                {{$t('header.documentation')}}
              </a>
            </li>
          </ul>
        
  
          <ul class="navbar-nav">
            <li v-if="!registered" class="nav-item ">
              <a class="nav-link d-flex gap-1 justify-content-center" href="#register" data-bs-toggle="modal" aria-expanded="false">
                <img :src="personAdd" width="20px">
                {{$t('header.registration')}}
              </a>
            </li>
            <li v-if="registered && !logged" class="nav-item">
              <a class="nav-link d-flex gap-1 justify-content-center" href="#login" data-bs-toggle="modal" aria-expanded="false">
                <img :src="personGear" width="20px">
                {{$t('header.authentication')}}
              </a>
            </li>
            <li v-if="logged" class="nav-item">
              <a class="nav-link d-flex gap-1 justify-content-center" href="#login" data-bs-toggle="modal" aria-expanded="false">
                <img :src="personCheck" width="20px">
                {{$t('header.welcome')}}   <!-- {{registration.username}} -->
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
    <Register @registered="goToConnection" v-if="!registered"/>
    <Login @logged="closeModal" v-if="registered && !logged" />
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
  }

  .dropdown-menu {
    z-index: 1070; 
  }
 
  </style> 