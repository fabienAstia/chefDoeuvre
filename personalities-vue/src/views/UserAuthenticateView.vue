<script setup>
import {ref, useTemplateRef} from 'vue';
import { useI18n } from 'vue-i18n';
import router from '@/router';
import eye from '@/assets/pictos/eye.svg';
import eyeSlash from '@/assets/pictos/eyeSlash.svg';
import {useSharedState} from '@/composables/useState'
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';
import { formatAlert } from '@/composables/useMessageFormatter';
import AlertModal from '@/components/Alert.vue'

const{t} = useI18n();
const sharedState = useSharedState();
const apiUrl = import.meta.env.VITE_API_URL

const modal = useTemplateRef('modal')
const showMessage = (msg) => {
    modal.value.openModal()
    modal.value.alertTxt = formatAlert(msg).message
}

const userCredentials = ref({username:'', password:''});

const visibility = ref(false);
const switchVisibility = () => {
  visibility.value = !visibility.value;
  const passwordField = document.querySelector('#password');
  if (passwordField.getAttribute('type') === 'password') {
    passwordField.setAttribute('type', 'text');
  } else {
    passwordField.setAttribute('type', 'password');
  } 
}

const authenticate = async() => {
  try{
    const response = await axios.post(`${apiUrl}/users/authenticate`, userCredentials.value);
      let jwt = response.data;
      localStorage.setItem('jwt', jwt); 
      showMessage(t('success.login'))
      sharedState.value = 'logged';
      const decodedToken = jwtDecode(jwt);
      if(decodedToken.role === 'ROLE_ADMIN'){
        await router.push(`questions`);
      } else if (decodedToken.role === 'ROLE_USER'){
        await router.push(`answers`)
      }else{
        await router.push(``);
      } 
  }catch(err){
      showMessage(err)
  }
}

</script>


<template>
  <AlertModal ref="modal"/>
  <div class="container-md">
    <h3 class="text-center">{{t('login.title')}}</h3>
    <form @submit.prevent="authenticate" class="bg-light fs-5">
    
      <div class="form-group m-3">
        <label for="username" class="col-form-label">{{t('login.username')}} <span>*</span> </label>
        <input type="email" class="form-control" id="username" v-model="userCredentials.username">
        <ul class="validationInfos">
          <li :class="{'valid': userCredentials.username!==''}">{{t('login.usernameRequired')}}</li>
        </ul>
      </div>
      <div class="form-group m-3">
        <label for="password" class="col-form-label">
          {{t('login.password')}} <span>*</span>
          <button class="passwordVisibility" @click="switchVisibility" type="button">
            <img v-if="visibility" :src="eye" width="20px">
            <img v-else :src="eyeSlash" width="20px">
          </button>
        </label>
        <input type="password" class="form-control" id="password" v-model="userCredentials.password">
        <ul class="validationInfos">
          <li :class="{'valid': userCredentials.password!==''}">{{t('login.passwordRequired')}}</li>
        </ul>
      </div>

      <div class="text-bg- text-center m-3">
        <button type="submit" class="btn btn-success m-3 fs-5" :disabled="userCredentials.username==='' || userCredentials.password===''">{{$t('login.submit')}}</button>
      </div>
    </form>
  </div>
</template> 

<style scoped>
  .container-md{
    width: 100%;
    max-width: 576px;
    margin: auto;                  
  }
  span{
    color: red;
  }
  .validationInfos{
    font-size: medium;
  }
  .valid{
    color: green;
    list-style-type: '✓  ';
  }
  .passwordVisibility{
    border: none;
    background-color: #f8f9fa;
  }
</style>