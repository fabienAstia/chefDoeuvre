<script setup>
    import { Chart } from 'chart.js';
    import { onMounted } from 'vue';
    import { useRoute } from 'vue-router';

    const route = useRoute();
    const mbtiType = JSON.parse(route.query.mbtiType);     

//     document.addEventListener('DOMContentLoaded', function () {
//     const ctx = document.getElementById('myChart').getContext('2d');
//     const myChart = new Chart(ctx, {
//         type: 'bar',
//         data: {
//             labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
//             datasets: [{
//                 label: '# of Votes',
//                 data: [12, 19, 3, 5, 2, 3],
//                 backgroundColor: [
//                     'rgba(255, 99, 132, 0.2)',
//                     'rgba(54, 162, 235, 0.2)',
//                     'rgba(255, 206, 86, 0.2)',
//                     'rgba(75, 192, 192, 0.2)',
//                     'rgba(153, 102, 255, 0.2)',
//                     'rgba(255, 159, 64, 0.2)'
//                 ],
//                 borderColor: [
//                     'rgba(255, 99, 132, 1)',
//                     'rgba(54, 162, 235, 1)',
//                     'rgba(255, 206, 86, 1)',
//                     'rgba(75, 192, 192, 1)',
//                     'rgba(153, 102, 255, 1)',
//                     'rgba(255, 159, 64, 1)'
//                 ],
//                 borderWidth: 1
//             }]
//         },
//         options: {
//             scales: {
//                 y: {
//                     beginAtZero: true
//                 }
//             }
//         }
//     });
// });

    // const data = {
    //     labels: [
    //         'Eating',
    //         'Drinking',
    //         'Sleeping',
    //         'Designing',
    //         'Coding',
    //         'Cycling',
    //         'Running'
    //     ],
    //     datasets: [{
    //         label: 'My First Dataset',
    //         data: [65, 59, 90, 81, 56, 55, 40],
    //         fill: true,
    //         backgroundColor: 'rgba(255, 99, 132, 0.2)',
    //         borderColor: 'rgb(255, 99, 132)',
    //         pointBackgroundColor: 'rgb(255, 99, 132)',
    //         pointBorderColor: '#fff',
    //         pointHoverBackgroundColor: '#fff',
    //         pointHoverBorderColor: 'rgb(255, 99, 132)'
    //     }, 
    //     {
    //         label: 'My Second Dataset',
    //         data: [28, 48, 40, 19, 96, 27, 100],
    //         fill: true,
    //         backgroundColor: 'rgba(255, 99, 132, 0.2)',
    //         borderColor: 'rgb(255, 99, 132)',
    //         pointBackgroundColor: 'rgb(255, 99, 132)',
    //         pointBorderColor: '#fff',
    //         pointHoverBackgroundColor: '#fff'
    //     }
    // ]}

    // const context = document.getElementById('myChart').getContext('2d'); 
    // const config = {
    //     type: 'radar',
    //     data: data,
    //     options: {
    //         elements: {
    //             line: {
    //                 borderWidth: 3
    //             }
    //         }
    //     },
    // };

    // const myChart = new Chart(context, config);

    // onMounted(async() => {
    //  myChart;
    // })


  

</script>


<template>
        <h1 class="text-center"><b class="style">{{ mbtiType.code }}</b></h1>
        <div class="d-flex justify-content-center">
            <img :src="`http://localhost:8080${mbtiType.image}`" class="mb-4" alt="mbtiType image">
        </div>

        <!-- <div class="chart-container">
            <canvas id="myChart"></canvas>
        </div> -->
        <p class="text-center">{{ mbtiType.description }}</p>
</template>