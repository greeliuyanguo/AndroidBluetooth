package com.bigdata.gree.androidbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bigdata.gree.androidbluetooth.util.LygLog;

/**
 * Android Bluetooth蓝牙作为设备
 * 要与其他蓝牙设备互联,那么先决条件
 * 就是已经被发现
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_ENABLE_BT = 1;

    private BluetoothAdapter mBluetoothAdapter;

    //广播接收发现蓝牙设备
    private final BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //把接收到的设备一个一个打印出来
                LygLog.d("设备名字：" + device.getName() + "，设备地址：" + device.getAddress());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化蓝牙设备
        if (checkIfHasBTD()) {
            openBluetooth();
        }

        //注册广播
        registerBroadcast();

        //设置点击事件
        findViewById(R.id.init).setOnClickListener(this);
        findViewById(R.id.discovery).setOnClickListener(this);
        findViewById(R.id.discoverable).setOnClickListener(this);
    }

    /**
     * 检查是否含有蓝牙设备
     */
    private boolean checkIfHasBTD() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null == mBluetoothAdapter) {
            LygLog.d("Device does not support Bluetooth.");
            //不支持蓝牙设备，退出
            return false;
        }
        LygLog.d("Discovery Bluetooth device...");
        return true;
    }

    /**
     * 系统调用蓝牙，引导应用开启蓝牙设备\
     * 调用蓝牙设备要开启权限
     */
    private void openBluetooth() {
        if (mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            //在onActivityResult回调判断
        }
    }

    /**
     * 启动蓝牙发现
     */
    private void discoveringDevices() {
        if (null == mBluetoothAdapter) {
            if (checkIfHasBTD()) {
                openBluetooth();
            }
        }
        if (mBluetoothAdapter.startDiscovery()) {
            LygLog.d("启动蓝牙扫描设备");
        }
    }

    /**
     * 保持自身的蓝牙设备可以被其他蓝牙设备扫描到
     */
    private void enablingDiscoverability() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120);
        startActivity(discoverableIntent);
    }

    /**
     * 注册广播接收器，接收蓝牙发现讯息
     */
    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBluetoothReceiver, filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.init:
                if (checkIfHasBTD()) {
                    openBluetooth();
                }
                break;
            case R.id.discovery:
                discoveringDevices();
                break;
            case R.id.discoverable:
                enablingDiscoverability();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //处理REQUEST_ENABLE_BT请求码的结果
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                LygLog.d("打开蓝牙成功");
            }
            if (resultCode == RESULT_CANCELED) {
                LygLog.d("放弃打开蓝牙");
            }
        } else {
            LygLog.d("打开蓝牙异常");
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBluetoothReceiver);
    }
}
