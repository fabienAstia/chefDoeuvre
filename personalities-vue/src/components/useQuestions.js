import {ref, onMounted} from 'vue'; 
import axios from 'axios'

export function useQuestions(){
  const questions = ref([])
  const allQuestions = ref([])
  const paginatedQuestions = ref([])
  const question = ref({id:'', text:'', dimension:'', isEditable: false})
  const jwt = localStorage.getItem('jwt');
    
  //Nvelle question
  const addQuestion = async() => {
    try{
        await axios.post('http://localhost:8080/questions', 
        question.value,
        {headers: {'Authorization': `Bearer ${jwt}`}});
          alert('You have created a new question.')
          questions.value.push({id: '', text: question.value.text, dimension: question.value.dimension});
          question.value.text= '';
          question.value.dimension= ''; 
          loadQuestions();
    }catch(err){
        if(err.response){
          const statusCode = err.response.status;
          if(statusCode >=400 && statusCode <500){
            alert(err.response.data.fieldsErrors.text)
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
    try {
      const response = await axios.get('http://localhost:8080/questions');
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

  async function getPaginatedQuestions() {
    try {
      const response = await axios.get('http://localhost:8080/questions/paginated');
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
            questions.value = allQuestions.value.filter(q => q.dimension === 'I' || q.dimension === 'E');
              break;
          case 'NS' :
            questions.value = allQuestions.value.filter(q => q.dimension === 'N' || q.dimension === 'S');
              break;
          case 'TF' :
            questions.value = allQuestions.value.filter(q => q.dimension === 'T' || q.dimension === 'F');
              break;
          case 'PJ' :
            questions.value = allQuestions.value.filter(q => q.dimension === 'P' || q.dimension === 'J');
              break;
      }     
  }

  function sortDim(dim){ 
      questions.value = allQuestions.value.filter(q => q.dimension === dim);
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
      await axios.put(`http://localhost:8080/questions/${index}`, 
        {id: '', text: updateQuestion.text, dimension: updateQuestion.dimension},
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
          alert(err.response.data.fieldsErrors.text)
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
      await axios.delete(`http://localhost:8080/questions/${index}`, {
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
      paginatedQuestions,
      editQuestion,
      updateQuestion,
      deleteQuestion,
      sortAxe,
      sortDim
  }
}

