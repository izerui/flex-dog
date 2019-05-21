<template>
    <v-card tile>
        <v-toolbar card dark color="primary">
            <v-btn icon dark @click="$emit('close')">
                <v-icon>close</v-icon>
            </v-btn>
            <v-toolbar-title>服务日志</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-side-icon>
                <v-tooltip bottom>
                    <template v-slot:activator="{ on }">
                        <v-switch
                                light
                                v-model="scroll" icon v-on="on">
                        </v-switch>
                    </template>
                    <span>自动滚动</span>
                </v-tooltip>
            </v-toolbar-side-icon>
        </v-toolbar>
        <v-card-text ref="container">
            <!--<div ref="containerDiv">-->
                <!--{{content}}-->
            <!--</div>-->
            <!-- Inline Code Block -->
            <highlight-code ref="containerDiv">
                {{content}}
            </highlight-code>
            <div ref="end"></div>
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        name: "LogDialog",
        props: {
            showLog: {
                type: Boolean,
                default: false
            },
            logUrl: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                content: '',
                timer: null,
                loading: false,
                begin: 0,
                scroll: true
            }
        },
        watch: {
            showLog: function(val) {
                if (val) {
                    if (this.timer !== null && this.timer !== undefined) {
                        clearInterval(this.timer)
                        this.content = ''
                        this.begin = 0
                    }
                    this.timer = setInterval(this.getLog, 1000)
                } else {
                    if (this.timer !== null && this.timer !== undefined) {
                        clearInterval(this.timer)
                        this.content = ''
                        this.begin = 0
                    }
                }
            }
        },
        methods: {
            async getLog() {
                if(this.loading || !this.scroll){
                    return;
                }
                if(this.logUrl){
                    this.loading = true;
                    const result = await this.$fly.get("/api/v1/log-length", {logUrl: encodeURI(this.logUrl)})
                    if (result.success) {
                        const length = result.data;
                        if (!this.begin) {
                            this.begin = length - 30000;
                            if (this.begin < 0) {
                                this.begin = 0;
                            }
                        }

                        const txt = await this.$fly.get("/api/v1/log-content",{
                            logUrl: encodeURI(this.logUrl),
                            begin: this.begin,
                        })
                        this.content += '<br>' + txt;
                        this.loading = false
                        this.begin = length
                        // this.$refs.container.scrollTop = Math.floor(this.$refs.containerDiv.scrollHeight)
                        if(this.scroll){
                            this.$refs.end.scrollIntoView()
                        }
                    }
                }
            }
        }
    }
</script>

<style scoped>

</style>