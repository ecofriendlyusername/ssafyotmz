<template>
  <div>
      <div id="StyleHead">
        WHAT’S MY STYLE
      </div>

    <div id="InnerCard2"> 
      <!-- 워터마크 찍은 이미지 사이즈 맞춰서 넣기 -->
        <img style="width:100%; border-radius: 20px; max-height: 325px; " :src="imgsrc" />
      </div>
    
      <!-- 스타일 출력 -->
      <!-- 스타일 별 설명 값 넣기 -->
      <div id="ResultText">
        <div id="ResultStyle">
          <span>#{{ result['data']['1'].style.toUpperCase()}}</span> &nbsp;&nbsp;
          <span>#{{ result['data']['2'].style.toUpperCase()}}</span> &nbsp;&nbsp;
          <span>#{{ result['data']['3'].style.toUpperCase()}}</span>
        </div>

        <div id="ExpBox">
            <p>{{ explanation[result['data']['1'].style][0] }}</p>
            <p>{{ explanation[result['data']['1'].style][1] }}</p>
        </div>
        <!-- <h3>#{{ result['data']['1'].style}} #{{ result['data']['2'].style}} #{{ result['data']['3'].style}}</h3> -->
      </div>
    </div>


      <div style="display:flex; justify-content: center;">
        <!-- 카카오 링크 공유 연결 -->
            <div v-on:click=shareKakao() id="kakaotalk-sharing-btn" class="ResultBtn">
              <div>
                <img src="@/assets/img/link.png" style="width:20px; margin-top: 4px;">
              </div>
              &nbsp;&nbsp;
              <div>
                스타일 공유하기
              </div>
            </div>

            <div class="ResultBtn" v-on:click="$router.push('/Find')">
              <div>
                <img src="@/assets/img/restart.png" style="width:20px; margin-top: 4px;">
              </div>
              &nbsp;&nbsp;
              <div>
                다시하기
              </div>
            </div>
      </div>


      
      <router-link to='/Recom' style="text-decoration:none; color:black">
        <div style="display:flex; justify-content:center; margin-top: 12px;">
          <div id="GoStyle">
            <p style="font-size:90%">
              더욱 다양한 스타일을 즐겨보세요
            </p>
            <p style="font-size:85%">
              Go to another style
            </p>
          </div>
        </div>
      </router-link>
      
    
        <!-- <div v-on:click=shareKakao() id="kakaotalk-sharing-btn">스타일 공유하기</div> | -->
        <!-- <router-lin to='/Find'>다시하기</router-link> -->

</template>

<script>
export default {
  name:'FindResultView',
  data(){
    return {
      initkakao:false,
      imgsrc: null,
      explanation: {
        'romantic': ['부드럽고 낭만적인 느낌을 기본으로 하는 스타일입니다.', 
        '소녀적인 분위기의 테마로 연약한 색상인 페일(pale)톤이 자주 사용됩니다.'],
        'feminine': ['여성만을 위한 가장 여성스러움을 연출할 수 있는 스타일입니다.',
        '시폰, 레이스, 리본 등으로 우아함을 나타낸 것이 특징입니다.'],
        'street': ['한정판 제품, 스케이트 문화와 연결되는 스타일입니다.', 
        '또한 길거리 패션답게 새로운 트렌드를 반영하기도 합니다.'],
        'country': ['목가적인 낭만, 자연미와 같은 서민적인 감성을 표현합니다.', 
        '내츄럴한 분위기의 색상이 주로 사용됩니다.'],
        'resort': ['호화로운 휴가를 떠올리게 하는 스타일입니다.', 
        '세련된 편안함이 특징으로 선글라스와 어울립니다.'],
        'retro': ['복고적인 느낌을 주는 스타일입니다.', 
        '빨간 립스틱과 복고풍 헤어스타일 등 옛 시절을 떠올리게 하는 스타일입니다.'],
        'classic': ['시간이 지나도 언제나 가치 있을 패션 스타일입니다.', 
        '심플하고 고급스러운 주얼리, 세련된 메이크업&헤어를 활용하세요.'],
        'sexy': ['여성의 인체 곡선을 드러나게 하여 성적인 매력을 강조한 스타일입니다.', 
        '란제리 겉옷화 등 다양한 디자인을 시도할 수 있습니다.'],
        'hippie': ['물질적인 것에 집착하지 않고, 자유로운 라이프스타일을 표방합니다.', 
        '자연스러운 머리와 스카프, 무늬가 그려진 아이템을 활용하세요.'],
        'modern': ['단순함과 간결함을 지향하며, 심플하고 베이직한 스타일입니다.', 
        '어딘가 모르게 차가운 느낌이면서 심플한 느낌이에요. 오피스룩을 떠올려보세요.'],
        'sophisticated': ['도시적인 세련된 아름다움을 지닌 전문직 여성의 패션 스타일입니다.', 
        '인공적으로 잘 다듬어진 세련미,성숙미를 표현합니다.'],
        'kitsch': ['힙하고 개성 있는 유니크한 장난꾸러기 스타일입니다.', 
        '독특한 디자인, 캐릭터 등이 가미된 아이템을 활용합니다.'],
        'avantgarde': ['격식과 전통에 영향을 받지 않고 새로운 것을 창조하는 스타일입니다.', 
        '유행에 앞선 독창적이고 기발한 디자인이 특징입니다.'],
        'western': ['카우보이나 개척자들이 착용한 복장으로 활동미와 야성적 이미지를 표현하는 스타일입니다.', 
        '프린지 디테일, 올 데님, 웨스턴 부츠 등을 주로 홀용합니다.'],
        'oriental': ['동양적인 신비를 나타낸 스타일입니다.', 
        '마레이온이나 마아크릴, 자카드 등 광택이 좋은 소재를 주로 사용합니다. 포인트 매치에 활용해보세요.'],
        'preppy': ['북미의 명문 대학과 유치원,사립학교에서 발전한 스타일입니다.', 
        '부유한  느낌을 주며, 특정 브랜드나 학교 등을 나타내는 작은 로고가 있는 옷이 많습니다.'],
        'sporty': ['기능성과 활동성을 가미하여 활동적인 운동감을 표현하는 스타일입니다.',
        '대비색으로 배색하거나 포인트 컬러로 스포티함을 강조할 수 있습니다.'],
        'hiphop': ['헐렁하고 편안한 스타일입니다. 다양한 코디와 액세서리로 세련된 연출을 할 수 있습니다.', 
        '실용성과 기능성을 가미해 누구든지 편하게 입을 수 있습니다.'],
        'manish': ['남성복 디자인을  여성복에 적용해 여성다운 감각으로 표현한 스타일입니다.', 
        '타이, 셔츠 등에 섬세한 액세서리를 달아 오히려 강한 여성의 매력을 표현합니다.'],
        'tomboy': ['소년스러운 느낌을 주는 스타일입니다.', 
        '전통적인 남성복의 실루엣을 추구하며, 루즈한 핏과 어두운 색상이 특징입니다.'],
        'genderless': ['남성적인 면과 여성적인 면을 하나로 통합시킨 양성성을 표현한 스타일입니다.', 
        '남녀 구분 없이 모두 착용할 수 있으며 다채로운 스타일링이 가능합니다.'],
        'punk': ['주류 문화에 대한 반항과 저항을 나타내는 스타일입니다.', 
        '반항적이고 창의적인 느낌을 주며, 도발적인 문구가 적힌 찢어지거나 닳은 티셔츠, 가죽 재킷 등이 사용됩니다.'],
        'military': ['군복풍의 요소를 디자인에 도입한 스타일입니다.', 
        '직선적이고 기능적･활동적인 것이 많습니다. 견장이나 브레이드, 금속 단추 등으로 군복풍을 강조하기도 합니다.']
      }
    }
  },
  methods:{
    async shareKakao(){
      await Kakao.Share.createCustomButton({
        container: '#kakaotalk-sharing-btn',
        templateId: 91677,
        templateArgs: {
          img_path: process.env.VUE_APP_API_URL + '/image' + this.result['imageId'],
          username: this.$store.state.Auth.nickname,
          style: this.result['data']['1']['style'],
        },
      })
    }
  },
  computed: {
    result() {
      return this.$store.state.result
    }
  },
  mounted() {
    console.log(this.result)
    this.imgsrc = URL.createObjectURL(this.result['img_path']);
    Kakao.init(process.env.VUE_APP_KAKAO_JAVASCRIPT_API_URL);
    // 여기 아래에 워터마크 찍는 코드 붙여야 함

  }

}
</script>

<style>
#ResultText {
  font-family: 'NanumSquareNeo-Variable';
  color: black;
}

.ResultBtn {
  font-family: 'NanumSquareNeo-Variable';
  align-items: center;
  background-color: #F5F5F7;
  border-radius: 14px;
  width: 32%;
  font-weight: bold;
  font-size: 75%;
  padding: 6px;
  margin: 5px;
  box-shadow: 5px 5px 5px rgb(192, 192, 192);
  color: black;
  display:flex; justify-content: center;
}

#GoStyle {
  font-family: 'NanumSquareNeo-Variable';
  background-color: #F5F5F7;
  border-radius: 18px;
  width: 70%;
  font-weight: bold;
  padding: 5px;
  box-shadow: 5px 5px 5px rgb(192, 192, 192);
  line-height: 7px; 
}

#StyleHead {
  margin-top: 20px;
  font-family: 'NanumSquareNeo-Variable';
  display: inline-block;
  background-color:black;
  color: white;
  width: 80%;
  padding: 8px;
  font-weight: bold;
  box-shadow: 2px 2px 2px 2px gray;
}

#InnerCard2 {
  display: flex;
  object-fit: cover;
  margin-top: 5%;
  margin-left: 10.5%;
  width:80%;
  height: 325px;
  background-color: white;
  border: 1px solid #a7a7a7;
  border-radius: 20px;
}

#ResultStyle {
  background-color: black;
  color: white;
  display: inline-block;
  width: 80%;
  padding: 8px;
  font-size:110%;
  font-weight: bold;
  margin-top: 7px;
}


#ExpBox {
  display:inline-block;
  width:85%;
  font-size: 90%;
  font-weight: bold;
  margin-bottom: 10px;
}
</style>