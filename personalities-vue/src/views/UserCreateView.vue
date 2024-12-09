<script setup>
import {computed, ref} from 'vue';
import { useI18n } from 'vue-i18n';
import { validateAuthentication } from '../components/validateAuthentication';
const {t} = useI18n;

const {
  validatedRegisterForm,
  createUserForm,
  handleSubmit,
  v$
} = validateAuthentication();

const emit = defineEmits(['registered']);

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

</script>


<template>
  <div class="container-md">
    <h3 class="text-center">{{$t('register.title')}}</h3>
    <form @submit.prevent="handleSubmit" class="bg-light fs-5">
     
        <div class="form-group m-3">
          <label for="email" class="col-form-label">{{$t('register.email')}} <span>*</span> </label>
          <input type="email" class="form-control" id="email" v-model="createUserForm.email">
          <span v-for="error in v$.email.$errors" :key="error.$uid" class="text-danger"><div>{{ error.$message }}</div></span>
        </div>
        <div class="form-group m-3">
          <label for="password" class="col-form-label">{{$t('register.password')}} <span>*</span></label>
          <input type="password" class="form-control" id="password" v-model="createUserForm.password">
          <input type="checkbox" class="form-control" id="checkbox">
          <ul class="littleFont">
            <li :class="{'valid': isValidLength}">Entre 8 et 16 caractères</li>
            <li :class="{'valid': containsUpperCase && containsLowerCase}">Au moins une majuscule et une minuscule</li>
            <li :class="{'valid': containsDigit}">Au moins un chiffre</li>
            <li :class="{'valid': containsSpecialChar}">Au moins un caractère spécial (@, #, $, etc.)</li>
          </ul>
          <span v-for="error in v$.password.$errors" :key="error.$uid" class="text-danger "><div>{{ error.$message }}</div></span>
        </div>

        <div class="text-bg- text-center m-3">
          <button type="submit" class="btn btn-success m-3 fs-5" >{{$t('register.submit')}}</button>
        </div>
    </form>

  </div>

</template> 


<style scoped>
  .container-md, h5{
    max-width: 576px;
  }
  span{
    color: red;
  }
  .littleFont{
    font-size: medium;
  }
  .valid{
    color: green;
  }
</style>
