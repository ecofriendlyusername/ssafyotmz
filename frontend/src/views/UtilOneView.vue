<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <v-dropdown class="profile-update-btn" :noPadding="true" :zIndex="0">
      <div>프로필 수정</div>
      <template #portal>
        <div class="drop item1" @click.self="$emit('open')">프로필 사진 수정</div>
        <div class="drop item2" @click.self="$emit('openBack')">배경 화면 수정</div>
      </template>
    </v-dropdown>

  </div>
</template>

<script>
// @ is an alias to /src


export default {
  name: 'UtilOneView',
  components: {

  }
}
</script>
