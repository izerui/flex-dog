<template>
    <v-card tile>
        <v-toolbar card dark color="primary">
            <v-btn icon dark @click="close">
                <v-icon>close</v-icon>
            </v-btn>
            <v-toolbar-title>新建服务</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-toolbar-items>
                <v-btn dark flat @click="save">
                    <v-icon>save</v-icon>
                    保存
                </v-btn>
            </v-toolbar-items>
        </v-toolbar>
        <v-card-text>
            <v-container grid-list-md>
                <v-layout wrap>
                    <v-flex xs12>
                        <v-autocomplete
                                item-text="Name"
                                item-value="server"
                                :items="servers"
                                label="服务器*"
                                multiple
                                required
                                v-model="formData.servers"
                        ></v-autocomplete>
                    </v-flex>
                    <v-flex xs12>
                        <v-text-field label="文件名*" required v-model="formData.fileName"></v-text-field>
                    </v-flex>
                    <v-flex xs12 sm6>
                        <v-select
                                item-text="name"
                                item-value="name"
                                v-model="formData.owner"
                                :items="publishers"
                                label="拥有者*"
                                required
                        ></v-select>
                    </v-flex>
                    <v-flex xs12 sm6>
                        <v-select
                                v-model="formData.type"
                                :items="['jar', 'file', 'www']"
                                label="发布方式*"
                                required
                        ></v-select>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-select
                                item-text="name"
                                item-value="phone"
                                v-model="formData.sender"
                                :items="publishers"
                                label="操作人*"
                                required
                                @change="disableMail = false"
                        >
                        </v-select>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-btn color="info" @click="sendCaptcha" v-bind:disabled="disableMail">
                            <v-icon>mail_outline</v-icon>
                            发送
                        </v-btn>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-text-field label="验证码*" required v-model="formData.code"></v-text-field>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        name: "NewFileDialog",
        props: {},
        data() {
            return {
                servers: [],
                formData: {
                    servers: null,
                    fileName: null,
                    owner: null,
                    type: null,
                    sender: null,
                    code: null,
                },
                publishers: [],
                disableMail: false,
            }
        },
        created() {
            this.initData();
        },
        methods: {
            initData() {
                this.resetForm()
                this.$fly.get("/api/v1/publishers").then(result => {
                    this.publishers = result.data;
                });

                this.$fly.get("/api/v1/servers").then(result => {
                    const serverList = result.UHostSet;

                    serverList.forEach(s => {
                        let ips = "";
                        s.IPSet.forEach(d => {
                            ips += d.IP + ",";
                        })
                        s.server = s.Name + "," + ips;
                    })
                    this.servers = serverList;
                })
            },
            async sendCaptcha() {
                const {success} = await this.$fly.get("/api/v1/file/send-captcha", {"phone": this.formData.sender})
                if(success){
                    this.disableMail = true
                    this.$message.success('发送成功');
                }
            },
            save() {
                this.$emit('save', this.formData)
            },
            close() {
                this.formData = {}
                this.$emit('close')
            },
            resetForm() {
                this.disableMail = false
                this.formData = {}
            }
        }
    }
</script>

<style scoped>

</style>