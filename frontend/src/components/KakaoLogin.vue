<template>
    <section>
        <p>1234</p>
        <button type="submit" v-on:click="loginWithKakao">login</button>
    </section>
</template>

<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js" integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>

<script>
	// $.cookie('kakaoUserSecedeAuth', null, {path:"/"});
	// $.cookie('kakaoUserEditAuth', null, {path:"/"});
</script>

<script>
export default {
name:'KakaoLoginVue',
    methods:{
        // 카카오 로그인
        loginWithKakao: function () {
            // $.cookie('kakao_prev_uri', '/users/login', {path:"/"});	// 카카오 호출 전 현재 페이지 저장

            Kakao.Auth.authorize({
                redirectUri : 'http://localhost:8080'
            });

        Kakao.API.request({
            url: '/v2/user/me',
            data: {
                property_keys: ['kakao_account.email', 'kakao_account.gender'],
                },
        }).then(function(response) {
            console.log(response);
        }).catch(function(error) {
            console.log(error);
            });
        }
    }
}
</script>

<style></style>