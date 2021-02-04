package com.ahies.flutterpda;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;


import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class PdaScannerPlugin implements EventChannel.StreamHandler, FlutterPlugin, MethodChannel.MethodCallHandler {
    private static final String CHANNEL = "com.qs.wiget/plugin";
    private static final String METHOD = "com.qs.wiget/method";

    private static final String SCAN_ACTION = "com.qs.scancode";

    private static EventChannel.EventSink eventSink;

    private  Context applicationContext;
    private static final BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String actionName = intent.getAction();
            if (SCAN_ACTION.equals(actionName)) {
                eventSink.success(intent.getStringExtra("code"));
            } else {
                Log.i("PdaScannerPlugin", "NoSuchAction");
            }
        }
    };
    public PdaScannerPlugin( ) {
    }
/*    public PdaScannerPlugin(Activity activity) {
        IntentFilter xmIntentFilter = new IntentFilter();
        xmIntentFilter.addAction(SCAN_ACTION);
        xmIntentFilter.setPriority(Integer.MAX_VALUE);
        applicationContext.registerReceiver(scanReceiver, xmIntentFilter);
    }*/

    public static void registerWith(PluginRegistry.Registrar registrar) {
        PdaScannerPlugin plugin = new PdaScannerPlugin();
        MethodChannel methodChannel = new MethodChannel(registrar.messenger(), METHOD);
        methodChannel.setMethodCallHandler(plugin);
        EventChannel channel = new EventChannel(registrar.messenger(), CHANNEL);
        channel.setStreamHandler(plugin);
    }

    @Override
    public void onListen(Object o, final EventChannel.EventSink eventSink) {
        PdaScannerPlugin.eventSink = eventSink;
    }

    @Override
    public void onCancel(Object o) {
        Log.i("PdaScannerPlugin", "PdaScannerPlugin:onCancel");
    }

    @Override
    public void onAttachedToEngine( FlutterPluginBinding binding) {
        applicationContext = binding.getApplicationContext();
        IntentFilter xmIntentFilter = new IntentFilter();
        xmIntentFilter.addAction(SCAN_ACTION);
        xmIntentFilter.setPriority(Integer.MAX_VALUE);
        applicationContext.registerReceiver(scanReceiver, xmIntentFilter);
    }

    @Override
    public void onDetachedFromEngine( FlutterPluginBinding binding) {

    }

    @Override
    public void onMethodCall( MethodCall call,  MethodChannel.Result result) {
        System.out.println("1111111111");
        if(call.method.equals("scan")){
            result.success("1111");
        }
    }
}
