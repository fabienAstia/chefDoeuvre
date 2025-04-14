<script setup>
    import Chart from 'chart.js/auto';
    import { onMounted, computed } from 'vue';
    import { useMbtiStore } from '@/stores/mbtiStore';

    const mbtiStore = useMbtiStore();
    const mbtiType = computed(() => mbtiStore.result);

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
            data: [percentages.value["N"], percentages.value["T"], percentages.value["E"], percentages.value["P"], percentages.value["S"], percentages.value["F"], percentages.value["I"], percentages.value["J"]],
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

</script>

<template>
    <body class="container-fluid">
        <div class="row my-3">
            <div class="col text-center">
                <h1 class="text-shadow mb-5"><b>Vos résultats</b></h1>
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
                <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${mbtiType.image}`" alt="mbtiType image" id="image">
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
                            <div class="progress-bar" :style="{ width: `${percentages.I}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{percentages["I"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["E"] }}%</span>
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
                            <div class="progress-bar" :style="{ width: `${percentages.N}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{percentages["N"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["S"] }}%</span>
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
                            <div class="progress-bar" :style="{ width: `${percentages.T}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{ percentages["T"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["F"] }}%</span>
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
                            <div class="progress-bar" :style="{ width: `${percentages.P}%` }"></div>
                            <span class="position-absolute w-100 text-white text-start">{{ percentages["P"] }}%</span> 
                            <span class="position-absolute w-100 text-white text-end"> {{ percentages["J"] }}%</span>
                        </div>
                    </li>
                </ul>
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
            <div class="col">
                <div class="text-center mb-1" v-for="job in mbtiType.professions">{{ job }}</div>
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