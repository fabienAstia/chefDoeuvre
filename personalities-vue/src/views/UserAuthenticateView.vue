<script setup>
import {ref} from 'vue';
import { useI18n } from 'vue-i18n';
import router from '@/router';
import eye from '@/assets/eye.svg';
import eyeSlash from '@/assets/eyeSlash.svg';
import {useSharedState} from '@/composables/useState'

const{t} = useI18n();
const sharedState = useSharedState();

//let jwt = ref('');
const userCredentials = ref({email:'', password:''});

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
  const url ='http://localhost:8080/users/authenticate';
  const options = {
    method:'POST',
    headers:{
      'Content-type':'application/json',
    },
    body:JSON.stringify(userCredentials.value)
  }
  try{
    const response = await fetch(url, options);
    if(response.ok){
      let jwt = await response.text();
      console.log(jwt);
      localStorage.setItem('jwt', jwt); 
      alert('Congrats ! you are now login')
      sharedState.value = 'logged';
      router.push('/');
    }else if(response.status === 401){
      alert('Bad Credentials');
    }else{
      alert('A client or server side has occured');
    }
  }catch(err){
    alert('an unexpected error has occured');
    console.error('an expected error has occured', err);
  }
}

// async accessToken(){
//             const jwt = localStorage.getItem("jwt").trim();
//             console.log("jwt token :" + jwt)
//             const options= {
//                 method:'GET',
//                 headers: {
//                     'Content-Type': 'application/json',
//                     'Authorization' : `Bearer ${jwt}`
//                 }
//             }
//             try{
//                 const response = await fetch('http://localhost:8080/accounts', options);
//                 if(response.ok){
//                     alert('good')
//                 }else{
//                     alert('problem')
//                 }
//             }catch(err){
//                 console.error(err);
//                 alert('bad')
//             }
        // }

</script>


<template>
  <div class="container-md">
    <h3 class="text-center">{{$t('login.title')}}</h3>
    <form @submit.prevent="authenticate" class="bg-light fs-5">
     
        <div class="form-group m-3">
          <label for="email" class="col-form-label">{{$t('login.email')}} <span>*</span> </label>
          <input type="email" class="form-control" id="email" v-model="userCredentials.email">
          <ul class="validationInfos">
            <li :class="{'valid': userCredentials.email!==''}">{{$t('login.emailRequired')}}</li>
          </ul>
        </div>
        <div class="form-group m-3">
          <label for="password" class="col-form-label">
            {{$t('login.password')}} <span>*</span>
            <button class="passwordVisibility" @click="switchVisibility" type="button">
              <img v-if="visibility" :src="eye" width="20px">
              <img v-else :src="eyeSlash" width="20px">
            </button>
          </label>
          <input type="password" class="form-control" id="password" v-model="userCredentials.password">
          <ul class="validationInfos">
            <li :class="{'valid': userCredentials.password!==''}">{{$t('login.passwordRequired')}}</li>
          </ul>
        </div>

        <div class="text-bg- text-center m-3">
          <button type="submit" class="btn btn-success m-3 fs-5" :disabled="userCredentials.email==='' || userCredentials.password===''">{{$t('login.submit')}}</button>
        </div>
    </form>

  </div>
</template> 

<style scoped>
  .container-md{
    max-width: 576px;
  }
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