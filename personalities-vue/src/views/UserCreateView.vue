<script setup>
import {computed, ref} from 'vue';
import{useRouter} from 'vue-router';
import { useI18n } from 'vue-i18n';
const {t} = useI18n;
const router = useRouter();

const props = defineProps(['registered']);
const emit = defineEmits(['registered']);

const createUserForm = ref({
        email: '',
        password:''
})

const isValidEmail = computed(() => {
  return /^(?=.{1,64}@)\w+([\.-]?\w+)*@(?=.{4,252}$)\w+([\.-]?\w+)*(\.\w{2,4})+$/.test(createUserForm.value.email);
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
  return isValidEmail
  && isValidLength.value 
  && containsUpperCase.value 
  && containsLowerCase.value 
  && containsDigit.value 
  && containsSpecialChar.value;
});
     
const newUser = async() => {
    const url = 'http://localhost:8080/users'
    const options = {
        method:'POST',
        headers:{'Content-type':'application/json'},
        body:JSON.stringify(createUserForm.value)
    }
    try {
        const response = await fetch(url, options);
        if (response.ok){
            alert('you have created an account');
            emit('registered');
            router.push('/authenticate');
        }else{
            alert('A client or server side error has occured');
        }
    } catch(err) {
        alert('An expected error has occured');
        console.error('An expected error has occured', err);
    }
}
</script>


<template>
  <div class="container-md">
    <h3 class="text-center">{{$t('register.title')}}</h3>
    <form @submit.prevent="newUser" class="bg-light fs-5">
     
        <div class="form-group m-3">
          <label for="email" class="col-form-label">{{$t('register.email')}} <span>*</span> </label>
          <input type="email" class="form-control" id="email" v-model="createUserForm.email">
          <ul class="validationInfos">
            <li :class="{'valid': isValidEmail}">{{$t('register.emailValid')}}</li>
          </ul>
        </div>
        <div class="form-group m-3">
          <label for="password" class="col-form-label">{{$t('register.password')}} <span>*</span></label>
          <input type="password" class="form-control" id="password" v-model="createUserForm.password">
          <input type="checkbox" class="form-control" id="checkbox">
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
</style>
