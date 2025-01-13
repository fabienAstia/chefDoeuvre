<script setup>
import {ref, onMounted} from 'vue';
import{useI18n} from 'vue-i18n';
const{t} = useI18n;
import { useQuestions } from './useQuestions';
import axios from 'axios';

const {paginatedQuestions, getPaginatedQuestions} = useQuestions();
const hover = ref(false);
const colorCondition = (buttonIndex) => buttonIndex === 0 ? 'grey' : buttonIndex > 0 ? 'blue' : 'purple';
const answered = ref({})
const jwt = localStorage.getItem('jwt');

onMounted(async() => {
    await getPaginatedQuestions();
    console.log(paginatedQuestions.value)
    console.log(paginatedQuestions)
    console.log("page2"+paginatedQuestions.pageable)

})

const addAnswer = async(idQuestion, buttonIndex, questionIndex) => {
  try {
    await axios.post('http://localhost:8080/answers', 
    {questionId: idQuestion, rating: buttonIndex},
    {headers:{'Content-Type':'application/json', 'Authorization':`Bearer ${jwt}`}});    
    alert('You have sent your response');
    answered.value[questionIndex] = buttonIndex;
  } catch(err) {
      if(err.response){
          const statusCode = err.response.status;
          if(statusCode >= 400 && statusCode < 500){
            alert('A client error has occurred!')
          }else if(statusCode >= 500 && statusCode < 600){
            alert('A server error has occurred!')
          }
      }else{
          alert('an unexpected error has occured');
          console.error('an unexpected error has occured', err);
      }
  }
}
const scroll = (questionIndex) => {
  document.getElementById(questionIndex + 1).scrollIntoView({behavior:"smooth", block:"center"});
}
</script>

<template>
    <!-- barre de progression -->
   
      <div v-for= "(q, questionIndex) in paginatedQuestions" class=" my-5 py-4 border-bottom d-flex"> 
        <div class="container-fluid" :id="questionIndex">
          <div class=" row fs-2 m-3 text-center ">
            <b>{{questionIndex+1}}. {{q.text}}</b>
          </div>
          
          <div class="row fs-5 m-3 p-3 d-flex justify-content-center align-items-center" >
            <div class="col-md-2 d-none d-md-block justify-content-end text-end me-3" :style="{color:'purple'}">
              <b>{{$t('answer.agree')}}</b>
            </div>
            <div class="col-12 col-sm-8 col-md-8 buttons d-flex justify-content-center align-items-center m-0 p-0 gap-3">
              <button
                v-for="buttonIndex in [-3, -2, -1, 0, 1, 2, 3]"
                :key="buttonIndex" 
                @mouseover="hover = {questionIndex, buttonIndex}"
                @mouseleave="hover = false"
                @click="addAnswer(q.id, buttonIndex, questionIndex); scroll(questionIndex)" 
                :class="'btn-circle '" 
                :style="{
                  width: `${3 + Math.abs(buttonIndex) * 1.3}vw`, 
                  height: `${3 + Math.abs(buttonIndex) * 1.3}vw`, 
                  aspectRatio: 1/1,
                  borderColor: colorCondition(buttonIndex),
                  backgroundColor: (hover.questionIndex === questionIndex && hover.buttonIndex === buttonIndex) || (answered[questionIndex] === buttonIndex) ? colorCondition(buttonIndex) : 'rgb(240, 240, 240)',
                  maxWidth: '100%',
                  maxHeight: '100%'}"
              >
              </button>
            </div>

            <div class="col-md-2 d-none d-md-block ms-3" :style="{color:'blue'}">
              <b>{{$t('answer.disagree')}}</b>
            </div>

          </div>

          <div class="row d-md-none d-flex justify-content-between w-100 text-center" >
              <div class="col-6 " :style="{color:'purple'}">
                <b>{{$t('answer.agree')}}</b>
              </div>
              <div class="col-6 text-center" :style="{color:'blue'}">
                <b>{{$t('answer.disagree')}}</b>
              </div>
          </div>

        </div>
      </div>
      <a @click="paginatedQuestions.pageable.pageNumber ===1">page 2</a>
</template>

<style scoped>


.buttons{
  min-width: none;
  width: auto; 
}

.btn-circle{
  border-radius: 50%;
  border: solid 3px;
  margin: 0;
  width: auto;
  height: auto;
  min-width: none;
  min-height: none;
}

</style>