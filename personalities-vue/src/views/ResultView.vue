<script setup>
    import Chart from 'chart.js/auto';
    import { onMounted, computed } from 'vue';
    import { useMbtiStore } from '@/stores/mbtiStore';

    const mbtiStore = useMbtiStore();
    const mbtiType = computed(() => mbtiStore.result);

    const sortedTraits = () => {
        const goodTraits = new Array();
        const badTraits = new Array();
        for(const[trait,evaluation] of Object.entries(mbtiType.value.personalityTraitsWithEval)){
            if(evaluation==='Force'){
                goodTraits.push(trait);
            }else{
                badTraits.push(trait);
            }
        }
        return {goodTraits, badTraits};
    }

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
            data: [33, 59, 90, 81, 56, 55, 40, 43],
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
                    pointLabels: {
                        display: true // Hides the labels around the radar chart 
                    },
                    ticks: {
                        display: false // Hides the labels in the middle (numbers)
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
        console.log("rateByPsych :" + Object.entries(mbtiType.value.rateByPsych.get('Intuition')))
    })

</script>

<template>
    <body class="container-fluid">
        <div class="row my-3">
            <div class="col text-center">
                <h1 class="text-shadow mb-5"><b>Vos résultats</b></h1>
            </div>
        </div>
        <div class="row align-items-center my-3">
            <h3 class="text-center text-shadow-light">Présentation</h3>
            <hr>
            <div class="d-flex col-12 col-md-6 justify-content-center fs-6 fs-md-5" >
                <ul class="fs-6 fs-md-5">
                    <li class="my-1 text-center"><span class="text-shadow-light"><b>{{ mbtiType.code }} - {{ mbtiType.name }}</b></span></li>
                    <li class="my-1">{{ mbtiType.description }}</li>
                    <li class="my-1">{{ mbtiType.populationPercentage }}% of the population</li>
                    <li class="my-1">{{ mbtiType.interestingFact }}</li>
                </ul>
            </div>
            <div class="d-flex col-12 col-md-6 justify-content-center">
                <img class="img-fluid" :src="`src/assets/images/mbtiTypes/${mbtiType.image}`" alt="mbtiType image" id="image">
            </div>
        </div>

        <div class="row align-items-center my-3">
            <h3 class="text-center text-shadow-light">Vos résultats</h3>
            <hr>
            <div class="d-flex col-12 col-md-6 justify-content-center" >
                <canvas id="myChart"></canvas> 
            </div>
            <div class="d-flex col-12 col-md-6 justify-content-center">
                <ul>
                    <li class="my-1">Intoversion / Extraversion - {{ Object.entries(mbtiType.rateByPsych) }}</li>
                    <li class="my-1">Intuition / Sensation</li>
                    <li class="my-1">Pensée / Sentiment</li>
                    <li class="my-1">Perception / Jugement</li>
                </ul>
            </div>
        </div>

        <div class="row text-center">
            <h3 class="text-shadow-light">Forces et Faiblesses</h3>
            <hr>
            <div class="col-12 col-md-6">
                    <h5 class="text-shadow-light my-3">Forces</h5>
                    <div class="mb-1" v-for="trait in sortedTraits().goodTraits" :key="trait">{{ trait }}</div>
            </div>
            <div class="col-12 col-md-6">
                    <h5 class="text-shadow-light my-3">Faiblesses</h5>
                    <div class="mb-1" v-for="trait in sortedTraits().badTraits" :key="trait">{{ trait }}</div>
            </div>
        </div>

        <div class="row mt-3">
            <div class="col">
                <h4 class="text-center text-shadow-light my-3">Métiers</h4>
                <hr>
                <div class="text-center mb-1" v-for="job in mbtiType.professions">{{ job }}</div>
            </div>
        </div>
    </body>
        
</template>

<style scoped>
body{
    max-width: 900px!important;
    background-color: #effcfe;;
    padding: 15px;
}
ul{
    padding: 0;
}
li{
    list-style-type: none;
}
h1{
    font-size: 4em;
    color: #0077b6;
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
}

</style>