import 'package:flutter/services.dart';

typedef void s(Object o);

class Flutterpda {
  static const EventChannel eventChannel = EventChannel('com.qs.wiget/plugin');
  static const MethodChannel methodChannel =
  MethodChannel('com.qs.wiget/method');

  static void onrecv(s success, s error) {
    eventChannel.receiveBroadcastStream().listen(success, onError: error);
  }

  static void scan() {
    methodChannel.invokeMethod("scan");
  }
}
