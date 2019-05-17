<template>
    <v-card tile>
        <v-card-title>安全验证</v-card-title>
        <v-divider></v-divider>
        <v-card-text>
            <v-container grid-list-md>
                <v-layout wrap>
                    <v-flex xs12 sm6>
                        <v-select
                                item-text="name"
                                item-value="phone"
                                v-model="phone"
                                :items="publishers"
                                label="拥有者*"
                                required
                                @change="disableSms = false"
                        ></v-select>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-btn @click="sendSms" v-bind:disabled="disableSms">发送</v-btn>
                    </v-flex>
                    <v-flex xs12>
                        <v-text-field label="验证码*" required v-model="code"></v-text-field>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="confirm" color="primary">
                <v-icon>done</v-icon>
                确定
            </v-btn>
            <v-btn @click="cancel">
                <v-icon>cancel</v-icon>
                取消
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "VerificationDialog",
        props: {
            callBack: {
                type: Function,
                required: true
            },
        },
        data() {
            return {
                phone: null,
                code: null,
                publishers: [],
                disableSms: false
            }
        },
        created() {
            this.initData();
        },
        methods: {
            sendSms() {
                this.$fly.get("/api/v1/send-captcha", {phone: this.phone}).then(s => {
                    if (s.success) {
                        this.$message.success("发送验证码成功");
                        this.disableSms = true;
                    }
                })
            },
            confirm() {
                this.callBack(this.phone, this.code);
            },
            cancel() {
                this.$emit("close");
            },
            initData() {
                this.disableSms = false;
                this.$fly.get("/api/v1/publishers").then(result => {
                    this.publishers = result.data;
                });
            }
        }
    }
</script>

<style scoped>

</style>