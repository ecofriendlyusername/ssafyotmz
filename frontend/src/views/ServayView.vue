<template>
    <ul>
        <li v-for="data in dataList">
            <div v-on:click="check($event)">
                <img :src='`${path}/images/${data.imageID}`' :alt="`${data.style}`">
                {{ data.style }}
            </div>
        </li>
    </ul>
    <button v-on:click="sub()">결정하기</button>
    <div>{{ checked }}</div>
</template>

<script>
import axios from "axios";

export default {
    name: 'servayVIew',
    data(){
        return {
            path: process.env.VUE_APP_API_URL,
            checked: {},
            dataList: [
                {'imageID':2, 'style':'country'},
                {'imageID':3, 'style':'country'},
                {'imageID':4, 'style':'street'},
                {'imageID':5, 'style':'country'},
                {'imageID':6, 'style':'modern'},
                {'imageID':7, 'style':'oriental'},
            ],
        }
    },
    mounted() {
        console.log('servay')
        axios.get(process.env.VUE_APP_API_URL + '/servay', {
            headers: {
                'Authorization': this.$store.state.Auth['accessToken']
            },
        })
        .then((response) => {
            console.log(response.data)
            this.dataList = response.data
        })
        .error(error => {
            console.log(error)
        })
    },
    methods: {
        sub(){

            const check = document.querySelectorAll('.checked')
            check.forEach(e=>{
                if (this.checked[e.alt]){
                    this.checked[e.alt] += 1
                }else{
                    this.checked[e.alt] = 1
                }
                
            })
            console.log(this.checked)

            // axios.post(process.env.VUE_APP_API_URL + '/servay', {
            //     headers: {
            //         'Authorization': this.$store.state.Auth['accessToken']
            //     },
            //     body: {
            //         data: JSON.stringify(this.checked)
            //     }
            // })
            // .then((response) => {
            //     console.log(response.data)
            //     // Auth check false save store
            //     // this.$store.state.Auth['check'] = false
            // })
            // .then(() => {
            //     this.$router.push('')
            // })
            // .error(error => {
            //     console.log(error)
            // })
        },
        async check(event){
            event.target.classList.toggle('checked')
        }
    }
}
</script>

<style>
.checked {
    border-block: solid red 10px;
}
</style>