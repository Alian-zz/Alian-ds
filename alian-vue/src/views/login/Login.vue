<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">Alain</div>
            <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" placeholder="username">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                            type="password"
                            placeholder="password"
                            v-model="loginForm.password"
                            @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm">登录</el-button>
                </div>
                <p class="login-tips">Tips : 用户名和密码随便填。反正你也进不去</p>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {loginSubmit} from "../../api/login/login";

    export default {
        data: function () {
            let checkUserName = (rule, value, callback) => {
                let nameVerify = /^[A-Za-z]{3,12}$/;
                if (nameVerify.test(value) === false) {
                    return callback(new Error("用户名为3-12位英文字母"))
                } else {
                    return callback();
                }
            };
            let checkPassword = (rule, value, callback) => {
                let passwordVerify = /^[A-Za-z0-9]{3,15}$/;
                if (passwordVerify.test(value) === false) {
                    return callback(new Error("密码为3-12位英文字母或数字"))
                } else {
                    return callback();
                }
            };

            return {
                loginForm: {
                    username: '',
                    password: '',
                },
                rules: {
                    username: [{validator: checkUserName, required: true, trigger: 'blur'}],
                    password: [{validator: checkPassword, required: true, trigger: 'blur'}],
                },
            };
        },
        methods: {
            submitForm() {
                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        loginSubmit(this.loginForm.username,this.loginForm.password).then(result => {
                            if (result.code !== 200) {
                                this.$message.error(result.msg);
                            } else {
                                this.$message.success('登录成功');
                                localStorage.setItem('token', result.data);
                                this.$router.replace('/');
                            }
                        })
                    } else {
                        this.$message.error('请输入账号和密码');
                        return false;
                    }
                });
            },
        },
    };
</script>


<style scoped>
    .login-wrap {
        position: relative;
        width: 100%;
        height: 100%;
        background-image: url(../../assets/img/login-bgi.png);
        background-size: 100%;
    }

    .ms-title {
        width: 100%;
        line-height: 50px;
        text-align: center;
        font-size: 20px;
        color: #fff;
        border-bottom: 1px solid #ddd;
    }

    .ms-login {
        position: absolute;
        left: 50%;
        top: 50%;
        width: 350px;
        margin: -190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(255, 255, 255, 0.3);
        overflow: hidden;
    }

    .ms-content {
        padding: 30px 30px;
    }

    .login-btn {
        text-align: center;
    }

    .login-btn button {
        width: 100%;
        height: 36px;
        margin-bottom: 10px;
    }

    .login-tips {
        font-size: 12px;
        line-height: 30px;
        color: #fff;
    }
</style>
