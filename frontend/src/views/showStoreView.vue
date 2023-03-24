<template>

 <div v-for="item in status">
    <p>{{ item }}</p>
 </div>
</template>

<script>
export default {
    data() {
        return {
            status: null
        }
    },
    mounted() {
        this.status = this.$store.state
    }
}
</script>

<style>

</style>