package com.android.hellosumaidl;

// Interface declaration （接口声明）
interface IAdditionService {
    // You can pass the value of in, out or inout
    // The primitive types (int, boolean, etc) are only passed by in
    int add(in int value1, in int value2);
}