## 외부 API를 이용한 날씨 앱 만들기
OpenWeatherMap 사이트를 사용
- 회원가입 후 API -> Current Weather Data -> Subscribe 선택
- Get APU key
- 발급받은 API 키를 이용해 날씨 정보를 받아와 화면에 뿌려주기

```vue
<script>
export default {
  data: function () {
    return {
      api_key: "YOUR_API_KEY_HERE",
      url_base: "https://api.openweathermap.org/data/2.5/",
      query: "",
      weather: {},
    };
  },
  methods: {
    fetchWeather: function (e) {
      if (e.key == "Enter") {
        let fetchUrl = `${this.url_base}weather?q=${this.query}&units=metric&APPID=${this.api_key}`;
        fetch(fetchUrl)
          .then((res) => {
            console.log(res);
            return res.json();
          })
          .then((results) => {
            return this.setResult(results);
          });
      }
    },
    setResult: function (results) {
      this.weather = results;
    },
    dateBuilder: function () {
      let d = new Date();
      let months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
      let days = [
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
      ];
      let day = days[d.getDay()];
      let date = d.getDate();
      let month = months[d.getMonth()];
      let year = d.getFullYear();
      return `${day} ${date} ${month} ${year}`;
    },
  },
};
</script>
```

*참고 : https://smoh.tistory.com/352

### 체감온도
: 바람이 많이 불수록 추위를 느끼고
습도가 높을수록 더위를 크게 느끼는 원리

오늘의 날씨 정보에 풍속, 습도 정보를 참고해서 실질적인 온도별 옷차림을 고려하는게 좋음.

체감온도 산출표 figma 참고

### 기온별 옷차림 추천
~5
: 겨울 옷

6~9
: 코트, 가죽자켓

10~11
: 트렌치코드, 간절기 야상, 여러겹 껴입기

12~16
: 기모후드티, 가디건, 니트/맨투맨

17~19
: 후드티, 바람막이, 슬랙스

20~22
: 셔츠, 7부바지, 면바지

23~27
: 티셔츠, 반바지

28~
: 린넨, 민소매, 숏팬츠