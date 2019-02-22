package com.java.hadoop;

/**
 * 一个byte有8位
 *
 * 能取值 -128 到127中间的数字
 */
public class Test {
    public static void main(String[] args) {
        byte by = -128;
        System.out.println(by);
        int i=1;
        System.out.println(i&0x00);
        System.out.println(i&0xff);
        int i1 = 1 << i;
        int i2 = i << 1;
        System.out.println(i>>1);
        System.out.println(i1);
        System.out.println(i2);
        System.out.println(i^0);
        byte[] tolh = tolh(10);
        for(byte b:tolh){
            System.out.print(b+"|");
        }
        System.out.println("============================================================");
        System.out.println(0x00);
        System.out.println(0xb);
        System.out.println(1|2);//0001  0010-> 0011
        System.out.println(1&2);//0001  0010->0000
        System.out.println(1^3);//0001 0011->0010
        System.out.println(1^2);//0001 0010->0011
        System.out.println(0x0001^0x0011);
        System.out.println(0x0010);
        System.out.println(0x1&0x1);
        System.out.println(0x1);
        System.out.println(0x11&0x11);
        System.out.println(0x10<<2);
        System.out.println(2<<0x10);//相当于2^16
        System.out.println(2<<16);
        System.out.println(2>>3);//超过边界用0 补齐
        System.out.println(2<<4);//0010  10000
        System.out.println(  "------------");
        System.out.println(1*0x10);
        System.out.println((2<<1)*0x10);
        System.out.println(2<<(1*0x10));//0011

        byte[] bytes = "1".getBytes();
        for(byte b:bytes){
            System.out.print(b+"|");
        }
        System.out.println("----------------");
        String a ="1";
        byte[] bytes2 = a.getBytes();
        for(byte b:bytes2){
            System.out.print(b+"|");
        }
        char cb =1;
        System.out.println("----------------");
        byte bytes3 = (byte)cb;
            System.out.println(bytes3+"|");

        System.out.println("1".getBytes().length);
        System.out.println("11".getBytes().length);
        System.out.println(a.getBytes().length);
        System.out.println("我m".getBytes().length);
        for(byte b:"我m".getBytes()){
            System.out.print(b+"|");
        }

    }

    /**
     * 将int转为低字节在前，高字节在后的byte数组
     */
    public static byte[] tolh(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }
    /**
     * 将int转为高字节在前，低字节在后的byte数组
     * @param n int
     * @return byte[]
     */
    public static byte[] toHH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }
    /**
     * 将byte数组转化成String
     */
    public static String toStr(byte[] valArr,int maxLen) {
        int index = 0;
        while(index < valArr.length && index < maxLen) {
            if(valArr[index] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, 0, temp, 0, index);
        return new String(temp);
    }

    /**
     * 将低字节在前转为int，高字节在后的byte数组
     */
    public static int vtolh(byte[] bArr) {
        int n = 0;
        for(int i=0;i<bArr.length&&i<4;i++){
            int left = i*8;
            n+= (bArr[i] << left);
        }
        return n;
    }


    //高前低后
    public static int bytesToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }
}
