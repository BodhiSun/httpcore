package com.bodhi.example;

import android.util.Log;

import com.bodhi.http.HttpCore;
import com.bodhi.http.HttpRequestListener;
import com.bodhi.http.component.BaseResp;
import com.bodhi.http.exception.URLNullException;

/**
 * Created by quinn on 17-6-2.
 */

public class AccountManager {

    private Account mData;

    private static AccountManager mInstance;

    public static AccountManager getInstance() {
        if (mInstance == null) {
            mInstance = new AccountManager();
        }

        return mInstance;
    }

    protected void requestMemberInfo() {
        ThreadManager.getInstance().run(new Runnable() {
            @Override
            public void run() {
                try {
                    final String url = "http://localhost:8080/HelloWorld/userinfoApi.jsp";
                    HttpCore.getInstance().get(url, AccountResp.class, new HttpRequestListener<AccountResp>() {
                        @Override
                        public void onResult(AccountResp resp) {
                            int code = resp.getCode();
                            String message = resp.getMessage();
                            int status = resp.getStatus();
                            String result = resp.getResult();
                            mData = resp.data;
                            Log.e("aaa","requestMember  InfoonResult code:"+code+" message:"+message+" status:"+status+" result:"+result+" data:"+mData);
                        }

                        @Override
                        public void onError(int errorCode, String errorMsg) {
                            Log.e("aaa", "onError   errorCode:" + errorCode + "   errorMsg:" + errorMsg);
                        }

                    });

//                    HttpCore.getInstance().anyGet(url, null, AccountResp.class, new HttpRequestListener<AccountResp>() {
//                        @Override
//                        public void onResult(AccountResp resp) {
//                            int code = resp.getCode();
//                            String message = resp.getMessage();
//                            int status = resp.getStatus();
//                            String result = resp.getResult();
//                            Account data = resp.data;
//                            Log.e("aaa","onResult code:"+code+" message:"+message+" status:"+status+" result:"+result+" data:"+data);
//                        }
//
//                        @Override
//                        public void onError(int errorCode, String errorMsg) {
//                            Log.e("aaa", "onError   errorCode:" + errorCode+"   errorMsg:"+errorMsg);
//                        }
//                    });

                } catch (URLNullException e) {
                    e.printStackTrace();
                    Log.e("aaa", "onError catch:" + e.getMessage());
                }
            }
        });
    }

    public void addDiamond(int amount) {
        if (mData != null)
            mData.coin += amount;
    }


    public void addCoin(int amount) {
        if (mData != null)
            mData.balance += amount;
    }

    public boolean isWXBinded() {
        if (mData == null)
            return false;

        return mData.isWXBinded();
    }

    public boolean isPhoneBinded() {
        if (mData == null)
            return false;
        return mData.isPhoneBinded();
    }

    public String getHeadUrl() {
        if (mData == null)
            return "";
        return mData.headimg;
    }

    public int getDiamond() {
        if (mData == null)
            return 0;
        return mData.coin;
    }

    public int getCoin() {
        if (mData == null)
            return 0;

        return mData.balance;
    }

    public String getNick() {
        if (mData == null)
            return "";
        return mData.nick_name;
    }

    public boolean hasMaster() {
        if (mData == null)
            return false;
        return mData.hasMaster();
    }

    public String getInviteCode() {
        if (mData == null)
            return "";
        return mData.invite_code;
    }

    public int uid() {
        if (mData == null) {
            return 0;
        }

        return mData.uid;
    }


    public class Account {

        public int balance;  // --- 零钱
        public int coin;     // --- 钻石
        public int child_num; //徒弟数
        public int uid; // uid
        public String nick_name; //昵称
        public int integral;  //积分

        public String invite_code;
        public int is_bind_parent;
        public int is_bind_wechat;
        public int is_bind_phone;
        public String headimg;
        private int charge;

        public boolean hasMaster() {
            return is_bind_parent != 0;
        }

        public boolean isWXBinded() {
            return is_bind_wechat == 1;
        }

        public boolean isPhoneBinded() {
            return is_bind_phone == 1;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "balance=" + balance +
                    ", coin=" + coin +
                    ", child_num=" + child_num +
                    ", uid=" + uid +
                    ", nick_name='" + nick_name + '\'' +
                    ", integral=" + integral +
                    ", invite_code='" + invite_code + '\'' +
                    ", is_bind_parent=" + is_bind_parent +
                    ", is_bind_wechat=" + is_bind_wechat +
                    ", is_bind_phone=" + is_bind_phone +
                    ", headimg='" + headimg + '\'' +
                    ", charge='" + charge + '\'' +
                    '}';
        }
    }

    class AccountResp extends BaseResp {
        private Account data;
    }


}
