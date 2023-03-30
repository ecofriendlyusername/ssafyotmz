<template>
<div id="main-container">
  <div id="join" v-if="!session">
    <div id="join-dialog" class="jumbotron vertical-center">
      <h1>Join a video session</h1>
      <div class="form-group">
        <p>
          <label>Participant</label>
          <input v-model="myUserName" class="form-control" type="text" required />
        </p>
        <p>
          <label>Session</label>
          <input v-model="mySessionId" class="form-control" type="text" required />
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="joinSession()">
            Join!
          </button>
        </p>
      </div>
    </div>
  </div>

  <div id="session" v-if="session">
    <div id="session-header">
      <h1 id="session-title">{{ mySessionId }} 님의 옷장</h1>
        <div>
          라이브 코디북 만들기 페이지
        </div>
      <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession"
        value="Leave session" />
    </div>
    <hr>
    <div class="categories">
      <div class="category" :class="{ 'selected': selected === 'outer' }" @click="getItems('outer')">아우터</div>
      <div class="category" :class="{ 'selected': selected === 'upper' }" @click="getItems('upper')">상의</div>
      <div class="category" :class="{ 'selected': selected === 'lower' }" @click="getItems('lower')">하의</div>
      <div class="category" :class="{ 'selected': selected === 'dress' }" @click="getItems('dress')">원피스</div>
      <div class="category" :class="{ 'selected': selected === 'etc' }" @click="getItems('etc')">기타</div>
    </div>
    <div class="items" >
      <div v-for="item in items" @click="choice(item)" :key="item.id">
        <img :src='`${item.src}`' style="width:100px;hegiht:100px"/>
      </div>
    </div>
    <hr>
    <div style="text-align:left;">Participants</div>
    <div id="video-container">
      <user-video class="publisher" :stream-manager="publisher" :is-speaking="publisher.isSpeaking && audioEnabled" />
      <user-video class="subscriber" v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" :is-speaking="sub.isSpeaking"/>
    </div>
    <div v-show="audioEnabled" @click="micOnOff" style="width:50px;height:50px">
      <img src="https://collusic.com/assets/track/unselected/vocal.svg" >
    </div>
    <div v-show="!audioEnabled" @click="micOnOff" style="width:50px;height:50px">
      <img  src="https://collusic.com/assets/track/unselected/crap.svg" >
    </div>
    <hr>
    <div>
      <div class="settings">
        <div class="box">
          <span class="editor-btn icon" title="Color Picker">
            <input title="Color Picker" type="color" id="color" v-model="backgroundColor"  @blur="updateColor()">
          </span>
        </div>
        <div @click="clear">clear</div>
        <div v-if="dragItemId" @click="removeItem">삭제</div>
      </div>
      <div :style="{margin: '10px', backgroundColor: backgroundColor}">
        <v-stage
          ref="stage"
          :config="configKonva"
          @dragstart="handleDragstart"
          @dragend="handleDragend"
          @mousedown="handleMouseDown"
          @touchstart="handleMouseDown"
        >
          <v-layer ref="layer"
          :config="configKonva">
            <v-rect 
              id="background"
              :config="{
                x: 0,
                y: 0,
                width: 400,
                height: 400,
                fill: backgroundColor
              }"
            />
            <v-image :config="{
              x: 330,
              y: 330,
              width: 50,
              height: 50,
              image: logo
            }" />
            <v-image v-for="item in list" :key="item.id"
              :config="{
                name: item.name,
                image: item.image,
                x: item.x,
                y: item.y,
                width: item.width,
                height: item.height,
                rotation: item.rotation,
                scaleX: item.scaleX,
                scaleY: item.scaleY,
                id: item.id,
                numPoints: 5,
                innerRadius: 30,
                outerRadius: 50,
                draggable: item.dragable,
                shadowColor: 'black',
                shadowBlur: 10,
                shadowOffsetX: dragItemId === item.id ? 10 : 3,
                shadowOffsetY: dragItemId === item.id ? 10 : 3,
                shadowOpacity: 0.2,
              }"
              @transformend="handleTransformEnd"
            ></v-image>
            <v-transformer ref="transformer"/>
          </v-layer>
        </v-stage>
      </div>
    </div>
    <div @click="captureCodiBoard">캡처</div>
    <hr>
  </div>
</div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./UserVideo";

const width = 400;
const height = 400;

axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:8000/';

export default {
  name: 'MainComponent',

  components: {
    UserVideo,
  },

  data() {
    return {
      // OpenVidu objects
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      audioEnabled: true,

      // Join form
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),

      items: [],
      count: 0,
      list: [],
      dragItemId: null,
      configKonva: {
        width: width,
        height: height
      },
      backgroundColor: '#FFDAB9',
      logo: null,
      selected: 'outer'
    };
  },

  created() {
    const logoImage = new Image();
    logoImage.src = '/logo.png'
    logoImage.onload = () => {
      this.logo = logoImage;
    }
  },

  mounted() {
    this.backgroundColor = '#' + Math.floor(Math.random() * 16777215).toString(16);
  },

  methods: {
    captureCodiBoard() {
      const stage = this.$refs.stage.getNode();
      const dataURL = stage.toDataURL({ pixelRatio: 3 });
      var link = document.createElement('a');
      link.download = 'capture.png';
      link.href = dataURL;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },
    updateColor() {
      const color = this.backgroundColor;
      console.log(color);
      this.session.signal({
        data: JSON.stringify({backgroundColor: color}),
        to: this.subscribers,
        type: 'updateBackgroundColor'
      }).then(() => {
        console.log('Message successfully sent');
      })
      .catch(error => {
          console.error(error);
      });
    },
    removeItem() {
      if (this.dragItemId === null) {
        return;
      }
        const index = this.list.findIndex(i => i.name === this.dragItemId);
        this.list.splice(index, 1);
        this.dragItemId = null;
        this.updateTransformer();
        this.updateItemsOnBoard();
    },
    handleTransformEnd(e) {
      const item = this.list.find(i => i.name === this.dragItemId);
      item.x = e.target.x();
      item.y = e.target.y();
      item.rotation = e.target.rotation();
      item.scaleX = e.target.scaleX();
      item.scaleY = e.target.scaleY();
      item.width = e.target.width();
      item.height = e.target.height();
      this.updateItemsOnBoard();
    },
    handleMouseDown(e) {
      console.log('click', e.target)
      if (e.target.id() === 'background') {
        this.dragItemId = null;
        this.updateTransformer();
        return;
      }

      const clickedOnTransformer = e.target.getParent().className === 'Transformer';
      if (clickedOnTransformer) {
        this.updateItemsOnBoard();
        return;
      }

      const name = e.target.name();
      const item = this.list.find(i => i.name === name);
      if (item) {
        this.dragItemId = name;
        this.updateTransformer();
        const index = this.list.indexOf(item);
        this.list.splice(index, 1);
        this.list.push(item);
      } else {
        this.dragItemId = null;
      }

      this.updateItemsOnBoard();
    },
    clear() {
      this.list = []
      this.dragItemId = null;
      this.updateTransformer();
      this.updateItemsOnBoard();
    },
    handleDragstart(e) {
      // save drag element:
      this.dragItemId = e.target.id();
      // move current element to the top:
      const item = this.list.find(i => i.id === this.dragItemId);
      const index = this.list.indexOf(item);
      this.list.splice(index, 1);
      this.list.push(item);

      this.updateTransformer();
    },
    handleDragend(e) {
      const item = this.list.find(i => i.name === this.dragItemId);

      console.log(this.list.find(i => i.name === this.dragItemId));

      item.x = e.target.x();
      item.y = e.target.y();
      item.rotation = e.target.rotation();
      this.updateItemsOnBoard();
    },
    updateTransformer() {
      const transformerNode = this.$refs.transformer.getNode();
      const stage = transformerNode.getStage();
      const { dragItemId } = this;

      const selectedNode = stage.findOne('.' + dragItemId);
      if (selectedNode === transformerNode.node()) {
        return;
      }

      if (selectedNode) {
        transformerNode.nodes([selectedNode]);
      } else {
        transformerNode.nodes([]);
      }
    },
    choice(item) {
      const img = new Image();
      img.src = process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/images/' + item.imageId;
      img.crossOrigin = 'Anonymous';

      const targetIndex = this.list.findIndex(x => x.name === String(item.id));
      this.dragItemId = null;
      this.updateTransformer();

      if (targetIndex !== -1) {
        const target = this.list[targetIndex];
        this.list.splice(targetIndex, 1);
        this.list.push(target);
        return;
      }
    
      this.list.push({
        name: String(item.id),
        image: img,
        id: String(item.id),
        x: item.x ? item.x : 10,
        y: item.y ? item.y : 10,
        rotation: 0,
        width: item.width ? item.width : 200,
        height: item.height ? item.height : 200,
        scaleX: item.scaleX ? item.scaleX : 1,
        screenY: item.scaleY ? item.scaleY : 1,
        dragable: true
      });
    },
    getItems(category) {
      this.selected = category;
      axios.get(process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/items/' + this.$store.state.Auth.memberId + '/' + category +'?page=0&size=10', { // outer, upper, lower, dress, etc
        headers: {
          'Content-Type': 'application/json',
          'Authorization': this.$store.state.Auth['accessToken']
        }
      })
      .then((response) => {
        // 파일 저장하는 api 리턴값으로 파일 경로 달라고 해야 함
        // this.image = response.data
        console.log(response.data)
        this.items = response.data.content;
        this.items.forEach(item => item.src = process.env.VUE_APP_DEFAULT_API_URL + '/api/v1/images/' + item.imageId);
      })
      .catch(error =>{
        console.log(error)
      });
    },
    micOnOff() {
        this.audioEnabled = !this.audioEnabled;
        this.publisher.publishAudio(this.audioEnabled);
    },
    alertSpeaker(id, isSpeaking) {
      this.session.signal({
        data: JSON.stringify({id: id, isSpeaking: isSpeaking}),
        to: this.subscribers,
        type: 'alertSpeaker'
      }).then(() => {
        console.log('Message successfully sent');
      })
      .catch(error => {
          console.error(error);
      });
    },
    alertMoveItem(x, y) {
      this.session.signal({
        data: JSON.stringify({x: x, y: y}),
        to: this.subscribers,
        type: 'moveItem'
      }).then(() => {
        console.log('Message successfully sent');
      })
      .catch(error => {
          console.error(error);
      });
    },
    alertSetUpBoard() {
      console.log('items', this.list)
      const data = {items:[], backgroundColor: this.backgroundColor};
      this.list.forEach(i => {
        const item = Object.assign({}, i);
        item.image = i.image.src;
        data.items.push(item);
      });
      console.log(data);
      this.session.signal({
        data: JSON.stringify(data),
        to: this.subscribers,
        type: 'setUpBoard'
      }).then(() => {
        console.log('Message successfully sent');
      })
      .catch(error => {
          console.error(error);
      });
    },
    updateItemsOnBoard() {
      const data = [];
      this.list.forEach(i => {
        const item = Object.assign({}, i);
        item.image = i.image.src;
        data.push(item);
      });
      console.log('updateItemsOnBoard', data);
      this.session.signal({
        data: JSON.stringify(data),
        to: this.subscribers,
        type: 'updateItemsOnBoard'
      }).then(() => {
        console.log('Message successfully sent');
      })
      .catch(error => {
          console.error(error);
      });
    },
    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        subscriber.isSpeaking = false;
        this.subscribers.push(subscriber);
        if (this.mySessionId === this.myUserName) {
          this.alertSetUpBoard();
        }
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.mySessionId).then((token) => {

        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session.connect(token, { clientData: this.myUserName })
          .then(() => {

            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: false, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManager = publisher;
            this.publisher = publisher;
            this.publisher.isSpeaking = false;
            const { list } = this;

            // --- 6) Publish your stream ---

            this.session.publish(this.publisher);
            this.session.on('publisherStartSpeaking', (event) => {
                console.log('User ' + event.connection.connectionId + ' start speaking');
                this.publisher.isSpeaking = true;
                this.alertSpeaker(event.connection.connectionId, true);
            });

            this.session.on('publisherStopSpeaking', (event) => {
                console.log('User ' + event.connection.connectionId + ' stop speaking');
                this.publisher.isSpeaking = false;
                this.alertSpeaker(event.connection.connectionId, false);
            });

            this.session.on('signal:setUpBoard', (event) => {
              console.log('setUpBoard');
              const data = JSON.parse(event.data);
              console.log(data)
              this.backgroundColor = data.backgroundColor;
              data.items.forEach(x => {
                if (this.list.findIndex(i => i.name === x.name) === -1) {
                  const item = x;
                  const img = new Image();
                  img.src = x.image;
                  img.crossOrigin = 'Anonymous';
                  item.image = img;
                  list.push(item)
                }
              });
            })

            this.session.on('signal:updateItemsOnBoard', (event) => {
              console.log('updateItemsOnBoard', event.data);
              const data = JSON.parse(event.data);
              list.splice(0, list.length);
              data.forEach(x => {
                if (this.list.findIndex(i => i.name === x.name) === -1) {
                  const item = x;
                  const img = new Image();
                  img.src = x.image;
                  img.crossOrigin = 'Anonymous';
                  item.image = img;
                  list.push(item)
                }
              });
            })

            this.session.on('signal:moveItem', (event) => {
              console.log('moveItem');
              const data = JSON.parse(event.data);
              const clothes = document.getElementById('clothes1');
              clothes.style.left = data.x;
              clothes.style.top = data.y;
            });

            this.session.on('signal:updateBackgroundColor', (event) => {
              console.log('updateBackgroundColor');
              const data = JSON.parse(event.data);
              this.backgroundColor = data.backgroundColor;
            })

            this.session.on('signal:alertSpeaker', (event) => {
              console.log('alertSpeaker');
              const data = JSON.parse(event.data)
              console.log(data)
              console.log(this.subscribers)
              if (this.subscribers.length === 0) {
                return;
              }
              let speaker = this.subscribers.find(sub => {
                return sub.stream.connection.connectionId === data.id});
              console.log(speaker)
              if (speaker !== undefined) {
                speaker.isSpeaking = data.isSpeaking;
              }
            });
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      // Empty all properties...
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.audioEnabled = true;
      this.OV = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions', { customSessionId: sessionId }, {
        headers: { 'Content-Type': 'application/json', },
      });
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      const response = await axios.post(APPLICATION_SERVER_URL + 'api/sessions/' + sessionId + '/connections', {}, {
        headers: { 'Content-Type': 'application/json', },
      });
      return response.data; // The token
    },
  },
}
</script>

<style scoped>
.mouse-img{
    display: flex;
    width: 200px;
    height: 200px;
    position: absolute;
  }

.categories {
  display: flex;
  margin: 10px;
}

.category {
  margin: 0 5px;
}

.selected {
  background-color: black;
  color: white;
}

.items {
  display: flex;
}

.settings {
  display: flex;
  flex-direction: row-reverse;
  margin: 10px;
}

#video-container {
  display: flex;
}
</style>

