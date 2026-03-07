<template>
  <el-row>
    <h3>记录</h3>
    <el-form ref="logAddForm" :model="form" label-width="80px" :span="18" style="width: 80%;" prop="form">
      <el-form-item label="姓名">
        <el-input v-model="form.userName" prop="userName"></el-input>
      </el-form-item>
      <el-form-item label="日期">
        <el-date-picker type="date" placeholder="选择日期" v-model="form.workDate"
                        value-format="yyyy-MM-dd HH:mm:ss" style="width: 100%;" prop="workDate"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="归属版本">
        <el-select v-model="form.workVersion" style="width: 100%;">
          <el-option v-for="(e,i) in attr.workVersion" :label="e.text" :value="e.code" :key="'wv'+i"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工时">
        <el-input v-model="form.workLength"></el-input>
      </el-form-item>

      <el-form-item label="工作内容">
        <el-input v-model="form.workContent"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="form.workNote"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">立即创建</el-button>
        <el-button type="reset" @click="resetF">重置</el-button>
        <el-button @click="listQuery">加载</el-button>
      </el-form-item>
    </el-form>

    <h3>列表</h3>

    <work-table/>
  </el-row>

</template>

<script>
import WorkTable from "@/views/work/WorkTable";

export default {
  data() {
    return {
      form: {
        userName: '',
        userCode: '',
        workDate: '',
        workLength: '',
        workContent: '',
        workVersion: '',
        workNote: ''
      },
      attr: {workVersion: [{code: '', text: '请选择'}]},
      list: []
    };
  },
  methods: {
    onSubmit() {
      this.$reqUtil.postReq("/workLog", this.form, this.resetF);
    }, resetF() {
      // this.$refs['logAddForm'].resetFields();
      Object.assign(this.$data.form, this.$options.data().form)
      // Object.assign(this.$data, this.$options.data())
    }, listQuery() {
      this.$reqUtil.getReq("workLog", {userCode: 'admin'}, (data) => this.list = data);
    }
  },
  components: {WorkTable},
  created() {
    this.$reqUtil.getReq("/attr/work_version", {}, (data) => this.attr.workVersion = data.values);
  }
}
</script>

<style>

</style>