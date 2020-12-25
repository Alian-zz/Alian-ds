<template>
     <product-detail :is-edit="true"></product-detail>
</template>

<script>
    import ProductDetail from "@/views/pms/product/productInfo/ProductDetail";

    export default {
        name: "updateProduct",
        components: {ProductDetail},
        data() {
            return {}
        },
        methods: {},
        mounted() {

        }
    }
</script>

<style scoped>

</style>