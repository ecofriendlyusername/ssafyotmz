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