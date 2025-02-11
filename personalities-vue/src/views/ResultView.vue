<script setup>
    import { Chart } from 'chart.js';
    import { onMounted } from 'vue';
    import { useRoute } from 'vue-router';

    const route = useRoute();
    const mbtiType = JSON.parse(route.query.mbtiType);     

    const data = {
        labels: [
            'Eating',
            'Drinking',
            'Sleeping',
            'Designing',
            'Coding',
            'Cycling',
            'Running'
        ],
        datasets: [{
            label: 'My First Dataset',
            data: [65, 59, 90, 81, 56, 55, 40],
            fill: true,
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgb(255, 99, 132)',
            pointBackgroundColor: 'rgb(255, 99, 132)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgb(255, 99, 132)'
        }, 
        {
            label: 'My Second Dataset',
            data: [28, 48, 40, 19, 96, 27, 100],
            fill: true,
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgb(255, 99, 132)',
            pointBackgroundColor: 'rgb(255, 99, 132)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff'
        }
    ]}

    onMounted(async() => {
       myChart;
    })

    const context = document.getElementById('myChart'); 
    const config = {
        type: 'radar',
        data: data,
        options: {
            elements: {
                line: {
                    borderWidth: 3
                }
            }
        },
    };
    const myChart = new Chart(context, config);

</script>


<template>
        <h1 class="text-center"><b class="style">{{ mbtiType.code }}</b></h1>
        <div class="d-flex justify-content-center">
            <img :src="`http://localhost:8080${mbtiType.image}`" class="mb-4" alt="mbtiType image">
        </div>

        <div class="chart-container">
            <canvas id="myChart"></canvas>
        </div>
        <p class="text-center">{{ mbtiType.description }}</p>
</template>