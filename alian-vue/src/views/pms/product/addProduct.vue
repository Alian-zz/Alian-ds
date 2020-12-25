<template>
     <product-detail :is-edit="false"></product-detail>
</template>

<script>
    import ProductDetail from "@/views/pms/product/productInfo/ProductDetail";

    export default {
        name: "addProduct",
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