<template>
    <v-card tile>
        <v-toolbar dense card dark color="primary">
            <v-btn icon dark @click="cancel">
                <v-icon>close</v-icon>
            </v-btn>
            <v-toolbar-title>安全校验</v-toolbar-title>
            <v-spacer></v-spacer>
            <!--            <v-toolbar-items>-->
            <!--                <v-tooltip bottom>-->
            <!--                    <template v-slot:activator="{ on }">-->
            <!--                        <v-btn @click="sendSms" icon v-on="on">-->
            <!--                            <v-icon>sms</v-icon>-->
            <!--                        </v-btn>-->
            <!--                    </template>-->
            <!--                    <span>发送验证码</span>-->
            <!--                </v-tooltip>-->
            <!--                <v-tooltip bottom>-->
            <!--                    <template v-slot:activator="{ on }">-->
            <!--                        <v-btn @click="confirm" icon v-on="on">-->
            <!--                            <v-icon>check_circle_outline</v-icon>-->
            <!--                        </v-btn>-->
            <!--                    </template>-->
            <!--                    <span>确认</span>-->
            <!--                </v-tooltip>-->
            <!--            </v-toolbar-items>-->
        </v-toolbar>
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
                        ></v-select>
                    </v-flex>
                    <v-flex xs12 sm6 md4>
                        <v-text-field label="验证码*" required v-model="code"></v-text-field>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="primary" @click="sendSms" flat>
                <v-icon dark>sms</v-icon>
                发送验证码
            </v-btn>

            <v-btn color="primary" @click="confirm" flat :loading="loading">
                <v-icon>check_circle_outline</v-icon>
                确定
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
  export default {
    name: "VerificationDialog",
    props: {},
    data() {
      return {
        phone: null,
        code: null,
        publishers: [],
        loading: false
      }
    },
    created() {
      this.initData();
    },
    methods: {
      confirmLoading(value) {
        this.loading = value;
      },
      sendSms() {
        this.$fly.get("/api/v1/send-captcha", {phone: this.phone}).then(s => {
          if (s.success) {
            this.$message.success("发送验证码成功");
          }
        })
      },
      confirm() {
        this.$emit("confirm", this.phone, this.code);
      },
      cancel() {
        this.loading = false
        this.$emit("close");
      },
      initData() {
        this.loading = false
        this.$fly.get("/api/v1/publishers").then(result => {
          this.publishers = result.data;
        });
      },
    }
  }
</script>

<style scoped>

</style>
