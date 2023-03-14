<template>
  <div>
    <p>{{ count }}</p>
    <div v-if="Message === 'hello'">
      <p>Hello World!</p>
    </div>
    <div v-else-if="Message === 'goodbye'">
      <p>Goodbye World!</p>
    </div>
    <div v-else>
      <p>No message to display.</p>
    </div>
  </div>
</template>

<script>
export default {
    name:'resultVue',
    computed: {
    ...mapState(['count']) // Vuex의 state에서 count 변수를 불러옵니다.
  }
}
</script>

<style>

</style>