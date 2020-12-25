<template>
    <div class="video-container"></div>
</template>

<script>
    export default {
        data () {
            return {
                userId: '',
                beforeUnloadTime: '',
                gapTime: ''
            }
        },
        mounted () {
            window.addEventListener('beforeunload', e => this.beforeunloadHandler(e))
            window.addEventListener('unload', e => this.unloadHandler(e))
        },
        destroyed() {
            window.removeEventListener('beforeunload', e => this.beforeunloadHandler(e))
            window.removeEventListener('unload', e => this.unloadHandler(e))
        },
        methods: {
            beforeunloadHandler(){
                this.beforeUnloadTime=new Date().getTime();
            },
            unloadHandler(e){
                this.gapTime=new Date().getTime()-this.beforeUnloadTime;
                //判断是窗口关闭还是刷新
                if(this.gapTime<=5){
                    debugger
                    alert("aaaaaaaaaaaaaaaaaaa");
                   /* var img = document.createElement("img");
                    //src是自己的后端地址以及参数(参数看自己需求)
                    img.src = this.$http.adornUrl(`/sys/user/updateUserInfo?userId=${this.userId }&token=${this.$cookie.get('token')}`);
                    //将img加到body中时就会立刻发送请求
                    document.body.appendChild(img);*/
                }else {
                    debugger
                    alert("aaaaaaaaaaaaaaaaaaa");
                }
            }
        }
    }
</script>

<style scoped>

</style>