<template>
    <h1>
        文件管理
    </h1>


</template>

<script>

export default {
    data() {
        return {
            fileList: []
        };
    },
    methods: {
        submitUpload() {
            this.$refs.upload.submit();
        },
        handleRemove(file, fileList) {
            console.log(file, fileList);
        },
        handlePreview(file) {
            console.log(file);
        }
    }
}
</script>