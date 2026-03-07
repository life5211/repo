<template>
  <el-container>
    <el-header>
      <el-row :gutter="20">
        <el-col :span="12">
          <h3 class="grid-content bg-purple">系统</h3>
        </el-col>
        <el-col :span="10" :offset="2">
          <el-row>
            <el-form
                :inline="true"
                :model="userInfo"
                class="demo-form-inline"
                size="mini"
            >
              <el-form-item>
                <el-input
                    v-model="userInfo.username"
                    placeholder="Username"
                    :disabled="isLogin"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-input
                    v-model="userInfo.userCode"
                    placeholder="UserCode"
                    :disabled="isLogin"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click=" onLogin(); isLogin = !isLogin; "
                >{{ isLogin ? "注销" : "登录" }}
                </el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </el-col>
      </el-row>
    </el-header>

    <el-container>
      <el-aside width="200px">
        <el-row class="tac">
          <h2>菜单</h2>

          <el-menu class="el-menu-vertical-demo">
            <el-menu-item :click="void(0)" v-for="(e, i) in menus" :key="'menu'+i">
              <i class="el-icon-menu"></i>
              <router-link :to="e.href" tag="span" slot="title">
                {{ e.title }}
              </router-link>
            </el-menu-item>
            <el-menu-item index="1-1">选项1</el-menu-item>
          </el-menu>
        </el-row>
      </el-aside>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>

    <el-footer>
      <slot name="footer"></slot>
    </el-footer>
  </el-container>
</template>

<script>
import util from "../utils/commonUtil";

export default {
  data() {
    return {
      menus: [
        {title: "记录日志", href: "add"},
        {title: "日志列表", href: "list"},
      ],
      userInfo: {username: "", userCode: ""},
      isLogin: false,
    };
  },
  methods: {
    onLogin: function () {
      if (this.isLogin) {
        this.userInfo = {username: "", userCode: ""};
        util.localSet("userInfo", null);
      } else {
        util.localSet("userInfo", this.userInfo);
      }
    },
  },
  created() {

    let userInfo = util.localGet("userInfo");
    if (userInfo) {
      this.userInfo = userInfo;
      this.isLogin = true;
    }
  },
};
</script>

<style scoped>
el-input {
  width: 100px;
}
</style>
