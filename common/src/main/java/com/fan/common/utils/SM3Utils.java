package com.fan.common.utils;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * @author yxj
 */
public class SM3Utils {

    /**
     * 参照国密SM3规范
     */
    static byte[] iVbyte =new byte[]{(byte) 0x73,(byte) 0x80,(byte) 0x16,(byte) 0x6f,(byte) 0x49,(byte) 0x14,(byte) 0xb2,(byte) 0xb9,(byte) 0x17
            ,(byte) 0x24,(byte) 0x42,(byte) 0xd7,(byte) 0xda,(byte) 0x8a,(byte) 0x06,(byte) 0x00,(byte) 0xa9,(byte) 0x6f,(byte) 0x30,(byte) 0xbc
            ,(byte) 0x16,(byte) 0x31,(byte) 0x38,(byte) 0xaa,(byte) 0xe3,(byte) 0x8d,(byte) 0xee,(byte) 0x4d,(byte) 0xb0,(byte) 0xfb,(byte) 0x0e,(byte) 0x4e};

    static byte[] T1byte =new byte[]{(byte) 0x79,(byte) 0xcc,(byte) 0x45,(byte) 0x19};
    static byte[] T2byte = new byte[]{(byte) 0x7a,(byte) 0x87,(byte) 0x9d,(byte) 0x8a};

    static byte[][] BArray  = null;
    static byte[][] WAArray= new byte[68][4];
    static byte[][] WBArray= new byte[64][4];

    static long n = 0L;

    /**
     * 生成SM3哈希值
     * @param plaintext 明文
     * @return 哈希值
     */
    public static String sm3Hash(String plaintext){
        if(StringUtils.isEmpty(plaintext)){
            return null;
        }
        byte[] append = append(plaintext.getBytes());
        byte[] sm3Hash=iteration(append);
        String hashValue = Hex.toHexString(sm3Hash);
        System.out.println(hashValue);
        return hashValue;
    }

    /**
     * 进行hash值对比
     * @param plaintext 明文
     * @param hashValue 哈希值
     * @return Boolean
     */
    public static boolean compareHashValue(String plaintext,String hashValue){
        if (hashValue != null && hashValue.length() != 0) {
            return Objects.equals(hashValue,sm3Hash(plaintext));
        } else {
            return false;
        }
    }

    /**
     *  当 n = 0-15
     */
    public static byte[] FF1(byte[] X,byte[] Y,byte[] Z){
        return DataConvertUtil.byteArrayXOR(DataConvertUtil.byteArrayXOR(X, Y), Z);
    }
    /**
     * 当 n = 16-63
     */
    public static byte[] FF2(byte[] X,byte[] Y,byte[] Z){
        return DataConvertUtil.byteArrayOR(DataConvertUtil.byteArrayOR(DataConvertUtil.byteArrayAND(X,Y),DataConvertUtil.byteArrayAND(X,Z)),DataConvertUtil.byteArrayAND(Y,Z));
    }
    //0-15
    public static byte[] GG1(byte[] X,byte[] Y,byte[] Z){
        return DataConvertUtil.byteArrayXOR(DataConvertUtil.byteArrayXOR(X, Y), Z);
    }
    //16-63
    public static byte[] GG2(byte[] X,byte[] Y,byte[] Z){
        return DataConvertUtil.byteArrayOR(DataConvertUtil.byteArrayAND(X,Y),DataConvertUtil.byteArrayAND(DataConvertUtil.byteArrayNOT(X), Z));
    }
    public static byte[] P0(byte[] X){
        return DataConvertUtil.byteArrayXOR(DataConvertUtil.byteArrayXOR(X,DataConvertUtil.bitCycleLeft(X,9)),DataConvertUtil.bitCycleLeft(X,17));
    }

    public static byte[] P1(byte[] X){
        return DataConvertUtil.byteArrayXOR(DataConvertUtil.byteArrayXOR(X,DataConvertUtil.bitCycleLeft(X,15)),DataConvertUtil.bitCycleLeft(X,23));
    }

    /**
     * 1.填充
     */
    public static byte[] append(byte[] m){
        long l = m.length*8;
        //计算k
        long k = 448-((l+1)%512);
        long length = (l+1+k+64)/8;
        byte[] append = new byte[(int)length];
        int mLen = m.length;
        //先把 m system.copy到首部
        System.arraycopy(m,0,append,0,mLen);
        //填充1
        System.arraycopy(new byte[]{-128},0,append,mLen,1);
        //填充64位bit 长度是l的二进制表达式
        byte[] array = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(l).array();
        System.arraycopy(array,0,append,(int)length-array.length,array.length);
        return append;
    }

    /**
     * 压缩函数
     */
    public static byte[] CF(byte[] V,byte[] BI){
        if(V==null){
            V=iVbyte;
        }
        expand(BI);
        byte[] SS1,SS2,TT1,TT2,T;
        byte[] VIABC=new byte[V.length];
        byte[] A=new byte[4];
        System.arraycopy(V, 4*0, A, 0, 4);
        byte[] B=new byte[4];
        System.arraycopy(V, 4*1, B, 0, 4);
        byte[] C=new byte[4];
        System.arraycopy(V, 4*2, C, 0, 4);
        byte[] D=new byte[4];
        System.arraycopy(V, 4*3, D, 0, 4);
        byte[] E=new byte[4];
        System.arraycopy(V, 4*4, E, 0, 4);
        byte[] F=new byte[4];
        System.arraycopy(V, 4*5, F, 0, 4);
        byte[] G=new byte[4];
        System.arraycopy(V, 4*6, G, 0, 4);
        byte[] H=new byte[4];
        System.arraycopy(V, 4*7, H, 0, 4);

        for (int j = 0; j < 64; j++) {

                int l1,l2,l3;
                l1=DataConvertUtil.bytesToInt(DataConvertUtil.bitCycleLeft(A,12),0,false);
                l2=DataConvertUtil.bytesToInt(E,0,false);
                if(j<16){
                    T=T1byte;
                }else{
                    T=T2byte;
                }
                l3=DataConvertUtil.bytesToInt(DataConvertUtil.bitCycleLeft(T,(j%32)),0,false);
                SS1 = DataConvertUtil.bitCycleLeft(DataConvertUtil.intToBytes(l1+l2+l3), 7);
                //System.out.println("SS1 len:"+SS1.length);
                //System.out.println("A len:"+A.length);
                //System.out.println("要异或的长度："+DataConvertUtil.bitCycleLeft(A,12).length);
                SS2=DataConvertUtil.byteArrayXOR(SS1, DataConvertUtil.bitCycleLeft(A,12));

                int l4,l5,l6,l7;
                if(j<16){
                    l4=DataConvertUtil.bytesToInt(FF1(A,B,C),0,false);
                }else{
                    l4=DataConvertUtil.bytesToInt(FF2(A,B,C),0,false);
                }
                l5=DataConvertUtil.bytesToInt(D,0,false);
                l6=DataConvertUtil.bytesToInt(SS2,0,false);
                l7=DataConvertUtil.bytesToInt(WBArray[j],0,false);
                TT1=DataConvertUtil.intToBytes(l4+l5+l6+l7);

                int l8,l9,l10,l11;
                if(j<16){
                    l8=DataConvertUtil.bytesToInt(GG1(E,F,G),0,false);
                }else {
                    l8=DataConvertUtil.bytesToInt(GG2(E,F,G),0,false);
                }
                l9 = DataConvertUtil.bytesToInt(H,0,false);
                l10 = DataConvertUtil.bytesToInt(SS1,0,false);
                l11 = DataConvertUtil.bytesToInt(WAArray[j],0,false);
                TT2 = DataConvertUtil.intToBytes(l8+l9+l10+l11);
                D=C;
                C=DataConvertUtil.bitCycleLeft(B,9);
                B=A;
                A=TT1;
                H=G;
                G=DataConvertUtil.bitCycleLeft(F,19);
                F=E;
                E=P0(TT2);
            //System.out.println(j+"A:"+Hex.toHexString(A));
            //System.out.println(j+"B:"+Hex.toHexString(B));
            //System.out.println(j+"C:"+Hex.toHexString(C));
            //System.out.println(j+"D:"+Hex.toHexString(D));
            //System.out.println(j+"E:"+Hex.toHexString(E));
            //System.out.println(j+"F:"+Hex.toHexString(F));
            //System.out.println(j+"G:"+Hex.toHexString(G));
            //System.out.println(j+"H:"+Hex.toHexString(H));
        }
        System.arraycopy(A, 0, VIABC, 0*4, 4);
        System.arraycopy(B, 0, VIABC, 1*4, 4);
        System.arraycopy(C, 0, VIABC, 2*4, 4);
        System.arraycopy(D, 0, VIABC, 3*4, 4);
        System.arraycopy(E, 0, VIABC, 4*4, 4);
        System.arraycopy(F, 0, VIABC, 5*4, 4);
        System.arraycopy(G, 0, VIABC, 6*4, 4);
        System.arraycopy(H, 0, VIABC, 7*4, 4);

        return DataConvertUtil.byteArrayXOR(VIABC, V);
    }
    /**
     * 扩展
     */
    public static void expand(byte[] BI){
        //第一步将消息分组B划分为16个字
        for (int i = 0; i <16 ; i++) {
            byte[] temByte = new byte[4];
            System.arraycopy(BI, i*4, temByte,0,4);
            WAArray[i]=temByte;
        }
        // 第二步
        for (int j = 16; j < 68; j++) {
            WAArray[j]= DataConvertUtil.byteArrayXOR(DataConvertUtil.byteArrayXOR(P1(DataConvertUtil.byteArrayXOR(DataConvertUtil.byteArrayXOR(WAArray[j-16],WAArray[j-9]),DataConvertUtil.bitCycleLeft(WAArray[j-3],15))),DataConvertUtil.bitCycleLeft(WAArray[j-13], 7)),WAArray[j-6]);
        }
        for (int j = 0; j <64 ; j++) {
            WBArray[j]=DataConvertUtil.byteArrayXOR(WAArray[j], WAArray[j+4]);
        }
    }

    /**
     *  2.迭代
     */
    public static byte[] iteration(byte[] append){
        byte[] sm3Hash=null;
        n = append.length/64;
        BArray = new byte [(int)n][64];
        for (int i = 0; i <n ; i++) {
            System.arraycopy(append, i*64, BArray[i],0,64);
            //压缩函数
            sm3Hash=CF(sm3Hash,BArray[i]);
        }
        return sm3Hash;
    }

 /*   public static void main(String[] args) {
        String str = "123456";
        byte[] append = append(str.getBytes());
        byte[] sm3Hash=iteration(append);
        System.out.println(Hex.toHexString(sm3Hash));
    }*/

    /**
     * Byte转Bit
     */
    public static String byteToBit(byte b) {
        return "" +(byte)((b >> 7) & 0x1) +
                (byte)((b >> 6) & 0x1) +
                (byte)((b >> 5) & 0x1) +
                (byte)((b >> 4) & 0x1) +
                (byte)((b >> 3) & 0x1) +
                (byte)((b >> 2) & 0x1) +
                (byte)((b >> 1) & 0x1) +
                (byte)((b >> 0) & 0x1);
    }
    /**
     * Bit转Byte
     */
    public static byte BitToByte(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {//4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }
}
