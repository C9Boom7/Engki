<template>
  <div class="quiz-board">
    <div class="row" v-for="j in 2" :key="j">
      <div
        class="col-4 imagebox"
        v-for="(data, i) in datas.images"
        v-if="parseInt(i / 3) == j - 1"
      >
        <img
          :src="'https://j3a510.p.ssafy.io/images/' + data.filePath"
          class="img"
          :class="{ selected: selects[i].selected }"
          @click="select(i)"
        />
      </div>
    </div>

    <div class="row">
      <div class="col-3"></div>
      <div class="col-6 quiz-text">
        {{ this.$store.state.quiz.word }}
      </div>
      <div class="col-3"></div>
    </div>
  </div>
</template>
<script>
import http from '../utils/http-common.js';
export default {
  props: ['isDone'],
  created() {
    this.isDone = false;
    if (this.$store.state.test_customizing) {
      // 부모 테스트 - 커스텀 퀴즈일 때
      this.quizapipath =
        '/custom/test/' +
        this.$store.state.test_customizing.image_id +
        '/' +
        this.$store.state.test_customizing.word +
        '/images/by/' +
        this.$store.state.parent.id;
    } else {
      if (this.$store.state.theme == 1) {
        // 커스텀 퀴즈
        this.quizapipath =
          '/custom/' +
          this.$store.state.quiz.word +
          '/images/by/' +
          this.$store.state.parent.id;
      } else {
        // 일반 퀴즈
        this.quizapipath = '/edu/' + this.$store.state.quiz.id + '/images';
      }
    }
    http
      .get(this.quizapipath, {
        headers: { 'X-AUTH-TOKEN': this.$store.state.token },
      })
      .then((data) => {
        this.$store.commit('setQuizB', data.data);
        this.setLocalVariable();
        // console.log(this.datas);
      });
  },
  watch: {
    isDone: function (val) {
      if (this.isCorrect()) {
        this.$store.commit('setExp', 2);
        this.$emit('correct');
      } else this.$emit('wrong');
    },
  },
  methods: {
    setLocalVariable() {
      this.datas = this.$store.state.quiz_b;
    },
    isCorrect() {
      var count = 0;
      for (var data in this.datas.images) {
        if (this.datas.images[data].selected) {
          count++;
          if (this.datas.images[data].word != this.$store.state.quiz.word) {
            this.$swal({
              title:
                '<div><span style="font-weight:100; font-size:2vw;">정답이 아닙니다.</span><br><span  style="font-weight:100; font-size:2vw;">다시 한번 생각해보세요.</span></div>',
              showCancelButton: false,
              confirmButtonText: '확인',
              timer: 1000,
            }).then((result) => {
              return true;
            });
            return false;
          }
        }
      }
      if (count != this.datas.answerNum) {
        this.$swal({
          title:
            '<div style="font-weight:100; font-size:2vw;"> ' +
            this.datas.answerNum +
            '개를 선택해주세요</div>',
          showCancelButton: false,
          confirmButtonText: '확인',
        }).then((result) => {});
        return false;
      }
      return true;
    },
    select(index) {
      this.datas.images[index].selected = !this.datas.images[index].selected;
      this.selects[index].selected = !this.selects[index].selected;
    },
  },
  data: function () {
    return {
      selectedIndex: -1,
      datas: [],
      selects: [
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
        {
          selected: false,
        },
      ],
    };
  },
};
</script>
<style lang="scss" scoped>
.quiz-board {
  margin-top: -3vh;
  // position:absolute;
  // top:7vh;
  // left:20vw;
  // transform: translate(0, -50%);
}
.quiz-text {
  margin-top: -3vh;
  font-size: 10vh;
}
.imagebox {
  padding: 0vw;
}
.img {
  // display:inherit;
  width: 13vw;
  height: 13vw;
  border-radius: 5vw;
  border: 1vw solid transparent;
  // background-color:yellow;
}
.selected {
  border: 0.5vw solid #f4b759;
}
</style>
