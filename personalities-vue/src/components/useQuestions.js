import {ref, onMounted} from 'vue';

export function useQuestions(){
    const questions = ref([])
    const allQuestions = ref([])
    const question = ref({id:'', text:'', dimension:'', isEditable: false})
    
    //Nvelle question
    const addQuestion = async() => {
        const url = 'http://localhost:8080/admin/questions'
        const options = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify(question.value)
        }
        try{
            const response = await fetch(url, options);
            if(response.ok) {
              alert('You have created a new question.')
              questions.value.push({id: '', text: question.value.text, dimension: question.value.dimension});
              question.value.text= '';
              question.value.dimension= ''; 
            } else {
              alert('A client or server error has occured');
            }
        } catch (error) {
            alert('An unexpected error has occured!');
            console.error('An unexpected error has occured', error);
        }
    }
    
    
    // AFFICHAGE des questions
    onMounted(() => {
      loadQuestions();
    })

    async function loadQuestions() {
        const url = 'http://localhost:8080/admin/questions';
        try{
          const response = await fetch(url);
          if(response.ok){
            const data = await response.json();
            allQuestions.value = data;
            questions.value = data;
            console.log(data);
          }else if(response.status === 401){
            alert('You don\'t have aceess');
          }else{
            alert('A client or server error has occured');
          }
        }catch(err){
          alert('An unexpected error has occured!');
          console.error('An unexpected error has occured', error);
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
        
        const url = `http://localhost:8080/admin/questions/${index}`;
        const options = {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'}, 
            body: JSON.stringify({id: '', text: updateQuestion.text, dimension: updateQuestion.dimension}),    
        }
        try {
            const response = await fetch(url, options);
            if(response.ok) {
            alert('You have modified the question');
            } else {
                alert('A client or server error has occured');
            }
        } catch (error) {
            alert('An unexpected error has occured!');
            console.error('An unexpected error has occured', error);
        }
    }
    
    //Delete Question
    async function deleteQuestion(index) {
      const url = `http://localhost:8080/admin/questions/${index}`;
      const options = {
        method: 'DELETE'
      }
      try {
        const response = await fetch(url, options);
        if (response.ok) {
          alert('You have deleted the question');
          questions.value =loadQuestions();
        }else{
          alert('A client or server error has occured');
        }
      } catch(error) {
        console.error(error);
      }
    }
    
    return {
        questions,
        question,
        addQuestion,
        loadQuestions,
        editQuestion,
        updateQuestion,
        deleteQuestion,
        sortAxe,
        sortDim
    }
}

