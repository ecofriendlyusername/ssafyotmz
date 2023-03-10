<template>
    <div id="sidebar">
        <div class="inner">

            <!-- Search -->
            <!-- <SearchComponentVue/> -->

            <!-- Menu -->
            <MenuComponentVue/>

            <!-- Section -->
            <!-- <SectionOneComponentVue/> -->

            <!-- Section -->
            <!-- <SectionTwoComponentVue/> -->

            <!-- Footer -->
            <!-- <FooterComponentVue/> -->

        </div>
    </div>
</template>

<script>
// import SearchComponentVue from './sidebar/SearchComponent.vue'
import MenuComponentVue from './sidebar/MenuComponent.vue'
// import FooterComponentVue from './sidebar/FooterComponent.vue'
// import SectionOneComponentVue from './sidebar/SectionOneComponent.vue'
// import SectionTwoComponentVue from './sidebar/SectionTwoComponent.vue'

export default {
    name: "SidebarComponentVue",
    components: {
        // SearchComponentVue,
        MenuComponentVue,
        // FooterComponentVue,
        // SectionOneComponentVue,
        // SectionTwoComponentVue,
    }
}
</script>


<style>

</style>