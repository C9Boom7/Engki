<template>
  <div class="background">
    <img
      v-if="!isChangeProfile"
      @click="startTutorial()"
      class="page-title-img"
      id="tutorial"
      src="../../public/img/icon/question-mark1.png"
    />
    <b-tooltip placement="top" target="tutorial" triggers="hover">
      <span
        style="font-family: GmarketSansMedium; color: #f2f2f2; font-size: 0.8vw"
        >도움말</span
      >
    </b-tooltip>
    <div class="box">
      <div class="innerbox">
        <div class="profile-icon">
          <img :src="kid.icon" />
          <div class="name">{{kid.name}}</div>
          <div class="bar" v-if="!isChangeProfile">
            <b-progress
              :value="value"
              :max="max"
              variant="success"
              show-progress
              class="mb-3"
            ></b-progress>
            <div style="margin-top: -0.5vw">level.{{ level + 1 }}</div>
          </div>
        </div>
        <div class="profile-icons">
          <table>
            <tbody id="contacts">
              <tr v-for="i in 5" :key="i" style="background-color: white">
                <td
                  v-for="j in 10"
                  :key="j"
                  :class="{ block: level < (i - 1) * 10 + (j - 1) }"
                  @click="selectProfileImage((i - 1) * 10 + (j - 1))"
                  style="
                    width: 7vw;
                    height: 2vw;
                    padding-top: 1vw;
                    padding-bottom: 1vw;
                    margin-right: 10vw;
                    margin-left: 10vw;
                  "
                >
                  <img
                    :class="{ active: selectedIndex == (i - 1) * 10 + (j - 1) }"
                    :src="fairytale[(i - 1) * 10 + (j - 1)]"
                    style="width: 52%"
                  />
                </td>
                <span style="width: 1vw"></span>
              </tr>
            </tbody>
          </table>
          <div class="change-button">
            <div class="button-1">
              <v-btn rounded style="font-size:3vh; margin:1vh; padding: 2vh 3vh;" @click="setProfileImage()">변경하기</v-btn>
            </div>
            <div class="button-2">
              <v-btn rounded style="font-size:3vh; margin:1vh;  padding: 2vh 3vh;" @click="returnKidPage()">돌아가기</v-btn>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 첫번째 튜토리얼 -->
    <b-modal
      modal-class="mymodal"
      ref="my-modal1"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>캐릭터 변경하기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
    >
      <div class="modal-body">
        <span>
          퀴즈를 풀고 얻은 경험치로 캐릭터를 변경해보세요.<br />
          경험치 100을 쌓으면 레벨업을 할 수 있습니다.<br />
          1부터 50까지의 레벨에 따라 캐릭터가 개방됩니다.
        </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 첫번째 튜토리얼 끝-->

    <!-- 두번째 튜토리얼 -->
    <b-modal
      :modal-class="profilemodal"
      ref="my-modal2"
      title-html="<span style='
  padding: 1vw;font-family: GmarketSansMedium; color: #263747;'>돌아가기</span>"
      :hide-footer="isHideFooter"
      header-border-variant="0"
      ><div class="arrow-left"></div>
      <div class="modal-body">
        <span> 이곳을 클릭하면 마이페이지로 돌아갈 수 있습니다.<br /> </span>
      </div>
      <div>
        <div class="modal-foot">{{ stage }}/{{ limit }}</div>
        <div class="modal-foot2">
          <b-button
            size="sm"
            variant="primary"
            :class="{ isButtonBlock: stage == 1 }"
            @click="prevTutorial(stage)"
          >
            &lt; 이전
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-if="stage < limit"
            @click="nextTutorial(stage)"
          >
            다음 >
          </b-button>
          <b-button
            size="sm"
            variant="primary"
            v-else
            @click="nextTutorial(stage)"
          >
            종료 >
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- 두번째 튜토리얼 끝-->
  </div>
</template>
<script>
import http from '../utils/http-common.js'
export default {
  name: 'SetProfileImage',
  props: {
    kid: null,
  },
  data: () => {
    return {
      fairytale: [
        '/img/icon/fairytale/001-knight.png',
        '/img/icon/fairytale/002-wizard.png',
        '/img/icon/fairytale/003-dwarf.png',
        '/img/icon/fairytale/004-elf.png',
        '/img/icon/fairytale/005-witch.png',
        '/img/icon/fairytale/006-ogre.png',
        '/img/icon/fairytale/007-giant.png',
        '/img/icon/fairytale/008-gnome.png',
        '/img/icon/fairytale/009-little red riding hood.png',
        '/img/icon/fairytale/010-wolf.png',
        '/img/icon/fairytale/011-queen.png',
        '/img/icon/fairytale/012-king.png',
        '/img/icon/fairytale/013-princess.png',
        '/img/icon/fairytale/014-prince.png',
        '/img/icon/fairytale/015-frog prince.png',
        '/img/icon/fairytale/016-fairy.png',
        '/img/icon/fairytale/017-robin hood.png',
        '/img/icon/fairytale/018-pirate.png',
        '/img/icon/fairytale/019-goblin.png',
        '/img/icon/fairytale/020-elf.png',
        '/img/icon/fairytale/021-pig.png',
        '/img/icon/fairytale/022-tin man.png',
        '/img/icon/fairytale/023-scarecrow.png',
        '/img/icon/fairytale/024-cowardly lion.png',
        '/img/icon/fairytale/025-pinocchio.png',
        '/img/icon/fairytale/026-puss in boots.png',
        '/img/icon/fairytale/027-mad hatter.png',
        '/img/icon/fairytale/028-cyclops.png',
        '/img/icon/fairytale/029-white rabbit.png',
        '/img/icon/fairytale/030-mermaid.png',
        '/img/icon/fairytale/031-genie.png',
        '/img/icon/fairytale/032-vampire.png',
        '/img/icon/fairytale/033-unicorn.png',
        '/img/icon/fairytale/034-dragon.png',
        '/img/icon/fairytale/035-phoenix.png',
        '/img/icon/fairytale/036-poison.png',
        '/img/icon/fairytale/037-poison.png',
        '/img/icon/fairytale/038-cauldron.png',
        '/img/icon/fairytale/039-poisonous.png',
        '/img/icon/fairytale/040-magic mirror.png',
        '/img/icon/fairytale/041-excalibur.png',
        '/img/icon/fairytale/042-glass shoes.png',
        '/img/icon/fairytale/043-magic lamp.png',
        '/img/icon/fairytale/044-crystal ball.png',
        '/img/icon/fairytale/045-ring.png',
        '/img/icon/fairytale/046-broom.png',
        '/img/icon/fairytale/047-magic wand.png',
        '/img/icon/fairytale/048-tower.png',
        '/img/icon/fairytale/049-castle.png',
        '/img/icon/fairytale/050-fairytale.png',
      ],
      selectedIndex: 0,
      level: 0,
      value: 0,
      max: 100,
      timer: null,
      isChangeProfile: false,
      stage: 1,
      limit: 2,
      isHideFooter: true,
      profilemodal: ['profilemodal'],
    }
  },
  mounted() {
    this.selectedIndex = this.fairytale.indexOf(this.$store.state.kid.icon)
    this.level = Math.ceil(this.kid.exp / 100 - (this.kid.exp % 100) / 100)
    this.timer = setInterval(() => {
      if (this.value == this.kid.exp % 100) {
        clearInterval(this.timer)
        this.timer = null
      } else this.value = this.value + 1
    }, 30)
  },
  methods: {
    prevTutorial(stage) {
      if (stage > 1) {
        this.$refs['my-modal' + stage].hide()
        this.stage--
        this.showTutorial(this.stage)
      }
    },
    nextTutorial(stage) {
      this.$refs['my-modal' + stage].hide()
      if (stage < this.limit) {
        this.stage++
        this.showTutorial(this.stage)
      }
    },
    showTutorial(index) {
      this.$refs['my-modal' + index].show()
    },
    startTutorial() {
      this.stage = 1
      this.$refs['my-modal1'].show()
    },
    returnKidPage() {
      this.$emit('returnKidPage', this.fairytale[this.selectedIndex])
    },
    setProfileImage() {
      this.$swal({
        title: '선택된 아바타로 변경하시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '네',
        cancelButtonText: '아니오',
        showCloseButton: true,
      }).then((result) => {
        if (result.value) {
          http
            .put(
              'kids/' + this.kid.id,
              {
                icon: this.fairytale[this.selectedIndex],
              },
              {
                headers: { 'X-AUTH-TOKEN': this.$store.state.token },
              }
            )
            .then(({ data }) => {
              this.$store.state.kid.icon = this.fairytale[this.selectedIndex]
              this.returnKidPage()
            })
        }
      })
    },
    selectProfileImage(num) {
      if (this.level >= num) {
        this.selectedIndex = num
        this.profileImage = this.fairytale[this.selectedIndex]
      }
    },
  },
}
</script>

<style>
.profilemodal > div {
  position: absolute !important;
  bottom: 3vh !important;
  left: 15vw !important;
}
</style>
<style lang="scss">
@import '../assets/sass/base.scss';
</style>
<style lang="scss" scoped>
/* 로그인페이지 틀 */
.background .box .innerbox {
  position: inherit;
}
.profile-icon {
  opacity: 0.8;
  border-radius: 50%;
  background: white;
  width: 15%;
  height: 15%;
  top: 18vh;
  left: 50vw;
  position: relative;
  box-shadow: 3px 3px 33px 4px rgba(163, 163, 163, 0.404);
  transform: translate(-50%, -50%);
}

.profile-icon img {
  padding: 7%;
  width: 72%;
  height: 72%;
  opacity: 1;
  object-fit: cover;
}
.profile-icon .name {
  margin-top: -1vw;
  font-size:3vh;

}
.profile-icon .bar {
  position: relative;
  transform: translate(25%, 0%);
  width: 68%;
}
.profile-icons {
  margin-top: -2%;
  margin-left: 10%;
  margin-right: 10%;
  background-color: white;
  height: 70%;
  border-radius: 10vh;
  padding: 5vh 1vw 1vh 5vw;
}

.profile-icons .change-button {
  position: relative;
  display: inline-block;
}
.button-1 {
  float: left;
  margin-left: -3.5vw;
  margin-right: 3.5vw;
}
.button-2 {
  float: left;
}
.active {
  border: 3px solid #8ebf43;
  border-style: dotted;
}

.block {
  opacity: 0.3;
}

.page-title-img {
  position: absolute;
  top: 0px;
  right: 0px;
  width: 3%;
  margin-top: 3vh;
  margin-right: 3vw;
  &:hover {
    opacity: 0.6;
  }
  z-index: 1000;
}

.isButtonBlock {
  opacity: 0.6;
  pointer-events: none;
}
.modal-body {
  margin-top: -2vw;
  margin-bottom: 1vw;
}

.modal-body span {
  color: #263747;
  opacity: 0.9;
  font-family: GmarketSansMedium;
}
.modal-foot {
  float: left;
  color: gray;
  padding-left: 1vw;
  font-family: GmarketSansMedium;
}
.modal-foot2 {
  float: right;
  font-family: GmarketSansMedium;
}
.mymodal > div {
  position: fixed !important;
  top: 0 !important;
  left: -23vw !important;
}


</style>
