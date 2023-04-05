<template>
    <div style="background-color:black; color: white; padding: 13px;">
        <br><br>

        <p style="display:flex; font-weight: bold;">스타일 진단 설문을 진행해주세요</p>
    
        <div style="display:flex; color: gray;margin-top: 20px;">
            O't MZ가 취향을 분석할 수 있도록
        </div>
        <div style="display:flex; color: gray; margin-top: 5px;">
            마음에 드는 스타일을 5개 이상 선택해주세요.
        </div>
        <br>


        <div style="display:grid; grid-template-columns: 1fr 1fr; gap: 5px; padding: 5px; ">
            <div v-for="data in dataList">
                <div v-on:click="check($event)" >
                    <div>
                        
                    </div><img :src='`${path}/images/${data.imageId}`' :alt="`${data.style}`" style="width:100%"> 
                </div>
            </div>
        </div>
        <br>

        <button v-on:click="sub()" id="surveyBtn">선택완료</button>
    </div>

    <!-- <div>{{ checked }}</div> -->
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
            // console.log(response.data)
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
                this.$store.state.Auth['first'] = false
            })
            .then(() => {
                this.$router.push('/')
            })
            .catch(error => {
                console.log(error)
                // console.log(sendData)
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
    opacity: 0.3;
}

#surveyBtn {
  background-color:#e63525;
  border: none;
  color:white;
  font-weight: bold;
  font-size: 85%;
  /* width: 60%; */
  padding: 15px 32px;
  border-radius: 15px;
  position: fixed;
  bottom:70px;
  left: 50%;
  transform: translate(-53%, 0);
  letter-spacing: 7px;
}
</style>