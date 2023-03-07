<template>
    <section>
        <p>1234</p>
        <button type="submit"></button>
        
    </section>
</template>

<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.1.0/kakao.min.js"
    integrity="sha384-dpu02ieKC6NUeKFoGMOKz6102CLEWi9+5RQjWSV0ikYSFFd8M3Wp2reIcquJOemx" crossorigin="anonymous"></script>
<script>
    // SDK를 초기화 합니다. 사용할 앱의 JavaScript 키를 설정해야 합니다.


</script>
<script>
export default {
    name:'KakaoLoginVue',
    methods:{
        log: function () {
            Kakao.init("d351596851492e4d0a8828d5497305e8");

            // SDK 초기화 여부를 판단합니다.
            console.log(Kakao.isInitialized());
        }
    }
}
</script>

<style></style>