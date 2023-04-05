<template>
    <ul>
        <li v-for="data in dataList">
            <div v-on:click="check($event)">
                <img :src='`${path}/images/${data.imageId}`' :alt="`${data.style}`"> 
            </div>
        </li>
    </ul>
    <button v-on:click="sub()">결정하기</button>
    <div>{{ checked }}</div>
</template>

<script>
import axios from "axios";

export default {
    name: 'surveyVIew',
    data(){
        return {
            path: process.env.VUE_APP_API_URL,
            checked: {},
            dataList: [],
        }
    },
    mounted() {
        console.log(this.$store.state.Auth['accessToken'])
        axios.get(process.env.VUE_APP_API_URL + '/survey', {
            headers: {
                'Authorization': this.$store.state.Auth['accessToken']
            },
        })
        .then((response) => {
            console.log(response.data)
            this.dataList = response.data
        })
        .catch(error => {
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
                e.classList.toggle('checked')
            })
            console.log(this.checked)
            const sendData = []
            for (var key in this.checked) {
                sendData.push({"style": key, "count": this.checked[key]})
            }
            this.checked = {}
            axios.post(process.env.VUE_APP_API_URL + '/survey', sendData, {
                headers: {
                    'Authorization': this.$store.state.Auth['accessToken']
                }
            })
            .then((response) => {
                console.log(response.data)
                // Auth check false save store
                // this.$store.state.Auth['check'] = false
            })
            .then(() => {
                this.$router.push('')
            })
            .catch(error => {
                console.log(error)
                console.log(sendData)
            })
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