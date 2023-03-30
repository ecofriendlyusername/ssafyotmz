<template>
  <div>
    <footer>
        <div style="display:flex; justify-content:space-between; padding: 10px;">
            <router-link to="/" style="text-decoration:none; color:white" v-on:click="clickhome">
                <div >
                    <img v-if="this.home=='false'" src="@/assets/img/homeicon.png" class="icon">
                    <img v-if="this.home=='true'" src="@/assets/img/clickhome.png" class="icon">
                    <p id="footerText">홈</p>
                </div>
            </router-link>

            <router-link to='/Find/index' style="text-decoration:none; color:white" v-on:click="clickstyle">
                <div>
                    <img v-if="this.style=='false'" src="@/assets/img/styleicon.png" class="icon">
                    <img v-if="this.style=='true'" src="@/assets/img/clickstyle.png" class="icon">
                    <p id="footerText">스타일 찾기</p>
                </div>
            </router-link>

            <router-link to="/MyPage/closet" style="text-decoration:none; color:white" v-on:click="clickcloset">
                <div>
                    <img v-if="this.closet=='false'" src="@/assets/img/closeticon.png" class="icon">
                    <img v-if="this.closet=='true'" src="@/assets/img/clickcloset.png" class="icon">
                    <p id="footerText">옷장</p>
                </div>
            </router-link>

            <router-link to='/MyPage/codybook' style="text-decoration:none; color:white" v-on:click="clickbook">
                <div>
                    <img v-if="this.book=='false'" src="@/assets/img/bookicon.png" class="icon">
                    <img v-if="this.book=='true'" src="@/assets/img/clickbook.png" class="icon">
                    <p id="footerText">코디북</p>
                </div>
            </router-link>

            <router-link to="/Mypage" style="text-decoration:none; color:white" v-on:click="clickmy">
                <div>
                    <img v-if="this.my=='false'" src="@/assets/img/myicon.png" class="icon">
                    <img v-if="this.my=='true'" src="@/assets/img/clickmy.png" class="icon">
                    <p id="footerText">마이페이지</p>
                </div>
            </router-link>

        </div>
    </footer>
  </div>
</template>

<script>
export default {
    data() {
        return {
            home: "true",
            style: "false",
            closet: "false",
            book: "false",
            my: "false",
        }
    },
    methods: {
        clickhome() {
            this.home="true"
            this.style="false"
            this.closet="false"
            this.book="false"
            this.my="false"
        },
        clickstyle() {
            this.style="true"
            this.home="false"
            this.closet="false"
            this.book="false"
            this.my="false"
        },
        clickcloset() {
            this.closet="true"
            this.home="false"
            this.style="false"
            this.book="false"
            this.my="false"
        },
        clickbook() {
            this.book="true"
            this.home="false"
            this.style="false"
            this.closet="false"
            this.my="false"
        },
        clickmy() {
            this.my="true"
            this.home="false"
            this.style="false"
            this.closet="false"
            this.book="false"
        },

    }

}
</script>

<style>
footer {
    width: 100%;
    max-width: 420px;
    position: fixed;
    bottom: 0;
    height: 80px;
    width: 100%;
    background-color: rgb(255, 255, 255);
    color: rgb(0, 0, 0);
    border-top: rgb(220, 220, 220) solid 0.1px;
}

#footerText{
    margin-top: 3px;
    color: black;
    font-size: 55%;
    font-weight: bold;
    font-family: 'NanumSquareNeo-Variable';
}

.icon {
    width: 31%;
    margin-top: 5px;
}

</style>