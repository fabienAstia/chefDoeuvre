import {ref, onMounted} from 'vue'; 
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL

export function useQuestions(){
  const questions = ref([])
  const allQuestions = ref([])
  const paginatedQuestions = ref([])
  const question = ref({id:'', label:'', psychPref:'', isEditable: false})
  const jwt = localStorage.getItem('jwt');
  const pageNumber = ref(0);
  const pageSize = ref(8);  
  const totalPages = ref(0);
  const totalElements = ref(0);

  //Nvelle question
  const addQuestion = async() => {
    try{
        await axios.post(`${apiUrl}/questions`, 
        question.value,
        {headers: {'Authorization': `Bearer ${jwt}`}});
          alert('You have created a new question.')
          questions.value.push({id: '', label: question.value.label, psychPref: question.value.psychPref});
          question.value.label= '';
          question.value.psychPref= ''; 
          loadQuestions();
    }catch(err){
        if(err.response){
          const statusCode = err.response.status;
          if(statusCode >=400 && statusCode <500){
            alert(err.response.data.fieldsErrors.label) 
          }else if(statusCode >=500 && statusCode <600){
            alert('A server error has occurred!')
          }
        }else{
          alert('an unexpected error has occured');
          console.error('an unexpected error has occured', err);
        }
    }
  }
    
  // AFFICHAGE des questions
  onMounted(() => {
    loadQuestions();
  })

  async function loadQuestions() {
    console.log("pagenumber = "+pageNumber.value)
    try {
      const response = await axios.get(`${apiUrl}/questions`);
      allQuestions.value = response.data;
      questions.value = response.data;
      console.log(questions.value)
    }catch(err){
      if(err.response){
        const statusCode = err.response.status;
        if(statusCode >=400 && statusCode <500){
          alert('A client error has occurred!')
        }else if(statusCode >=500 && statusCode <600){
          alert('A server error has occurred!')
        }
      }else{
        alert('an unexpected error has occured');
        console.error('an unexpected error has occured', err);
      }
    }
  }
  
//Questios PAGIEES
  async function getPaginatedQuestions() {
    try {
      const url =`${apiUrl}/questions/paginated?pageNum=${pageNumber.value}&pageSize=${pageSize.value}`;
      const response = await axios.get(url);
      paginatedQuestions.value = response.data.content;
      totalPages.value = response.data.page.totalPages;
      totalElements.value = response.data.page.totalElements;
      pageSize.value = response.data.page.size;
      paginatedQuestions.value.forEach((question) => {
        console.log("ID:", question.id, "psychPref:", question.psychPref, "Label:", question.label);
      });
    }catch(err){
      if(err.response){
        const statusCode = err.response.status;
        if(statusCode >=400 && statusCode <500){
          alert('A client error has occurred!')
        }else if(statusCode >=500 && statusCode <600){
          alert('A server error has occurred!')
        }
      }else{
        alert('an unexpected error has occured');
        console.error('an unexpected error has occured', err);
      }
    }
  }

  async function getNextPage() {
    try {
      pageNumber.value++
      const response = await axios.get(`${apiUrl}/questions/paginated?pageNum=${pageNumber.value}&pageSize=${pageSize.value}`);
      paginatedQuestions.value = response.data.content;
    }catch(err){
      if(err.response){
        const statusCode = err.response.status;
        if(statusCode >=400 && statusCode <500){
          alert('A client error has occurred!')
        }else if(statusCode >=500 && statusCode <600){
          alert('A server error has occurred!')
        }
      }else{
        alert('an unexpected error has occured');
        console.error('an unexpected error has occured', err);
      }
    }
  }
    
  // TRIER les questions par AXE
  function sortAxe(axe){
      switch (axe) {
          case 'IE' :
            questions.value = allQuestions.value.filter(q => q.psychPref === 'I' || q.psychPref === 'E');
              break;
          case 'NS' :
            questions.value = allQuestions.value.filter(q => q.psychPref === 'N' || q.psychPref === 'S');
              break;
          case 'TF' :
            questions.value = allQuestions.value.filter(q => q.psychPref === 'T' || q.psychPref === 'F');
              break;
          case 'PJ' :
            questions.value = allQuestions.value.filter(q => q.psychPref === 'P' || q.psychPref === 'J');
              break;
      }     
  }

  function sortDim(dim){ 
      questions.value = allQuestions.value.filter(q => q.psychPref === dim);
  }     
  
  //Edit Question
  async function editQuestion(index) {
    const editableQuestion = questions.value.find(q => q.id === index);
      editableQuestion.isEditable=!editableQuestion.isEditable;  
  }
  
  //Update Question
  async function updateQuestion(index){
    const updateQuestion = questions.value.find(q => q.id === index);
    try {
      await axios.put(`${apiUrl}/questions/${index}`, 
        {id: '', label: updateQuestion.label, psychPref: updateQuestion.psychPref},
        { headers: {
          'Authorization': `Bearer ${jwt}`
      }});
      alert('You have modified the question');
      editQuestion(index);
    }catch(err){
      if(err.response){
        const statusCode = err.response.status;
        console.log(err.response.data)
        if(statusCode >=400 && statusCode <500){
          alert(err.response.data.fieldsErrors.label)
        }else if(statusCode >=500 && statusCode <600){
          alert('A server error has occurred!')
        }
      }else{
        alert('an unexpected error has occured');
        console.error('an unexpected error has occured', err);
      }
    }
  }
  
  //Delete Question
  async function deleteQuestion(index) {
    try {
      await axios.delete(`${apiUrl}/questions/${index}`, {
        headers: {'Authorization': `Bearer ${jwt}`}
      });
        alert('You have deleted the question');
        questions.value =loadQuestions();
    }catch(err){
        if(err.response){
          const statusCode = err.response.status;
          if(statusCode >=400 && statusCode <500){
            alert('A client error has occurred!')
          }else if(statusCode >=500 && statusCode <600){
            alert('A server error has occurred!')
          }
        }else{
          alert('an unexpected error has occured');
          console.error('an unexpected error has occured', err);
        }
    }
  }
  
  return {
      questions,
      question,
      addQuestion,
      loadQuestions,
      getPaginatedQuestions,
      getNextPage,
      pageNumber,
      pageSize,
      totalPages,
      totalElements,
      paginatedQuestions,
      editQuestion,
      updateQuestion,
      deleteQuestion,
      sortAxe,
      sortDim
  }
}

