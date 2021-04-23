<template>
    <div>
       <el-form :model="loginForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
         <el-form-item label="用户名" prop="userId">
          <el-input v-model="ruleForm.userId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
export default {

};
</script>
