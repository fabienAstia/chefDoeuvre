<script setup>
    import Chart from 'chart.js/auto';
    import { onMounted, computed } from 'vue';
    import { useMbtiStore } from '@/stores/mbtiStore';

    const mbtiStore = useMbtiStore();
    const mbtiType = computed(() => mbtiStore.result);

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
            data: [65, 59, 90, 81, 56, 55, 40, 43],
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
            plugins: {
                legend: {
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
    })

</script>


<template>
        <h1 class="text-center"><b class="style">{{ mbtiType.code}}</b></h1>
        <div class="d-flex justify-content-center">
            <img :src="`http://localhost:8080${mbtiType.image}`" class="mb-4" alt="mbtiType image">
        </div>
        <p class="text-center">{{ mbtiType.description }}</p>

        <div class="chart-container d-flex">
            <canvas id="myChart" style="width: 400px; height: 400px;"></canvas>
        </div>
</template>

<style scoped>
    .chart-container{
        width: 400px;
        height: 400px;
        justify-content: center;
    }
</style>