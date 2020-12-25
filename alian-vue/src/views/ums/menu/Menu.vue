<template>
    <div class="app-container">
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets" style="margin-top: 5px"></i>
            <span style="margin-top: 5px">数据列表</span>
            <el-button
                    class="btn-add"
                    @click="handleAddMenu()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="menuTable"
                      style="width: 100%"
                      :data="list"
                      border>
                <el-table-column label="编号" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="菜单名称" align="center">
                    <template slot-scope="scope">{{scope.row.title}}</template>
                </el-table-column>
                <el-table-column label="菜单级数" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.level | levelFilter}}</template>
                </el-table-column>
                <el-table-column label="前端名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="前端图标" width="100" align="center">
                    <template slot-scope="scope">
                        <svg-icon :icon-class="scope.row.icon"></svg-icon>
                    </template>
                </el-table-column>
                <el-table-column label="是否显示" width="100" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                @change="handleHiddenChange(scope.$index, scope.row)"
                                :active-value="0"
                                :inactive-value="1"
                                v-model="scope.row.hidden">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="排序" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.sort }}</template>
                </el-table-column>
                <el-table-column label="设置" width="120" align="center">
                    <template slot-scope="scope">
                        <el-button
                                v-if="scope.row.level === 0"
                                size="mini"
                                icon="el-icon-caret-bottom"
                                :disabled="scope.row.level | disableNextLevel"
                                @click="handleChildren(scope.$index, scope.row)"
                        >查看下级
                        </el-button>

                        <el-button
                                size="mini"
                                v-else
                                icon="el-icon-caret-top"
                                @click="handleParent(scope.$index, scope.row)"
                        >返回上级
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                type="text"
                                @click="handleUpdate(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="text"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="pagination-container">
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes,prev, pager, next,jumper"
                    :page-size="listQuery.size"
                    :page-sizes="[10,15,20]"
                    :current-page.sync="listQuery.current"
                    :total="total">
            </el-pagination>
        </div>

        <!-- 新增/修改弹出框 -->
        <el-dialog :title="title" :visible.sync="editVisible" width="50%">
            <el-form ref="menuForm" :model="menuForm" label-width="40%" v-if="editVisible">
                <el-form-item label="菜单名称：" prop="title">
                    <el-input v-model="menuForm.title"></el-input>
                </el-form-item>
                <el-form-item label="上级菜单：">
                    <el-select v-model="menuForm.parentId"
                               placeholder="请选择菜单">
                        <el-option
                                v-for="item in MenuList"
                                :key="item.id"
                                :label="item.title"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="前端名称：" prop="name">
                    <el-input v-model="menuForm.name"></el-input>
                </el-form-item>
                <el-form-item label="前端图标：" prop="icon">
                    <el-input v-model="menuForm.icon" style="width: 80%"></el-input>
                    <svg-icon style="margin-left: 8px" :icon-class="menuForm.icon"></svg-icon>
                </el-form-item>
                <el-form-item label="是否显示：">
                    <el-radio-group v-model="menuForm.hidden">
                        <el-radio :label="0">是</el-radio>
                        <el-radio :label="1">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序：">
                    <el-input v-model="menuForm.sort"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit('menuForm')">提交</el-button>
                    <el-button v-if="!isEdit" @click="resetForm('menuFrom')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>


    </div>
</template>

<script>
    import {
        deleteMenuById,
        fetchMenu,
        fetchMenuList,
        getMenuById,
        saveOrUpdateMenu,
        updateHiddenById
    } from "@/api/ums/menu";

    const defaultMenu = {
        title: '',
        parentId: 0,
        name: '',
        icon: '',
        hidden: 0,
        sort: 0
    };

    export default {
        name: "Menu",
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                list: [],
                total: null,
                listQuery: {
                    size: 10,
                    level: 0,
                    parentId: null,
                    current: 1
                },
                rules: {
                    title: [
                        {required: true, message: '请输入菜单名称', trigger: 'blur'},
                        {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                    ],
                    name: [
                        {required: true, message: '请输入前端名称', trigger: 'blur'},
                        {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                    ],
                    icon: [
                        {required: true, message: '请输入前端图标', trigger: 'blur'},
                        {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
                    ]
                },
                title: "菜单信息",
                editVisible: false,
                menuForm: Object.assign({}, defaultMenu),
                MenuList: [],
            }
        },
        methods: {
            getList() {
                fetchMenuList(this.listQuery).then(result => {
                    if (result.code === 200) {
                        this.list = result.data.records;
                        this.total = result.data.total;
                    }

                });
            },
            handleSizeChange(val) {
                this.listQuery.current = 1;
                this.listQuery.size = val;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listQuery.current = val;
                this.getList();
            },
            handleChildren(index, row) {
                this.$set(this.listQuery, 'parentId', row.id);
                this.$set(this.listQuery, 'level', null);
                this.$set(this.listQuery, 'current', 1);
                this.getList();

            },
            handleParent(index, row) {
                this.$set(this.listQuery, 'parentId', null);
                this.$set(this.listQuery, 'level', 0);
                this.$set(this.listQuery, 'current', 1);
                this.getList();
            },
            handleAddMenu(){
                this.menuForm = Object.assign({}, defaultMenu);
                this.selectMenu();
                this.editVisible = true;
            },
            selectMenu(){
                fetchMenu(0).then(result =>{
                    this.MenuList = result.data;
                    this.MenuList.unshift({id: 0, title: '无上级分类'});
                })
            },
            handleUpdate(index,row){
                getMenuById(row.id).then(result =>{
                    if (result.code === 200) {
                        this.menuForm = result.data;
                        this.editVisible = true;
                    }else {
                        this.$message.error("编辑异常")
                    }
                })
            },
            handleHiddenChange(index,row){
                updateHiddenById(row.id,row.hidden).then(result => {
                    if (result.code === 200) {
                        this.$message({
                            message: '修改成功',
                            type: 'success',
                            duration: 1000
                        });
                    }

                });
            },
            handleDelete(index,row){
                this.$confirm('是否要删除该菜单', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteMenuById(row.id).then(result => {
                        if(result.code === 200){
                            this.$message({
                                message: '删除成功',
                                type: 'success',
                                duration: 1000
                            });
                            this.getList();
                        }else {
                            this.$message.error("删除失败")
                        }

                    });
                });
            },
            onSubmit(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$confirm('是否提交数据', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            if (this.isEdit) {
                                saveOrUpdateMenu(this.menuForm).then(result => {
                                    this.editVisible = false;
                                    this.$message({
                                        message: '修改成功',
                                        type: 'success',
                                        duration: 1000
                                    });
                                    this.getList()
                                });
                            } else {
                                saveOrUpdateMenu(this.menuForm).then(result => {
                                    this.editVisible = false;
                                    this.$message({
                                        message: '提交成功',
                                        type: 'success',
                                        duration: 1000
                                    });
                                    this.getList()
                                });
                            }
                        });

                    } else {
                        this.$message({
                            message: '验证失败',
                            type: 'error',
                            duration: 1000
                        });
                        return false;
                    }
                });
            },
            resetForm() {
                this.menuForm = Object.assign({}, defaultMenu);
                this.selectMenu();
            },

        },
        mounted() {
            this.getList();
        },
        filters: {
            levelFilter(value) {
                if (value === 0) {
                    return '一级';
                } else if (value === 1) {
                    return '二级';
                }
            },
            disableNextLevel(value) {
                if (value === 0) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
</script>

<style scoped>

</style>