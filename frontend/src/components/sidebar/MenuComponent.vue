<template>
    <nav id="menu">
        <header class="major">
            <h2>Menu</h2>
        </header>
        <ul>
            <li><router-link to="/">Homepage</router-link></li>
            <li><router-link to="/generic">Generic</router-link></li>
            <li><router-link to="/elements">Elements</router-link></li>
            <li>
                <span class="opener">Submenu</span>
                <ul>
                    <li><a href="#">Lorem Dolor</a></li>
                    <li><a href="#">Ipsum Adipiscing</a></li>
                    <li><a href="#">Tempus Magna</a></li>
                    <li><a href="#">Feugiat Veroeros</a></li>
                </ul>
            </li>
            <li><a href="#">Etiam Dolore</a></li>
            <li><a href="#">Adipiscing</a></li>
            <li>
                <span class="opener">Another Submenu</span>
                <ul>
                    <li><a href="#">Lorem Dolor</a></li>
                    <li><a href="#">Ipsum Adipiscing</a></li>
                    <li><a href="#">Tempus Magna</a></li>
                    <li><a href="#">Feugiat Veroeros</a></li>
                </ul>
            </li>
            <li><a href="#">Maximus Erat</a></li>
            <li><a href="#">Sapien Mauris</a></li>
            <li><a href="#">Amet Lacinia</a></li>
        </ul>
    </nav>
</template>


<script>


</script>
<script>
export default {
    name:'MenuComponentVue',

}
</script>

<style>

</style>