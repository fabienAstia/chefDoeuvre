<script setup>
import { useRoute } from 'vue-router';
import { onMounted, ref, computed, watch } from 'vue';
import axios from 'axios'

const route = useRoute();
const code = route.params.code;
const mbtiType = ref({})
const specificJobs = ref({});
const specificJobs2 = ref({})
const isTruncated = ref(false)

onMounted(async() => {
    getMbtiType()
    getSpecificJobs()
})

async function getMbtiType(){
    try{
        const response = await axios.get(`http://localhost:8080/mbti/${code}`)
        mbtiType.value = response.data;
    }catch(err) {
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

const image = computed(() => {
  return new URL(`/src/assets/images/mbtiTypes/${mbtiType.value.image}`, import.meta.url).href;
});

const sortedTraits = computed(() => {
    const traits = {force: [], faiblesse: []};
    if(!mbtiType.value.strengthAndWeaknesses) return traits;
    for(const[trait,evaluation] of Object.entries(mbtiType.value.strengthAndWeaknesses)){
        if(evaluation === "Force"){
            traits.force.push(trait);
        }else {
            traits.faiblesse.push(trait);
        }
    }
    return traits;
});

const formatSalaire = computed(() =>{
    let salaire = specificJobs2.value.salary
    
    if(salaire){
        const regex = /\d+\.\d+/g
        const matches = salaire.match(regex)

        matches.forEach(m => {
            salaire = salaire.replace(m, Math.floor(parseFloat(m)))
        })
        return salaire
    }
    return ''
})    

 
// `http://localhost:8080/questions/paginated?pageNum=${pageNumber.value}&pageSize=${pageSize.value}`
// https://api.pole-emploi.io/partenaire/offresdemploi/v2/offres/search
// https://api.pole-emploi.fr/offresdemploi/v2/offres/search?motsCles=développeur&typeContrat=CDI&departement=75
// motsCles : Permet de rechercher des offres contenant des mots-clés spécifiques.
// typeContrat : Filtre les offres selon le type de contrat (CDI, CDD, etc.).
// experience : Filtre selon le niveau d'expérience requis.
// dureeHebdo : Permet de spécifier la durée hebdomadaire du travail.
// commune : Filtre les offres par code INSEE de la commune.
// departement : Filtre par code du département.
// region : Filtre par code de la région.
// natureContrat : Permet de spécifier la nature du contrat (temps plein, temps partiel, etc.). 
// const getSpecificJobs = async() => {


const getSpecificJobs = async() => {
    try {
        const response = await axios.get(`http://localhost:8080/jobs/paginated?motsCles=${keyWords.value}&page=${1}&size=${2}`) 
        specificJobs.value = response.data.content[0]
        console.log('content[0]', response.data.content[0])
        console.log("result length", response.data.content.length)

        specificJobs2.value = ({
            title: response.data.content[0].title, 
            description: response.data.content[0].description,
            coordinates: response.data.content[0].coordinates, 
            contractType: response.data.content[0].contractType,
            workingHours : response.data.content[0].workingHours, 
            companyName: response.data.content[0].companyName,
            salary: response.data.content[0].salary,
            experience : response.data.content[0].experience,
            sourceUrl: response.data.content[0].sourceUrl
        })

        // console.log('specificJobs:', specificJobs.value)
    }catch(err) {
        if(err.response){
            const statusCode = err.response.status;
            if(statusCode >= 400 && statusCode < 500){
                alert('A client error has occurred!')
            }else if(statusCode >= 500 && statusCode < 600){
                alert('A server error has occurred!')
            }
        }else{
            alert('an unexpected error has occured');
            console.log('unexpected error')
        }
    }
}

watch(
    () => specificJobs2.value.coordinates,
    () => {
        getAddress()
    }
)

const address = ref('')
const formatAddress = computed(() => {
    const a = address.value
    return `<small> ${a.house_number ?? ''}  ${a.road ?? ''} <br> 
        ${a.suburb ?? ''} ${a.city ?? ''}, ${a.country ?? ''} ${a.postcode ?? ''}</small>`
})

const getAddress = async() => {
    let place = specificJobs2.value.coordinates
    console.log('place', specificJobs2.value.coordinates)
    console.log('latitude', specificJobs2.value.coordinates.latitude)

    try {
        const response = await axios.get(`http://localhost:8080/address?lat=${place.latitude}&lon=${place.longitude}`)
        console.log('address', response.data.address)
        address.value = response.data.address
    }catch(err){
        if(err.response){
            const statusCode = err.response.status;
            if(statusCode >= 400 && statusCode < 500){
                alert('A client error has occurred!')
            }else if(statusCode >= 500 && statusCode < 600){
                alert('A server error has occurred!')
            }
        }else{
            alert('an unexpected error has occured');
            console.log('unexpected error')
        }
    }
}

const truncatedDescription = computed(() => {
    const description = specificJobs2.value.description
    if(description){
        console.log('description', description)
        const text = `${description} `
        return text.substring(0, 60) + "...";
    }
}) 

const untruncatedDescription = computed(() => {
    return specificJobs2.value.description
})

const untruncate = () => {
    isTruncated.value = !isTruncated.value
}
const keyWords=ref('Développeur')

</script>

<template>
    <div class="container">
        <div class="container-fluid">
            <div class="row mb-3">
                <div class="col text-center">
                    <h1 class="text-shadow mb-5"><b>{{ mbtiType.code }}</b></h1>
                </div>
            </div>
            <div class="row align-items-center my-3 style">
                <h3 class="text-center text-shadow-light">Présentation</h3>
                <div class="col-12 col-md-6 justify-content-center fs-6 fs-md-5" >
                    <div class="text-center text-shadow-light w-100 mb-3"><b>{{ mbtiType.code }} - {{ mbtiType.name }}</b></div>
                    <ul class="fs-6 fs-md-5" style="list-style-type: '▸';">
                        <li class="my-3 ms-3">{{ mbtiType.description }}</li>
                        <li class="my-3 ms-3">{{ mbtiType.populationPercentage }}% of the population</li>
                        <li class="my-3 ms-3">{{ mbtiType.interestingFact }}</li>
                    </ul>
                </div>
                <div class="d-flex col-12 col-md-6 justify-content-center">
                    <img class="img-fluid" :src="image" alt="mbtiType image" id="image">
                </div>
            </div>

            <div class="row text-center mt-5 style">
                <h3 class="text-shadow-light">Forces et Faiblesses</h3>
                <div class="col-12 col-md-6">
                        <h5 class="text-shadow-light mb-3">Forces</h5>
                        <div class="mb-1" v-for="trait in sortedTraits.force" :key="trait">{{ trait }}</div>
                </div>
                <div class="col-12 col-md-6">
                        <h5 class="text-shadow-light mb-3">Faiblesses</h5>
                        <div class="mb-1" v-for="trait in sortedTraits.faiblesse" :key="trait">{{ trait }}</div>
                </div>
            </div>

            <div class="row mt-5 style">
                <h3 class="text-center text-shadow-light">Métiers</h3>
                <div class="col-12 col-md-6">
                    <div class="text-center mb-1" v-for="job in mbtiType.professions">{{ job }}</div>
                </div>
                <div class="col-12 col-md-6">
                    <input type="text" v-model="keyWords">
                    <div>{{ keyWords }}</div>
            
                    <div><b>{{ specificJobs2.title }}</b> - {{ specificJobs2.contractType }}</div> 
                    <div>{{ specificJobs2.companyName }}</div> 
                    <div v-html="formatAddress"></div> 
                    <div>{{ specificJobs2.workingHours }} </div>
                    <div>{{ formatSalaire }}</div>
                    <div>{{ specificJobs2.experience }}</div>
                    <div>{{ specificJobs2.sourceUrl }}</div>
                    <div v-if="!isTruncated">{{ truncatedDescription }}</div> 
                    <div v-else>{{ untruncatedDescription }}</div> 

                    <button @click="untruncate" v-if="!isTruncated">read more</button>
                    <button @click="untruncate" v-else>read less</button>

                </div>
            </div>

            <div>
                <!-- <input type="text" v-model="keyWords">
                <div>{{ keyWords }}</div>
          
                <div><b>{{ specificJobs2.title }}</b> - {{ specificJobs2.contractType }}</div> 
                <div>{{ specificJobs2.companyName }}</div> 
                <div v-html="formatAddress"></div> 
                <div>{{ specificJobs2.workingHours }} </div>
                <div>{{ formatSalaire }}</div>
                <div>{{ specificJobs2.experience }}</div>
                <div>{{ specificJobs2.sourceUrl }}</div>
                <div v-if="!isTruncated">{{ truncatedDescription }}</div> 
                <div v-else>{{ untruncatedDescription }}</div> 

                <button @click="untruncate" v-if="!isTruncated">read more</button>
                <button @click="untruncate" v-else>read less</button> -->

                <hr>
                <div><p>specific</p>{{ specificJobs }}</div>
                

            </div>

        </div>
    </div>
</template>

<style>
html{
    background-color: #effcfe;
}
.container{
    max-width: 900px!important; 
    padding: 15px;
}
ul{
    padding: 0;
}
h1{
    font-size: 4em;
    color: #0077b6;
}
h3{
    margin-bottom: 25px;
    padding-top: 20px;
    padding-bottom: 20px;
    border-bottom: 2px solid #0079bb21;
    background-color: white;
    border: 2px solid white;
}
.style{
    background-color: #effcfe;
    border: 2px solid #0079bb21;
}
.text-shadow{
    text-shadow:
        0px 1px 0px #270446,
        0px 2px 0px #270446,
        0px 3px 0px #270446,
        0px 4px 0px #270446,
        0px 5px 0px #270446,
        0px 6px 0px #270446,
        0px 7px 0px #270446,
        0px 8px 0px #270446,
        0px 9px 0px #270446,
        0px 10px 0px #270446,

        0px 10px 5px #270446;
}
.text-shadow-light{
    color: #0077b6;
    text-shadow:
        0px 1px 0px #270446,
        0px 2px 0px #270446,
        0px 3px 0px #270446,

        0px 10px 5px #270446;
}
.img-fluid{
    width: 80%;
    height: auto;
    max-height: 300px;
    max-width: 300px;
    min-height: 100px;
    min-width: 100px;
}
#myChart{
    max-width: 350px;
    max-height: 300px;
    min-height: 100px;
}
.progress-bar{
    background-color: #0077b6;
    border-color: #00344f;
}
.progress{
    background-color:#4e038f;
    border-color: #270446;
}
.no-bullet{
    list-style: none;
}

</style>