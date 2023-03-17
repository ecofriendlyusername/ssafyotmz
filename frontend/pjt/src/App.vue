<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      
      <v-container>
        <v-layout align-center>

          <v-flex xs3 class="text-xs-right">
            <router-link to="/">
              <v-img class="shrink mr-2" contain src="@/assets/img/logo_RB.png" transition="scale-transition" width="60"/>
            </router-link>
          </v-flex>

          <v-flex xs3 justify-center>
            <p>현재 기능</p>
          </v-flex>

          <v-flex xs3 justify-center>
            <v-menu offset-y style="min-width: 2000px;">
              <template v-slot:activator="{ on }">
                <v-btn v-on="on" text>드롭다운</v-btn>
              </template>
              <v-list dense>
                <v-list-item>
                  <v-list-item-title>유저정보</v-list-item-title>
                </v-list-item>
                <hr>
                <v-list-item router to="/">
                  <v-list-item-title>Home</v-list-item-title>
                </v-list-item>
                <v-list-item router to="/about">
                  <v-list-item-title>About</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-flex>

        </v-layout>

      </v-container>

    </v-app-bar>

    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({

  }),
  methods: {

  },
};
</script>
