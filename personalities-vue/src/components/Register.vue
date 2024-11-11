<script setup>
import { useI18n } from 'vue-i18n';
import {ref} from 'vue';

const registration = ref({username:'', email:'', password:''});

const emit = defineEmits(['registered']);

const{t}= useI18n;

const newRegistration = async() => {
  const url = 'http://localhost:8080/api/auth/register'
  const options = {
    method:'POST',
    headers:{'Content-type':'application/json'},
    body:JSON.stringify(registration.value)
  }
  try{
    const response = await fetch(url, options);
    if (response.ok){
      emit('registered');
      console.log('Emission de l\'événement "registered"');
    }else{
      alert('A client or server side error has occured');
    }
  }catch(err){
    alert('An expected error has occured');
    console.error('An expected error has occured', err);
  }
}
</script>

<template>
<!-- register -->
<div class="modal fade" id="register" tabindex="-1">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header text-bg-light">
        <h5 class="modal-title">{{$t('register.title')}}</h5>
        <button type="button" class="btn-close text-bg-light" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <form @submit.prevent="newRegistration">
        <div class="modal-body text-bg-light" >
            <div class="form-group">
              <label for="username" class="col-form-label">{{$t('register.username')}} :</label>
              <input type="text" class="form-control" id="username" v-model="registration.username">
            </div>
            <div class="form-group">
              <label for="email" class="col-form-label">{{$t('register.email')}} :</label>
              <input type="email" class="form-control" id="email" v-model="registration.email">
            </div>
            <div class="form-group">
              <label for="password" class="col-form-label">{{$t('register.password')}} :</label>
              <input type="password" class="form-control" id="password" v-model="registration.password"></input>
            </div>
        </div>
        <div class="modal-footer text-bg-light">
          <button type="submit" class="btn btn-secondary">{{$t('register.submit')}}</button>
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
