<script setup>
import {computed, ref} from 'vue';
import{useRouter} from 'vue-router';
import { useI18n } from 'vue-i18n';
import eyeSlash from '@/assets/pictos/eyeSlash.svg';
import eye from '@/assets/pictos/eye.svg';
import {useSharedState} from '@/composables/useState'
import axios from 'axios';

const {t} = useI18n();
const router = useRouter();
const sharedState = useSharedState();

const createUserForm = ref({
        username: '',
        password:''
})

const isValidUsername = computed(() => {
  return /^(?=.{1,64}@)\w+([\.-]?\w+)*@(?=.{4,252}$)\w+([\.-]?\w+)*(\.\w{2,4})+$/.test(createUserForm.value.username);
});
const isValidLength = computed(() => {
  return createUserForm.value.password.length <= 20 && createUserForm.value.password.length >= 8;
});
const containsUpperCase = computed(() => {
   return createUserForm.value.password !== createUserForm.value.password.toLowerCase();
});
const containsLowerCase = computed(() => {
   return createUserForm.value.password !== createUserForm.value.password.toUpperCase();
});
const containsDigit = computed(() => {
   return /\d/.test(createUserForm.value.password);
});
const containsSpecialChar = computed(() => {
   return /[^\w\s]/.test(createUserForm.value.password);
});

const validateRegistration = computed(() => { 
  return isValidUsername.value
  && isValidLength.value 
  && containsUpperCase.value 
  && containsLowerCase.value 
  && containsDigit.value 
  && containsSpecialChar.value;
});

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

const newUser = async() => {
    try {
      await axios.post('http://localhost:8080/users', createUserForm.value);
        alert('you have created an account');
        router.push('/authenticate');
    } catch(err) {
      if(err.response){
        const statusCode = err.response.status;
        if(statusCode >= 400 && statusCode < 500) {
          alert(err.response.data.fieldsErrors.username, err.response.data.fieldsErrors.password)
        } else if (statusCode >= 500 && statusCode < 600) {
          alert(t('error.server'))
        }
      } else {
        alert(t('error.unexpected'))
        console.error('An unexpected error has occured', err);
      }
    }
}
</script>


<template>
  <div class="container-md">
    <h3 class="text-center">{{$t('register.title')}}</h3>
    <form @submit.prevent="newUser" class="bg-light fs-5">
     
        <div class="form-group m-3">
          <label for="username" class="col-form-label">{{$t('register.username')}} <span>*</span> </label>
          <input type="email" class="form-control" id="username" v-model="createUserForm.username">
          <ul class="validationInfos">
            <li :class="{'valid': isValidUsername}">{{$t('register.usernameValid')}}</li>
          </ul>
        </div>
        <div class="form-group m-3">
          <label for="password" class="col-form-label">
            {{$t('register.password')}} <span>* </span>
            <button class="passwordVisibility" @click="switchVisibility" type="button">
              <img v-if="visibility" :src="eye" width="20px">
              <img v-else :src="eyeSlash" width="20px">
            </button>
          </label>
          <input type="password" class="form-control" id="password" v-model="createUserForm.password">
          <ul class="validationInfos">
            <li :class="{'valid': isValidLength}">{{$t('register.passwordLength')}}</li> 
            <li :class="{'valid': containsUpperCase && containsLowerCase}">{{$t('register.passwordLowerUpperCase')}}</li>
            <li :class="{'valid': containsDigit}">{{$t('register.passwordDigit')}}</li>
            <li :class="{'valid': containsSpecialChar}">{{$t('register.passwordSpecialChar')}}</li>
          </ul>
        </div>

        <div class="text-bg- text-center m-3">
          <button type="submit" class="btn btn-success m-3 fs-5" :disabled="!validateRegistration">{{$t('register.submit')}}</button>
        </div>
    </form>

  </div>
</template> 


<style scoped>
  .container-md{
    max-width: 576px;
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
