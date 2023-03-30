<template>
  <div>
    <div class="SubNav">
        <div class="SubNavText">
            WHAT’S MY STYLE
        </div>
    </div>

    <img src="@/assets/img/PictureIndex.png" id="PictureIndex">
    <br>
    <img src="@/assets/img/Arrow.png" id="Arrow">
    <br>
    <img src="@/assets/img/CLICK.png" style="width:35px;">
    <!-- <div id="ClickText">CLICK!</div> -->
    <router-link to="/Find" style="text-decoration:none">
        <div id="UploadText">UPLOAD <br> </div>
    </router-link>

    <div style="height:70px">
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style>
@font-face {
    font-family: 'TheJamsil5Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2') format('woff2');
    font-weight: 700;
    font-style: normal;
}

.SubNav {
    background-color: hwb(0 96% 4%);
}

.SubNavText {
    display: flex;
    justify-content: center;
    padding: 18px;
    font-size: 120%;
    font-weight: bold;
    font-family: 'TheJamsil5Bold';
    color: rgb(0, 0, 0);
}

#PictureIndex {
    margin-top: 4%;
    width: 95%;
}

#Arrow {
    width: 1.2%;
    height: 50px;
}

#UploadText {
    font-size: 180%;
    font-family: 'TheJamsil5Bold';
    font-weight: bold;
    margin-top: 5px;
    color: black;
    padding-bottom: 20px;
}
</style>