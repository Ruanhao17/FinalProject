package com.swufe.finalproject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser{
        private BmobFile IDCard_img;
        public BmobFile getIDCard_img() {
            return IDCard_img;
        }
        public void setIDCard_img(BmobFile IDCard_img) {
            this.IDCard_img = IDCard_img;
        }
}

