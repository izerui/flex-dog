<template>
    <v-container>
        <v-textarea
                :height="divHeight"
                box
                disabled
                label="访问日志:"
                class="body-2"
                :value="content"
        ></v-textarea>
    </v-container>
</template>

<script>
    export default {
        name: "ConsoleLogs",
        data() {
            return {
                content: '',
                timer: null,
                divHeight:600,
            }
        },
        beforeMount() {
            var h = document.documentElement.clientHeight || document.body.clientHeight;
            this.divHeight = h - 100;
        },
        created(){
          this.beginLoad();
        },
        destroyed(){
          this.stopLoad();
        },
        methods: {
            stopLoad(){
                clearInterval(this.timer);
            },
            beginLoad(){
                if(this.timer){
                    clearInterval(this.timer);
                }
                this.timer = setInterval(this.loadData,500)
            },
            async loadData(){
                const result = await this.$fly.get("/api/v1/records/top100");
                const dataArray = result.data;
                let _str = "";
                dataArray.forEach(item=>{
                    _str += item.entName + "    -   " + item.userName + "    " + this.timeFormat(item.begin) + ":    " + item.name + " / " + item.type + " / " + item.application + "    耗时: " + item.time + " " + (item.success == true ? "成功" : "失败") + "\n";
                })
                this.content = _str;
            },
            timeFormat(time){
                const newTime = new Date(time);
                return newTime.toISOString().
                replace(/T/, ' ').
                replace(/\..+/, '')
            },
        }
    }
</script>