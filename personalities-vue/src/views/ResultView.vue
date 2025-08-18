<script setup>
    import Chart from 'chart.js/auto';
    import { onMounted, computed, useTemplateRef, ref, watch } from 'vue';
    import { useMbtiStore } from '@/stores/mbtiStore';
    import { formatAlert } from '@/composables/useMessageFormatter';
    import AlertModal from '@/components/Alert.vue'
    import OfferJobCard from '@/components/OfferJobCard.vue';
    import { useI18n } from 'vue-i18n';
    import axios from 'axios'

    const specificJobs = ref({});
    const allCoordinates = ref({})
    const keyWords=ref('')
    const isTruncated = ref({})
    const addressesChunk = ref ([])
    const modal = useTemplateRef('modal')
    const pageNumber = ref(0);
    const pageSize = ref(2);  
    // const totalPages = ref(0);
    // const totalElements = ref(0);
    const metadata = ref({})
    const apiUrl = import.meta.env.VITE_API_URL

    const {t} = useI18n();
    const mbtiStore = useMbtiStore();
    const mbtiType = computed(() => mbtiStore.result);

    const showMessage = (msg) => {
        modal.value.openModal()
        modal.value.alertTxt = formatAlert(msg).message
    }

    const sortedTraits = computed(() => {
        const traits = {force: [], faiblesse: []};
        for(const[trait,evaluation] of Object.entries(mbtiType.value.strengthAndWeaknesses)){
            if(evaluation === "Force"){
                traits.force.push(trait);
            }else {
                traits.faiblesse.push(trait);
            }
        }
        console.log("traits =" + traits)
        return traits;
    });

    const percentages = computed(() => {
        const percentages = {};
        for(const[prefPsych, percentage] of Object.entries(mbtiType.value.rateByPsych)){
            percentages[prefPsych] = percentage;
        }
        return percentages;
    });

    const data = {
        labels: [
            'Intuition',
            'Thinking',
            'Extraversion',
            'Perception',
            'Sensation',
            'Feeling',
            'Introversion',
            'Judgment'
        ],
        datasets: [{
            label: 'Psychological preferences',
            data: [percentages.value["INTUITION"], percentages.value["THINKING"], percentages.value["EXTRAVERSION"], percentages.value["PERCEPTION"], percentages.value["SENSATION"], percentages.value["FEELING"], percentages.value["INTROVERSION"], percentages.value["JUDGMENT"]],
            fill: true,
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgb(255, 99, 132)',
            pointBackgroundColor: 'rgb(255, 99, 132)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgb(255, 99, 132)'
        }
    ]}

    const config = {
        type: 'radar',
        data: data,
        options: {
            scales: {
                r: {
                    beginAtZero: true,
                    min:0,
                    max:100,
                    pointLabels: {
                        display: true 
                    },
                    ticks: {
                        display: false 
                    }
                }
            },
            plugins: {
                legend: {
                    display: false,
                },
                datalabels: {
                    display: false,
                }
            },
            elements: {
                line: {
                    borderWidth: 3
                }
            }
        },
    };

onMounted(async() => {
    const context = document.getElementById('myChart'); 
    const myChart = new Chart(context, config);
    console.log("rateByPsych :" + Object.entries(mbtiType.value.rateByPsych))
})

const getSpecificJobs = async() => {
    try {
        const response = await axios.get(`${apiUrl}/jobs/paginated?motsCles=${keyWords.value}&page=${pageNumber.value}&size=${pageSize.value}`) 
        specificJobs.value = response.data.data.paginatedOfferJobViews
        allCoordinates.value = response.data.data.allCoordinates

        specificJobs.value.forEach((offerJob, index) => {
            isTruncated.value[index] = true;
        })
        metadata.value = response.data.metadata;
        await getAddress();

        console.log("specificJobs", specificJobs.value)
        console.log("allCoordinates", allCoordinates.value)
    }catch(err) {
       showMessage(err)
    }
}

const getNextPage = async() => {
    try {
        pageNumber.value++
        await getSpecificJobs();
        // const response = await axios.get(`http://localhost:8080/jobs/paginated?motsCles=${keyWords.value}&page=${0}&size=${2}`) 
        // specificJobs.value = response.data.data.paginatedOfferJobViews
        // allCoordinates.value = response.data.data.allCoordinates

        // specificJobs.value.forEach((offerJob, index) => {
        //     isTruncated.value[index] = true;
        // })

        // metadata.value = response.data.metadata;

        // console.log("specificJobs", specificJobs.value)
        // console.log("allCoordinates", allCoordinates.value)

    }catch(err) {
       showMessage(err)
    }
}

const getPreviousPage = async() => {
    try {
        pageNumber.value--
        await getSpecificJobs();
    } catch(err) {
        showMessage(err)
    }
}

watch(keyWords, async () => {
        await getSpecificJobs()
    }
)

const addresses = ref ([])
const getAddress = async() => {
    try {
        const response = await axios.post(`${apiUrl}/address`,
            allCoordinates.value.slice(0, 20),
            {headers:{'Content-Type':'application/json'}}); 
        
        addressesChunk.value = response.data
        console.log('addressesChunk', addressesChunk.value)
        addresses.value = []
        for(let i = 0; i < addressesChunk.value.length; i++){
            addresses.value.push(addressesChunk.value[i].formatted)
        }
        console.log('addresses.value', addresses.value)
        return addresses.value
    }catch(err){
        showMessage(err)
    }
}

const displayOffers = (job) => {
    keyWords.value = job;
    pageNumber.value=0;
}
</script>

<template>
    <AlertModal ref="modal"/>
    <body class="container-fluid">
        <div class="row my-3">
            <div class="col text-center">
                <h1 class="text-shadow mb-5"><b>Vos résultats</b></h1>
            </div>
        </div>
        <div class="row align-items-center my-3 style">
            <h3 class="text-center text-shadow-light">{{t('personality.presentation')}}</h3>
            <div class="col-12 col-md-6 justify-content-center fs-6 fs-md-5" >
                <div class="text-center text-shadow-light w-100 mb-3"><strong>{{ mbtiType.code }} - {{ mbtiType.name }}</strong></div>
                <ul class="fs-6 fs-md-5" style="list-style-type: '▸';">
                    <li class="my-3 ms-3">{{ mbtiType.description }}</li>
                    <li class="my-3 ms-3">{{ mbtiType.populationPercentage }}% of the population</li>
                    <li class="my-3 ms-3">{{ mbtiType.interestingFact }}</li>
                </ul>
            </div>
            <div class="d-flex col-12 col-md-6 justify-content-center">
                <img class="img-fluid" :src="`/mbtiTypes/${mbtiType.image}`" :alt="`${mbtiType} image`" id="image">
            </div>
        </div>

        <div class="row align-items-center my-5 style">
            <h3 class="text-center text-shadow-light">Vos résultats</h3>
            <div class="d-flex col-12 col-md-6 justify-content-center" >
                <canvas id="myChart"></canvas> 
            </div>
            <div class="d-flex col-12 col-md-6 justify-content-center text-center">
                <ul class="w-100 no-bullet">
                    <li class="my-3">
                        <div class="d-flex">
                            <div style="color: #0077b6;">
                                <b>Introversion</b> 
                            </div>
                            <div class="text-end w-100" style="color: #270446;">
                                <b>Extraversion</b>
                            </div>
                        </div>
                        <div class="progress position-relative" role="progressbar" aria-label="Info example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar" :style="{ width: `${percentages.INTROVERSION}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{percentages["INTROVERSION"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["EXTRAVERSION"] }}%</span>
                        </div>
                    </li>
                    <li class="my-3">
                        <div class="d-flex">
                            <div style="color: #0077b6;">
                                <b>Intuition</b> 
                            </div>
                            <div class="text-end w-100" style="color: #270446;">
                                <b>Sensation</b>
                            </div>
                        </div>
                        <div class="progress position-relative" role="progressbar" aria-label="Info example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar" :style="{ width: `${percentages.INTUITION}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{percentages["INTUITION"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["SENSATION"] }}%</span>
                        </div>
                    </li>
                    <li class="my-3">
                        <div class="d-flex">
                            <div style="color: #0077b6;">
                                <b>Pensée</b> 
                            </div>
                            <div class="text-end w-100" style="color: #270446;">
                                <b>Sentiment</b>
                            </div>
                        </div>
                        <div class="progress position-relative" role="progressbar" aria-label="Info example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar" :style="{ width: `${percentages.THINKING}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{ percentages["THINKING"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["FEELING"] }}%</span>
                        </div>
                    </li>
                    <li class="my-3">
                        <div class="d-flex">
                            <div style="color: #0077b6;">
                                <b>Perception</b> 
                            </div>
                            <div class="text-end w-100" style="color: #270446;">
                                <b>Jugement</b>
                            </div>
                        </div>
                        <div class="progress position-relative" role="progressbar" aria-label="Info example" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100">
                            <div class="progress-bar" :style="{ width: `${percentages.PERCEPTION}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{ percentages["PERCEPTION"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["JUDGMENT"] }}%</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="row text-center mt-5 style">
            <h2 class="text-shadow-light">{{t('personality.strengths_&_weaknesses')}}</h2>
            <div class="col-12 col-md-6">
                    <h3 class="text-shadow-light mb-3">{{t('personality.strengths')}}</h3>
                    <div class="mb-1" v-for="trait in sortedTraits.force" :key="trait">{{ trait }}</div>
            </div>
            <div class="col-12 col-md-6">
                    <h3 class="text-shadow-light mb-3">{{t('personality.weaknesses')}}</h3>
                    <div class="mb-1" v-for="trait in sortedTraits.faiblesse" :key="trait">{{ trait }}</div>
            </div>
        </div>

        <div class="row mt-5 style">
            <h2 class="text-center text-shadow-light">{{t('personality.jobs')}}</h2>
            
            <div :class="['col-12', specificJobs.length ? 'col-md-6' : 'col-md-12', 'fs-6 fs-md-5 text-center']">
                <h3 class="text-shadow-light mb-3">{{t('personality.professions')}}</h3>
                    <div v-if="!specificJobs.length" class="callout-note mb-3" id="jobs-help" role="note">
                    <strong >{{ t('personality.accessibility_offerJob') }}</strong>
                </div>
                <div class="text-center mb-1" v-for="job in mbtiType.professions" @click="displayOffers(job)" id="pointer">{{ job }}</div>
            </div>

            <div class="offersJob col-12 col-md-6 fs-6 fs-md-5" >
                <h3 v-if="specificJobs.length" class="text-shadow-light mb-3 text-center">{{t('personality.offersJob')}}</h3>
                <div v-for="(offerJob, index) in specificJobs" :key="index">
                    
                    <!-- <OfferJobCard
                    v-if="addresses[index]"
                    :offerJob="offerJob"
                    :address="addresses[index]"
                    /> -->

                    <OfferJobCard
                    :offerJob="offerJob"
                    />

                </div>    

                <div v-if="pageNumber===0 && pageNumber<(metadata.totalPages-1)" class="d-flex justify-content-center fs-2 gap-2 align-items-center">
                    <button v-if="pageNumber<(metadata.totalPages-1)" class="btn btn-primary btn-sm m-1" @click="getNextPage()">Next page</button>
                    <span class="page_indicator">{{pageNumber+1}}/{{metadata.totalPages}}</span>
                </div>   
                <div v-if="pageNumber>0 && pageNumber<(metadata.totalPages-1)" class="d-flex justify-content-around fs-2  align-items-center">
                    <button v-if="pageNumber>0" class="btn btn-primary btn-sm m-1" @click="getPreviousPage()">Previous page</button>
                    <span class="page_indicator">{{pageNumber+1}}/{{metadata.totalPages}}</span>
                    <button v-if="pageNumber<(metadata.totalPages-1)" class="btn btn-primary btn-sm m-1" @click="getNextPage()">Next page</button>
                </div>   
                <div v-if="pageNumber===(metadata.totalPages-1)" class="d-flex justify-content-center fs-2 gap-2 align-items-center">
                    <button v-if="pageNumber>0" class="btn btn-primary btn-sm m-1" @click="getPreviousPage()">Previous page</button>
                    <span class="page_indicator">{{pageNumber+1}}/{{metadata.totalPages}}</span>
                </div>   

            </div>
        </div>
    </body>
        
</template>

<style scoped>
html{
    background-color: #effcfe;
}
body{
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
h2{
    margin-bottom: 25px;
    padding-top: 20px;
    padding-bottom: 20px;
    border-bottom: 2px solid #0079bb21;
    background-color: white;
    border: 2px solid white;
}
h3{
    font-size:larger ;
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
#pointer{
    cursor: pointer;
}
#pointer:hover{
    font-weight:800;
    background-color: #0077b6;
    color: #effcfe;
}
.page_indicator{
    color: #effcfe;
    background-color: #0077b6;
    font-weight: 400;
    font-size: medium;
    padding: 0.9%;
    border-radius: 10%;
}
.callout-note{     
  background: #f6f2fa;                    
  padding: .5rem;
}

</style>