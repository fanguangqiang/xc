package com.fan.common.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * SM3算法 进行数据转换
 *
 * @author yxj
 */
public class DataConvertUtil {

    /**
     * byte[] 循环左移
     */
    public static byte[] bitCycleLeft(byte[] tmp, int bitLen) {
        //要位移的位数对32取余
        bitLen %= 32;
        //要位移的位数对32取余后除以8
        int byteLen = bitLen / 8;
        //要位移的位数对32取余后除以8后对8取余
        int len = bitLen % 8;
        if (byteLen > 0) {
            //如果位移位数大于8
            tmp = byteCycleLeft(tmp, byteLen);
        }
        if (len > 0) {
            tmp = bitSmall8CycleLeft(tmp, len);
        }
        return tmp;
    }

    //int 循环左移
    private static int bitCycleLeft(int n, int bitLen) {
        bitLen %= 32;//要位移的位数对32取余
        byte[] tmp = intToBytes(n);//把int转成byte
        int byteLen = bitLen / 8;//要位移的位数对32取余后除以8
        int len = bitLen % 8;//要位移的位数对32取余后对8取余
        if (byteLen > 0) {
            //如果位移位数大于8
            tmp = byteCycleLeft(tmp, byteLen);
        }
        if (len > 0) {
            tmp = bitSmall8CycleLeft(tmp, len);
        }
        return bytesToInt(tmp, 0, false);
    }


    private static byte[] bitSmall8CycleLeft(byte[] in, int len) {
        byte[] tmp = new byte[in.length];
        int t1, t2, t3;
        for (int i = 0; i < tmp.length; i++) {
            t1 = (byte) ((in[i] & 0x000000ff) << len);
            t2 = (byte) ((in[(i + 1) % tmp.length] & 0x000000ff) >> (8 - len));
            t3 = (byte) (t1 | t2);
            tmp[i] = (byte) t3;
        }
        return tmp;
    }

    private static byte[] byteCycleLeft(byte[] in, int byteLen) {
        byte[] tmp = new byte[in.length];
        System.arraycopy(in, byteLen, tmp, 0, in.length - byteLen);
        System.arraycopy(in, 0, tmp, in.length - byteLen, byteLen);
        return tmp;
    }

    /**
     * 字节数组逆序
     *
     * @param in
     * @return
     */
    private static byte[] back(byte[] in) {
        byte[] out = new byte[in.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = in[out.length - i - 1];
        }

        return out;
    }

    public static byte[] rotateRight(byte[] sourceBytes, int n) {
        int i = bytesToInt(sourceBytes, 0, false);
        int i1 = i >> n;
        return intToBytes(i1);
    }

    //与
    public static byte[] byteArrayAND(byte[] bytes1, byte[] bytes2) {
        int length = bytes1.length;
        if (bytes1.length != bytes2.length) {
            throw new IllegalArgumentException("不同长度数据无法进行AND运算");
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (bytes1[i] & bytes2[i]);
        }
        return bytes;
    }

    //或
    public static byte[] byteArrayOR(byte[] bytes1, byte[] bytes2) {
        int length = bytes1.length;
        if (bytes1.length != bytes2.length) {
            throw new IllegalArgumentException("不同长度数据无法进行OR运算");
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (bytes1[i] | bytes2[i]);
        }
        return bytes;
    }

    //异或
    public static byte[] byteArrayXOR(byte[] bytes1, byte[] bytes2) {
        int length = bytes1.length;
        if (bytes1.length != bytes2.length) {
            throw new IllegalArgumentException("不同长度数据无法进行XOR运算");
        }
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) (bytes1[i] ^ bytes2[i]);
        }

        return bytes;
    }

    //非
    public static byte[] byteArrayNOT(byte[] bytes1) {
        int length = bytes1.length;
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = (byte) ~bytes1[i];
        }
        return bytes;
    }

    /**
     * 利用 {@link ByteBuffer}实现byte[]转long
     *
     * @param input
     * @param offset
     * @param littleEndian 输入数组是否小端模式
     * @return
     */
    public static long bytesToLong(byte[] input, int offset, boolean littleEndian) {
        if (offset < 0 || offset + 8 > input.length) {
            throw new IllegalArgumentException(String.format("less than 8 bytes from index %d  is insufficient for long", offset));
        }
        ByteBuffer buffer = ByteBuffer.wrap(input, offset, 8);
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        return buffer.getLong();
    }

    public static byte[] longToBytes(long value) {

        return ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
    }


    public static int bytesToInt(byte[] input, int offset, boolean littleEndian) {


        if (offset < 0 || offset + 4 > input.length) {
            throw new IllegalArgumentException(String.format("less than 4 bytes from index %d  is insufficient for long", offset));
        }
        ByteBuffer buffer = ByteBuffer.wrap(input, offset, 4);
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        return buffer.getInt();


    }

    public static byte[] intToBytes(int value) {

        return ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).putInt(value).array();
    }

/*
    public static void main(String[] args) {

//        long a=511;
//        long b=13;
//        byte[] bytes = byteArrayXOR(longToBytes(a), longToBytes(b));
//        System.out.println(bytesToLong(bytes, 0,false));
//
//
//        int ia=511;
//        int i = ia << 2;
//        System.out.println(i);

        String ss1 = "abcd";
        byte[] bytes = ss1.getBytes();

        byte[] bytes1 = bitCycleLeft(bytes, 2);
        System.out.println(Hex.toHexString(bytes1));

        System.out.println(15 / 8);

    }*/


}


