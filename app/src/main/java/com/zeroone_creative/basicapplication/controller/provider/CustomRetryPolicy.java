package com.zeroone_creative.basicapplication.controller.provider;import com.android.volley.DefaultRetryPolicy;import com.android.volley.NetworkResponse;import com.android.volley.Request;import com.android.volley.VolleyError;import com.android.volley.VolleyLog;public class CustomRetryPolicy extends DefaultRetryPolicy {    protected long mInterval = 2000;    // デフォルトタイムアウト時間    private static final int CONNECTION_TIMEOUT = 10000;    private static final int CONNECTION_RETRY_COUNT = 3;    private Request<?> mRequest;    public CustomRetryPolicy(Request<?> request) {        super(CONNECTION_TIMEOUT, CONNECTION_RETRY_COUNT, 1f);        mRequest = request;    }    public CustomRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier, Request<?> request) {        super(initialTimeoutMs, maxNumRetries, backoffMultiplier);        mRequest = request;    }    @Override    public void retry(VolleyError error) throws VolleyError {        NetworkResponse response = error.networkResponse;        if (response != null) {            // サーバーエラー時はリトライしない            throw error;        }        if (mRequest != null && mRequest.isCanceled()) {            // キャンセル済みならリトライしない（エラー処理されない）            throw error;        }        if (mInterval > 0) {            try {                Thread.sleep(mInterval);            } catch (InterruptedException e) {            }        }        VolleyLog.d("Network Retry count : %d", getCurrentRetryCount());        super.retry(error);    }}