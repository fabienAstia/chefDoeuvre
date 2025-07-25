<script setup>
import {ref, onMounted, useTemplateRef} from 'vue';
import{useI18n} from 'vue-i18n';
import { useQuestions } from '../composables/useQuestions';
import axios from 'axios';
import {useRouter} from 'vue-router';
import {useMbtiStore} from '@/stores/mbtiStore'
import AlertModal from '@/components/Alert.vue'
import { formatAlert } from '@/composables/useMessageFormatter';

const{t} = useI18n();
const {paginatedQuestions, pageNumber, totalPages, totalElements, pageSize, getPaginatedQuestions, getNextPage} = useQuestions(); 
const hover = ref(false);
const colorCondition = (buttonIndex) => buttonIndex === 0 ? 'grey' : buttonIndex > 0 ? '#0077b6' : '#7b2cbf';
const answers = ref ([]);
const totalPAGES = 3; //for DEMO

const jwt = localStorage.getItem('jwt');
const router = useRouter();
const mbtiStore = useMbtiStore();
const modal = useTemplateRef('modal')

const showMessage = (msg) => {
  modal.value.openModal()
  modal.value.alertTxt = formatAlert(msg).message
}

onMounted(async() => {
    await getPaginatedQuestions();
    paginatedQuestions.value.forEach((question) => {
      console.log(question.id, question.psychPref, question.label)
    })
})

const addAnswer = (idQuestion, buttonIndex) => {
  const existingAnswer = answers.value.find(answer => answer.questionId === idQuestion);
  if(existingAnswer){
    existingAnswer.rating = buttonIndex;
  }else{
    answers.value.push({questionId: idQuestion, rating: buttonIndex, clicked: true});
  }
  console.log("answersLength= "+answers.value.length) ;
  return true;
}

const isPageCompleted = () => {
    answers.value.length === (pageNumber.value +1) * pageSize.value 
    ? getNextPage() && scrollToTop()
    : showMessage(t("answer.uncomplete"))
} 

const isTestCompleted = () => {
  return (answers.value.length === (totalPAGES) * pageSize.value)
} 

const handleSubmit = () => {
  isTestCompleted() 
  ? sendAnswers()
  : showMessage(t("answer.uncomplete"))
}

const sendAnswers = async() => {
  answers.value.forEach((answer) => {
    console.log("id:"+answer.questionId, "rating:"+answer.rating, "clicked:"+answer.clicked);
  })

try {
    const response = await axios.post('http://localhost:8080/answers', 
    answers.value,
    {headers:{'Content-Type':'application/json', 'Authorization':`Bearer ${jwt}`}});    
    mbtiStore.setResult(response.data);
    router.push({name:'result'});
}catch(err) {
    showMessage(err)
  }
}

const scroll = (questionIndex) => {
  if(document.getElementById(questionIndex + 1) !== null){
    document.getElementById(questionIndex + 1).scrollIntoView({behavior:"smooth", block:"center"});
  }
}
const scrollToTop = () => {
  globalThis.scrollTo({ top: 0, left: 0}); 
}

const answered = (idQuestion, buttonIndex) => {
  return answers.value.find(
    (answer) => answer.clicked === true && answer.questionId === idQuestion && answer.rating === buttonIndex
   );
};

</script>


<template>
  <AlertModal ref="modal"/>
    <div id="sticky" class="progressWidth p-3">
      <div class="progress"  role="progressbar" aria-label="Info example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
        <div  class="progress-bar bg-danger p-3" :style="{width: `${(answers.length*100)/24}%`}"></div> <!-- for DEMO -->
        <!-- <div  class="progress-bar bg-danger p-3" :style="{width: `${(answers.length*100)/totalElements}%`}"></div>  -->

      </div>
    </div>
    
      <div v-for= "(q, questionIndex) in paginatedQuestions" class="mt-3 p-4 border-bottom d-flex"> 
        <div class="container-fluid" :id="questionIndex">
          <div class=" row fs-2 m-3 text-center ">
            <b>{{q.label}}</b>
          </div>
          
          <div class="row fs-5 m-3 p-3 d-flex justify-content-center align-items-center" >
            <div class="col-md-2 d-none d-md-block justify-content-end text-end me-3" :style="{color:'#7b2cbf'}">
              <b>{{$t('answer.disagree')}}</b>
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
                  backgroundColor: (hover.questionIndex === questionIndex && hover.buttonIndex === buttonIndex) || (answered(q.id, buttonIndex)) ? colorCondition(buttonIndex) : 'rgb(240, 240, 240)',
                  maxWidth: '100%',
                  maxHeight: '100%'}"
              >
              </button>
            </div>

            <div class="col-md-2 d-none d-md-block ms-3" :style="{color:'#0077b6'}">
              <b>{{$t('answer.agree')}}</b>
            </div>

          </div>

          <div class="row d-md-none d-flex justify-content-between w-100 text-center" >
              <div class="col-6 " :style="{color:'#7b2cbf'}">
                <b>{{$t('answer.disagree')}}</b>
              </div>
              <div class="col-6 text-center" :style="{color:'#0077b6'}">
                <b>{{$t('answer.agree')}}</b>
              </div>
          </div>

        </div>
      </div>

      
      <div v-if="pageNumber<(totalPAGES-1)" class="d-flex justify-content-center bg-light fs-5">
        <button type="submit" class="btn btn-outline-primary btn-lg m-5" @click="isPageCompleted()">Next page</button>
      </div>
      <div v-else="pageNumber===(totalPAGES-1)" class="d-flex justify-content-center bg-light fs-5">
        <form @submit.prevent="handleSubmit" class="bg-light fs-5">
          <button type="submit" class="btn btn-outline-danger btn-lg m-5">Submit</button>
        </form> 
      </div>

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

.progressWidth{
  max-width: 1028px;
  margin: auto;
  position: sticky;
  top:60;
  background: rgba(255, 255, 255, 0.2); 
  backdrop-filter: blur(8px); 
  border-radius: 10px; 
}
#sticky{
  z-index: 1200;
  position: sticky;
  top: 80px;
  opacity: 0.5;
}
.progress-bar {
  background-color: rgba(220, 53, 69, 1); 
  box-shadow: 0px 0px 10px rgba(220, 53, 69, 0.8); 
}

</style>