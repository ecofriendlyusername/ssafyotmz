<template>
  <div>
    <footer>
        <div style="display:flex; justify-content:space-between; padding: 10px 20px;">
            <router-link to="/" style="text-decoration:none; color:white">
                <div>
                    <img src="@/assets/img/homeicon.png" style="width: 35px;">
                    <p id="footerText">홈</p>
                </div>
            </router-link>

            <router-link to='/Find/index' style="text-decoration:none; color:white">
                <div>
                    <img src="@/assets/img/styleicon.png" style="width: 35px;">
                    <p id="footerText">스타일 찾기</p>
                </div>
            </router-link>

            <router-link to="/MyPage/closet" style="text-decoration:none; color:white">
                <div>
                    <img src="@/assets/img/closeticon.png" style="width: 35px;">
                    <p id="footerText">옷장</p>
                </div>
            </router-link>

            <router-link to='/Codybook/solo' style="text-decoration:none; color:white">
                <div>
                    <img src="@/assets/img/bookicon.png" style="width: 35px;">
                    <p id="footerText">코디북</p>
                </div>
            </router-link>

            <router-link to="/Mypage" style="text-decoration:none; color:white">
                <div>
                    <img src="@/assets/img/myicon.png" style="width: 35px;">
                    <p id="footerText">마이페이지</p>
                </div>
            </router-link>

        </div>
    </footer>
  </div>
</template>

<script>
export default {

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
    font-size: 12px;
    font-weight: bold;
    font-family: 'NanumSquareNeo-Variable';
}

</style>