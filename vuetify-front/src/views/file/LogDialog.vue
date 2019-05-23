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
        <v-card-text>
            <!--<div ref="containerDiv">-->
            <!--{{content}}-->
            <!--</div>-->
            <!-- Inline Code Block -->
            <!--<highlight-code ref="containerDiv" lang="Bash">-->
            <!--{{content}}-->
            <!--</highlight-code>-->
            <!--<highlight-code ref="containerDiv" lang="Bash">-->
            <pre v-for="(line) in contentArray">{{line}}</pre>
            <!--</highlight-code>-->
            <div ref="endDivider" v-if="contentArray.length > 0">----------------end</div>
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
                // content: '',
                contentArray: [],
                timer: null,
                waiting: false,
                begin: 0,
                scroll: true
            }
        },
        watch: {
            showLog: function (val) {
                if (val) {
                    if (this.timer !== null && this.timer !== undefined) {
                        clearInterval(this.timer)
                        this.content = ''
                        this.begin = 0
                    }
                    this.contentArray = []
                    this.scroll = true;
                    this.timer = setInterval(this.getLog, 1500)
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
            onScroll(e) {
                console.log(e.target.scrollTop, e.target.clientHeight, e.target.scrollHeight)
                if (e.target.scrollTop + e.target.clientHeight === e.target.scrollHeight) {
                    this.scroll = true;
                } else {
                    this.scroll = false;
                }
            },
            async getLog() {
                if (this.waiting) {
                    return;
                }
                if (this.logUrl) {
                    this.waiting = true;
                    const result = await this.$fly.get("/api/v1/log-length", {logUrl: encodeURI(this.logUrl)})
                    if (result.success) {
                        const length = result.data;
                        if (!this.begin) {
                            this.begin = length - 50000;
                            if (this.begin < 0) {
                                this.begin = 0;
                            }
                        }

                        const txt = await this.$fly.get("/api/v1/log-content", {
                            logUrl: encodeURI(this.logUrl),
                            begin: this.begin,
                        })
                        this.begin = length
                        // this.content += txt;
                        this.contentArray = this.contentArray.concat(txt.split(/\r\n|[\r\n]/))
                        this.waiting = false

                        if(txt){
                            setTimeout(()=>{
                                if (this.scroll) {
                                    this.$refs.endDivider.scrollIntoView(
                                        {
                                            behavior: "smooth", //"auto" | "instant" | "smooth", // 默认 auto
                                            block: "end", //"start" | "center" | "end" | "nearest", // 默认 center
                                            inline: "nearest", //"start" | "center" | "end" | "nearest", // 默认 nearest
                                        }
                                    )
                                }
                            },300)
                        }
                    }
                }
            }
        }
    }
</script>

<style scoped>
    pre:hover{
        background-color: lightgrey;
    }
    pre {
        white-space: pre-wrap;
        word-wrap: break-word;
    }
</style>