<script setup>
import {ref} from 'vue';
import { useI18n } from 'vue-i18n';
const userCredentials = ref({email:'', password:''});
const{t} = useI18n();

const newConnexion = async() => {
  const url ='http://localhost:8080/api/auth/authenticate';
  const options = {
    method:'POST',
    headers:{
      'Content-type':'application/json',
      'Authorization':`Bearer ${token}`  
    },
    body:JSON.stringify(userCredentials.value)
  }
  try{
    const response = await fetch(url, options);
    if(response.ok){
      alert('Congrats ! you are now login')
    }else{
      alert('there is a server or client side error')
    }
  }catch(err){
    alert('an unexpected error has occured');
    console.error('an expected error has occured', err);
  }
}
</script>

<template>
<!-- Log-in -->
<div class="modal fade" id="login" tabindex="-1">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header text-bg-light">
          <h5 class="modal-title">{{$t('login.title')}}</h5>
          <button type="button" class="btn-close text-bg-light" data-bs-dismiss="modal" aria-label="Close" @click="close"></button>
        </div>
        <form @submit.prevent="newConnexion">
          <div class="modal-body text-bg-light" >
              <div class="form-group">
                <label for="email" class="col-form-label">{{$t('login.email')}} :</label>
                <input type="email" class="form-control" id="email" v-model="userCredentials.email">
              </div>
              <div class="form-group">
                <label for="password" class="col-form-label">{{$t('login.password')}} :</label>
                <input type="password" class="form-control" id="password" v-model="userCredentials.password"></input>
              </div>
          </div>
          <div class="modal-footer text-bg-light">
            <button type="submit" class="btn btn-secondary">{{$t('login.submit')}}</button>
          </div>
      </form>
  
      </div>
    </div>
  </div>

</template>

<style scoped>
  .container{
    max-width: 768px;
  }
</style>