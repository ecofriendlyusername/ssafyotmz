<template>
<div class="member" v-if="streamManager">
	<div id="profile" :class="{ 'active': isSpeaking }">
		<img :src='`${profileImage}`'>
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
		profileImage () {
			const { profileImage } = this.getConnectionData();
			return profileImage ? profileImage : 'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg';
		}
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
	width: 91%;
  height: 91%;
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