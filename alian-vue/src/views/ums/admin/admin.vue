<template>
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float:right"
                        type="primary"
                        @click="handleSearchList()"
                        size="small">
                    查询搜索
                </el-button>
                <el-button
                        style="float:right;margin-right: 15px"
                        @click="handleResetSearch()"
                        size="small">
                    重置
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="输入搜索：">
                        <el-input v-model="listQuery.username" class="input-width" placeholder="帐号/姓名"
                                  clearable></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button size="mini" class="btn-add" @click="handleAdd()" style="margin-left: 20px">添加</el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="adminTable"
                      :data="list"
                      style="width: 100%;"
                      v-loading="listLoading" border>
                <el-table-column label="编号" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="帐号" align="center">
                    <template slot-scope="scope">{{scope.row.username}}</template>
                </el-table-column>
                <el-table-column label="姓名" align="center">
                    <template slot-scope="scope">{{scope.row.nickName}}</template>
                </el-table-column>
                <el-table-column label="邮箱" align="center">
                    <template slot-scope="scope">{{scope.row.email}}</template>
                </el-table-column>
                <el-table-column label="添加时间" width="160" align="center">
                    <template slot-scope="scope">{{scope.row.createTime}}</template>
                </el-table-column>
                <el-table-column label="最后登录" width="160" align="center">
                    <template slot-scope="scope">{{scope.row.loginTime}}</template>
                </el-table-column>
                <el-table-column label="是否启用" width="140" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                @change="handleStatusChange(scope.row)"
                                :active-value="1"
                                :inactive-value="0"
                                v-model="scope.row.status">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini"
                                   type="text"
                                   @click="handleSelectRole(scope.row)">分配角色
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   @click="handleUpdate(scope.row)">
                            编辑
                        </el-button>
                        <el-button size="mini"
                                   type="text"
                                   @click="handleDelete(scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="pagination-container">
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handlePageChange"
                    layout="total, sizes,prev, pager, next,jumper"
                    :current-page.sync="listQuery.current"
                    :page-size="listQuery.size"
                    :page-sizes="[5,10,15]"
                    :total="total">
            </el-pagination>
        </div>
        <el-dialog
                :title="isEdit?'编辑用户':'添加用户'"
                :visible.sync="dialogVisible"
                width="40%">
            <el-form :model="adminForm"
                     ref="adminForm"
                     :rules="rules"
                     label-width="150px" size="small">
                <el-input type="hidden" v-model="adminForm.id" style="width: 250px"></el-input>
                <el-form-item label="帐号：" prop="username">
                    <el-input v-model="adminForm.username" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="姓名：">
                    <el-input v-model="adminForm.nickName" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="邮箱：">
                    <el-input v-model="adminForm.email" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="密码：">
                    <el-input v-model="adminForm.password" type="password" style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="备注：">
                    <el-input v-model="adminForm.note"
                              type="textarea"
                              :rows="5"
                              style="width: 250px"></el-input>
                </el-form-item>
                <el-form-item label="是否启用：">
                    <el-radio-group v-model="adminForm.status">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="0">否</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleDialogConfirm()" size="small">确 定</el-button>
      </span>
        </el-dialog>
        <el-dialog
                title="分配角色"
                :visible.sync="allocDialogVisible"
                width="30%">
            <el-select v-model="adminRoleIds" multiple placeholder="请选择" size="small" style="width: 80%">
                <el-option
                        v-for="item in adminRoleList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>
            <span slot="footer" class="dialog-footer">
        <el-button @click="allocDialogVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleAllocDialogConfirm()" size="small">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>


<script>

    import {
        fetchAdminById,
        fetchAdminList,
        fetchUpdateAdmin,
        fetchCreateAdmin,
        fetchUpdateStatus,
        fetchDeleteAdminById,
        fetchRoleByAdmin,
        fetchAllRole,
        createRoleById,
        usernameValidate
    } from "@/api/ums/admin";


    export const defaultListQuery = {
        username: '',
        size: 5,
        current: 1
    }

    export const defaultAdmin = {
        id: null,
        username: '',
        password: '',
        nickName: '',
        email: '',
        note: '',
        status: 1,
        hidden: 0
    }

    export default {
        name: "admin",
        data() {
            return {
                listQuery: Object.assign({}, defaultListQuery),
                list: [],
                pageSizes: [5, 10, 15, 20],
                total: null,
                listLoading: false,
                dialogVisible: false,
                adminForm: Object.assign({}, defaultAdmin),
                isEdit: false,
                allocDialogVisible: false,
                adminRoleIds: [],
                adminRoleList: [],
                adminId: null,
                allList: [],
            }

        },
        methods: {
            getList() {
                fetchAdminList(this.listQuery).then(result => {
                    if (result.code === 200) {
                        this.list = result.data.records;
                        this.total = result.data.total;
                    }
                })
            },
            //新增用户
            handleAdd() {
                this.dialogVisible = true;
            },
            handleUpdate(row) {
                fetchAdminById(row.id).then(result => {
                    this.adminForm = result.data
                })
                this.isEdit = true;
                this.dialogVisible = true;
                this.admin = Object.assign({}, row);
            },
            handleResetSearch() {
                this.listQuery = Object.assign({}, defaultListQuery);
                this.getList();
            },
            handlePageChange(val) {
                this.$set(this.listQuery, 'current', val);
                this.getList();
            },
            handleSizeChange(val) {
                this.$set(this.listQuery, 'current', 1);
                this.$set(this.listQuery, 'size', val);
                this.getList();
            },
            handleSearchList() {
                this.listQuery.current = 1;
                this.getList();
            },
            handleStatusChange(row) {
                fetchUpdateStatus(row.id, row.status);
            },


            handleSelectRole(row){
                this.adminId = row.id;
                this.allocDialogVisible = true;
                this.getRoleListByAdmin(row.id);
            },

            getAllRole(){
                fetchAllRole().then(result =>{
                    this.adminRoleList = result.data;
                })
            },

            getRoleListByAdmin(adminId) {
                fetchRoleByAdmin(adminId).then(response => {
                    let allocRoleList = response.data;
                    this.adminRoleIds=[];
                    debugger;
                    if(allocRoleList!=null&&allocRoleList.length>0){
                        for(let i=0;i<allocRoleList.length;i++){
                            this.adminRoleIds.push(allocRoleList[i].roleId);
                        }
                    }
                });
            },
            handleDelete(row) {
                this.$confirm('是否要删除该用户?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    fetchDeleteAdminById(row.id).then(result => {
                        if(result.code === 200){
                            this.$message({
                                message: '删除成功！',
                                type: 'success'
                            });
                        }else {
                            this.$message.error("删除失败")
                        }
                        this.getList()

                    })
                })
            },
            handleDialogConfirm() {
                if (this.isEdit) {
                    fetchUpdateAdmin(this.adminForm).then(response => {
                        this.$message({
                            message: '修改成功！',
                            type: 'success'
                        });
                        this.dialogVisible = false;
                        this.getList();
                    })
                } else {
                    this.adminForm.password = this.$md5(this.adminForm.password)
                    fetchCreateAdmin(this.adminForm).then(response => {
                        this.$message({
                            message: '添加成功！',
                            type: 'success'
                        });
                        this.dialogVisible = false;
                        this.getList();
                    })
                }
            },
            handleAllocDialogConfirm(){
                this.$confirm('是否要确认?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.allList = [];
                    for (let i = 0; i < this.adminRoleIds.length; i++) {
                        this.allList.push({
                            roleId: this.adminRoleIds[i],
                            adminId: this.adminId
                        })
                    }
                    createRoleById(this.adminId,this.allList).then(result => {
                        if(result.code === 200){
                            this.$message({
                                message: '分配成功！',
                                type: 'success'
                            });
                            this.allocDialogVisible = false;
                        }

                    })
                })
            }
        },
        watch: {
            'adminForm.username': function (newValue, oldValue) {
                usernameValidate(newValue).then(result =>{
                    if(result.data != null){
                        this.$message.error("用户名已存在")
                    }

                })
            }
        },
        mounted() {
            this.getList();
            this.getAllRole();
        },

    }
</script>

<style scoped>

</style>