<template>
    <el-card class="form-container" shadow="never">
        <el-tree
                :data="menuTreeList"
                show-checkbox
                default-expand-all
                node-key="id"
                ref="tree"
                highlight-current
                :props="defaultProps">
        </el-tree>
        <div style="margin-top: 20px" align="center">
            <el-button type="primary" @click="handleSave()">保存</el-button>
            <el-button @click="handleClear()">清空</el-button>
        </div>

    </el-card>
</template>

<script>

    import {fetchTreeList, fetchMenuByRole} from "@/api/ums/role";
    import {saveMenuByRole} from "@/api/ums/menu";

    export default {
        name: "allocMenu",
        data() {
            return {
                menuTreeList: [],
                defaultProps: {
                    children: 'children',
                    label: 'title'
                },
                roleId: null
            };
        },
        created() {
            this.roleId = this.$route.query.roleId;
            this.treeList();
            this.getRoleMenu(this.roleId);
        },
        methods: {
            treeList() {
                fetchTreeList().then(response => {
                    this.menuTreeList = response.data;
                });
            },
            getRoleMenu(roleId) {
                fetchMenuByRole(roleId).then(response => {
                    debugger;
                    let menuList = response.data;
                    let checkedMenuIds = [];
                    if (menuList != null && menuList.length > 0) {
                        for (let i = 0; i < menuList.length; i++) {
                            checkedMenuIds.push(menuList[i].menuId);
                        }
                    }
                    this.$refs.tree.setCheckedKeys(checkedMenuIds);
                });
            },
            handleSave() {
                let checkedNodes = this.$refs.tree.getCheckedNodes();
                let menuByRoleList = [];
                if (checkedNodes != null && checkedNodes.length > 0) {
                    for (let i = 0; i < checkedNodes.length; i++) {
                        menuByRoleList.push({
                            "menuId": checkedNodes[i].id,
                            "roleId": this.roleId
                        })
                    }
                }
                this.$confirm('是否分配菜单？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    debugger
                    saveMenuByRole(this.roleId, menuByRoleList).then(response => {
                        this.$message({
                            message: '分配成功',
                            type: 'success',
                            duration: 2000
                        });
                        this.$router.back();
                    })
                })
            },
            handleClear() {
                this.$refs.tree.setCheckedKeys([]);
            }
        }
    }
</script>

<style scoped>

</style>
