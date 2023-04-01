<template>
<div class="member" v-if="streamManager">
	<div id="profile" :class="{ 'active': isSpeaking }">
		<img src="https://k.kakaocdn.net/dn/CezCL/btrKatXtJFO/8X2UHivEyLVjnx9wZyHM9K/img_110x110.jpg">
	</div>
	<ov-video :stream-manager="streamManager"/>
	<p>{{ clientData }}</p>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
		isSpeaking: Boolean,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>

<style>
#profile {
	width: 60px;
	height: 60px;
	border-radius: 50%;
	border: 1px solid #e1e2e3;
	display: -ms-flexbox;
	display: flex;
	-ms-flex-pack: center;
	justify-content: center;
	-ms-flex-align: center;
	align-items: center;
	overflow: hidden;
}

#profile > img {
	width: 55px;
  height: 55px;
  border-radius: 50%;
  background-position: 50%;
  background-size: cover;
  background-repeat: no-repeat;
}

.member {
	text-align: center;
	margin: 0 2px;
  font-size: 12px;
}

.active {
  background-color: blueviolet;
}
</style>