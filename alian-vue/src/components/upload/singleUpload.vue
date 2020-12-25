<template>
  <el-upload
    class="avatar-uploader"
    action="/api/upload/uploadPhoto"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <img v-if="imageUrl" :src="imageUrl" class="avatar">
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
</template>

<script>
    export default {
        name: "singleUpload",
        props:{
          value:String,
        },
        computed:{
          imageUrl(){
            return this.value;
          }
        },
        data() {
            return {

            }
        },
        methods: {
          handleAvatarSuccess(res, file) {
            this.imageUrl = res.data;
            this.emitInput(res.data);
          },
          beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
              this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
              this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
          },
          emitInput(val){
            this.$emit("input",val)
          }
        },
        mounted() {

        }
    }
</script>

<style>

 /* .el-upload {
    !* 图片在方框内显示长边 *!
    object-fit: contain;
  }

  .el-upload {
  border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }

   .avatar {
   width: auto;
     height: auto;
     max-width: 100%;
     max-height: 100%;
     display: block;
   }


  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }*/

 .el-upload { /*这是上传图片父容器。*/
 border: 1px dashed #d9d9d9;
   border-radius: 6px;
   cursor: pointer;
   position: relative;
   overflow: hidden;
   display: flex;
   justify-content: space-around;
   align-items: center;
 }

/* 2. 修改图片大小样式。*/
  .avatar { /*这是上传图片子容器。*/
  width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    display: block;
  }


</style>
