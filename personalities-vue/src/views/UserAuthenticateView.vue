<script setup>
import {ref} from 'vue';
import { useI18n } from 'vue-i18n';
import {validateAuthentication} from '@/components/validateAuthentication'
import router from '@/router';

const{t} = useI18n();

const {
  validatedRegisterForm,
  createUserForm,
  emailPattern,
  passwordPattern,
  rules,
  handleSubmit,
  v$,
  newUser,
  touchField
    } = validateAuthentication();

//let jwt = ref('');
const userCredentials = ref({email:'', password:''});


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
      router.push('/home');
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
          <span v-for="error in v$.email.$errors" :key="error.$uid" class="text-danger"><div>{{ error.$message }}</div></span>
        </div>
        <div class="form-group m-3">
          <label for="password" class="col-form-label">{{$t('login.password')}} <span>*</span></label>
          <input type="password" class="form-control" id="password" v-model="userCredentials.password">
          <input type="checkbox" class="form-control" id="checkbox">
          <!-- <ul class="littleFont">
            <li :class="{'valid': isValidLength}">Entre 8 et 16 caractères</li>
            <li :class="{'valid': containsUpperCase && containsLowerCase}">Au moins une majuscule et une minuscule</li>
            <li :class="{'valid': containsDigit}">Au moins un chiffre</li>
            <li :class="{'valid': containsSpecialChar}">Au moins un caractère spécial (@, #, $, etc.)</li>
          </ul> -->
          <span v-for="error in v$.password.$errors" :key="error.$uid" class="text-danger "><div>{{ error.$message }}</div></span>
        </div>

        <div class="text-bg- text-center m-3">
          <button type="submit" class="btn btn-success m-3 fs-5" >{{$t('login.submit')}}</button>
        </div>
    </form>

  </div>

</template> 

<style scoped>
  .container-md{
    max-width: 768px;
  }
</style>