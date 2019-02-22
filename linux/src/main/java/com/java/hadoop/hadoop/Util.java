//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.java.hadoop.hadoop;

import com.jcraft.jsch.HASH;
import com.jcraft.jsch.JSchException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

class Util {
    private static final byte[] b64 = str2byte("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=");
    private static String[] chars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    static final byte[] empty = str2byte("");

    Util() {
    }

    private static byte val(byte foo) {
        if (foo == 61) {
            return 0;
        } else {
            for(int j = 0; j < b64.length; ++j) {
                if (foo == b64[j]) {
                    return (byte)j;
                }
            }

            return 0;
        }
    }

    static byte[] fromBase64(byte[] buf, int start, int length) throws JSchException {
        try {
            byte[] foo = new byte[length];
            int j = 0;

            for(int i = start; i < start + length; i += 4) {
                foo[j] = (byte)(val(buf[i]) << 2 | (val(buf[i + 1]) & 48) >>> 4);
                if (buf[i + 2] == 61) {
                    ++j;
                    break;
                }

                foo[j + 1] = (byte)((val(buf[i + 1]) & 15) << 4 | (val(buf[i + 2]) & 60) >>> 2);
                if (buf[i + 3] == 61) {
                    j += 2;
                    break;
                }

                foo[j + 2] = (byte)((val(buf[i + 2]) & 3) << 6 | val(buf[i + 3]) & 63);
                j += 3;
            }

            byte[] bar = new byte[j];
            System.arraycopy(foo, 0, bar, 0, j);
            return bar;
        } catch (ArrayIndexOutOfBoundsException var6) {
            throw new JSchException("fromBase64: invalid base64 data", var6);
        }
    }

    static byte[] toBase64(byte[] buf, int start, int length) {
        byte[] tmp = new byte[length * 2];
        int foo = length / 3 * 3 + start;
        int i = 0;

        int j;
        int k;
        for(j = start; j < foo; j += 3) {
            k = buf[j] >>> 2 & 63;
            tmp[i++] = b64[k];
            k = (buf[j] & 3) << 4 | buf[j + 1] >>> 4 & 15;
            tmp[i++] = b64[k];
            k = (buf[j + 1] & 15) << 2 | buf[j + 2] >>> 6 & 3;
            tmp[i++] = b64[k];
            k = buf[j + 2] & 63;
            tmp[i++] = b64[k];
        }

        foo = start + length - foo;
        if (foo == 1) {
            k = buf[j] >>> 2 & 63;
            tmp[i++] = b64[k];
            k = (buf[j] & 3) << 4 & 63;
            tmp[i++] = b64[k];
            tmp[i++] = 61;
            tmp[i++] = 61;
        } else if (foo == 2) {
            k = buf[j] >>> 2 & 63;
            tmp[i++] = b64[k];
            k = (buf[j] & 3) << 4 | buf[j + 1] >>> 4 & 15;
            tmp[i++] = b64[k];
            k = (buf[j + 1] & 15) << 2 & 63;
            tmp[i++] = b64[k];
            tmp[i++] = 61;
        }

        byte[] bar = new byte[i];
        System.arraycopy(tmp, 0, bar, 0, i);
        return bar;
    }

    static String[] split(String foo, String split) {
        if (foo == null) {
            return null;
        } else {
            byte[] buf = str2byte(foo);
            Vector bar = new Vector();
            int start = 0;

            while(true) {
                int index = foo.indexOf(split, start);
                if (index < 0) {
                    bar.addElement(byte2str(buf, start, buf.length - start));
                    String[] result = new String[bar.size()];

                    for(int i = 0; i < result.length; ++i) {
                        result[i] = (String)((String)bar.elementAt(i));
                    }

                    return result;
                }

                bar.addElement(byte2str(buf, start, index - start));
                start = index + 1;
            }
        }
    }

    static boolean glob(byte[] pattern, byte[] name) {
        return glob0(pattern, 0, name, 0);
    }

    private static boolean glob0(byte[] pattern, int pattern_index, byte[] name, int name_index) {
        if (name.length > 0 && name[0] == 46) {
            if (pattern.length > 0 && pattern[0] == 46) {
                return pattern.length == 2 && pattern[1] == 42 ? true : glob(pattern, pattern_index + 1, name, name_index + 1);
            } else {
                return false;
            }
        } else {
            return glob(pattern, pattern_index, name, name_index);
        }
    }

    private static boolean glob(byte[] pattern, int pattern_index, byte[] name, int name_index) {
        int patternlen = pattern.length;
        if (patternlen == 0) {
            return false;
        } else {
            int namelen = name.length;
            int i = pattern_index;
            int j = name_index;

            while(i < patternlen && j < namelen) {
                if (pattern[i] == 92) {
                    if (i + 1 == patternlen) {
                        return false;
                    }

                    ++i;
                    if (pattern[i] != name[j]) {
                        return false;
                    }

                    i += skipUTF8Char(pattern[i]);
                    j += skipUTF8Char(name[j]);
                } else {
                    if (pattern[i] == 42) {
                        while(i < patternlen && pattern[i] == 42) {
                            ++i;
                        }

                        if (patternlen == i) {
                            return true;
                        }

                        byte foo = pattern[i];
                        if (foo == 63) {
                            while(j < namelen) {
                                if (glob(pattern, i, name, j)) {
                                    return true;
                                }

                                j += skipUTF8Char(name[j]);
                            }

                            return false;
                        }

                        if (foo == 92) {
                            if (i + 1 == patternlen) {
                                return false;
                            }

                            ++i;

                            for(foo = pattern[i]; j < namelen; j += skipUTF8Char(name[j])) {
                                if (foo == name[j] && glob(pattern, i + skipUTF8Char(foo), name, j + skipUTF8Char(name[j]))) {
                                    return true;
                                }
                            }

                            return false;
                        }

                        while(j < namelen) {
                            if (foo == name[j] && glob(pattern, i, name, j)) {
                                return true;
                            }

                            j += skipUTF8Char(name[j]);
                        }

                        return false;
                    }

                    if (pattern[i] == 63) {
                        ++i;
                        j += skipUTF8Char(name[j]);
                    } else {
                        if (pattern[i] != name[j]) {
                            return false;
                        }

                        i += skipUTF8Char(pattern[i]);
                        j += skipUTF8Char(name[j]);
                        if (j >= namelen) {
                            if (i >= patternlen) {
                                return true;
                            }

                            if (pattern[i] == 42) {
                                break;
                            }
                        }
                    }
                }
            }

            if (i == patternlen && j == namelen) {
                return true;
            } else if (j >= namelen && pattern[i] == 42) {
                boolean ok = true;

                while(i < patternlen) {
                    if (pattern[i++] != 42) {
                        ok = false;
                        break;
                    }
                }

                return ok;
            } else {
                return false;
            }
        }
    }

    static String quote(String path) {
        byte[] _path = str2byte(path);
        int count = 0;

        for(int i = 0; i < _path.length; ++i) {
            byte b = _path[i];
            if (b == 92 || b == 63 || b == 42) {
                ++count;
            }
        }

        if (count == 0) {
            return path;
        } else {
            byte[] _path2 = new byte[_path.length + count];
            int i = 0;

            for(int var5 = 0; i < _path.length; ++i) {
                byte b = _path[i];
                if (b == 92 || b == 63 || b == 42) {
                    _path2[var5++] = 92;
                }

                _path2[var5++] = b;
            }

            return byte2str(_path2);
        }
    }

    static String unquote(String path) {
        byte[] foo = str2byte(path);
        byte[] bar = unquote(foo);
        return foo.length == bar.length ? path : byte2str(bar);
    }

    static byte[] unquote(byte[] path) {
        int pathlen = path.length;
        int i = 0;

        while(i < pathlen) {
            if (path[i] == 92) {
                if (i + 1 == pathlen) {
                    break;
                }

                System.arraycopy(path, i + 1, path, i, path.length - (i + 1));
                --pathlen;
                ++i;
            } else {
                ++i;
            }
        }

        if (pathlen == path.length) {
            return path;
        } else {
            byte[] foo = new byte[pathlen];
            System.arraycopy(path, 0, foo, 0, pathlen);
            return foo;
        }
    }

    static String getFingerPrint(HASH hash, byte[] data) {
        try {
            hash.init();
            hash.update(data, 0, data.length);
            byte[] foo = hash.digest();
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < foo.length; ++i) {
                int bar = foo[i] & 255;
                sb.append(chars[bar >>> 4 & 15]);
                sb.append(chars[bar & 15]);
                if (i + 1 < foo.length) {
                    sb.append(":");
                }
            }

            return sb.toString();
        } catch (Exception var6) {
            return "???";
        }
    }

    static boolean array_equals(byte[] foo, byte[] bar) {
        int i = foo.length;
        if (i != bar.length) {
            return false;
        } else {
            for(int j = 0; j < i; ++j) {
                if (foo[j] != bar[j]) {
                    return false;
                }
            }

            return true;
        }
    }

    static Socket createSocket(final String host, final int port, int timeout) throws JSchException {
        Socket socket = null;
        if (timeout == 0) {
            try {
                socket = new Socket(host, port);
                return socket;
            } catch (Exception var12) {
                String message = var12.toString();
                if (var12 instanceof Throwable) {
                    throw new JSchException(message, var12);
                } else {
                    throw new JSchException(message);
                }
            }
        } else {
            final Socket[] sockp = new Socket[1];
            final Exception[] ee = new Exception[1];
            String message = "";
            Thread tmp = new Thread(new Runnable() {
                public void run() {
                    sockp[0] = null;

                    try {
                        sockp[0] = new Socket(host, port);
                    } catch (Exception var4) {
                        ee[0] = var4;
                        if (sockp[0] != null && sockp[0].isConnected()) {
                            try {
                                sockp[0].close();
                            } catch (Exception var3) {
                                ;
                            }
                        }

                        sockp[0] = null;
                    }

                }
            });
            tmp.setName("Opening Socket " + host);
            tmp.start();

            try {
                tmp.join((long)timeout);
                message = "timeout: ";
            } catch (InterruptedException var11) {
                ;
            }

            if (sockp[0] != null && sockp[0].isConnected()) {
                socket = sockp[0];
                return socket;
            } else {
                message = message + "socket is not established";
                if (ee[0] != null) {
                    message = ee[0].toString();
                }

                tmp.interrupt();
                tmp = null;
                throw new JSchException(message, ee[0]);
            }
        }
    }

    static byte[] str2byte(String str, String encoding) {
        if (str == null) {
            return null;
        } else {
            try {
                return str.getBytes(encoding);
            } catch (UnsupportedEncodingException var3) {
                return str.getBytes();
            }
        }
    }

    static byte[] str2byte(String str) {
        return str2byte(str, "UTF-8");
    }

    static String byte2str(byte[] str, String encoding) {
        return byte2str(str, 0, str.length, encoding);
    }

    static String byte2str(byte[] str, int s, int l, String encoding) {
        try {
            return new String(str, s, l, encoding);
        } catch (UnsupportedEncodingException var5) {
            return new String(str, s, l);
        }
    }

    static String byte2str(byte[] str) {
        return byte2str(str, 0, str.length, "UTF-8");
    }

    static String byte2str(byte[] str, int s, int l) {
        return byte2str(str, s, l, "UTF-8");
    }

    static String toHex(byte[] str) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < str.length; ++i) {
            String foo = Integer.toHexString(str[i] & 255);
            sb.append("0x" + (foo.length() == 1 ? "0" : "") + foo);
            if (i + 1 < str.length) {
                sb.append(":");
            }
        }

        return sb.toString();
    }

    static void bzero(byte[] foo) {
        if (foo != null) {
            for(int i = 0; i < foo.length; ++i) {
                foo[i] = 0;
            }

        }
    }

    static String diffString(String str, String[] not_available) {
        String[] stra = split(str, ",");
        String result = null;

        label27:
        for(int i = 0; i < stra.length; ++i) {
            for(int j = 0; j < not_available.length; ++j) {
                if (stra[i].equals(not_available[j])) {
                    continue label27;
                }
            }

            if (result == null) {
                result = stra[i];
            } else {
                result = result + "," + stra[i];
            }
        }

        return result;
    }

    static String checkTilde(String str) {
        try {
            if (str.startsWith("~")) {
                str = str.replace("~", System.getProperty("user.home"));
            }
        } catch (SecurityException var2) {
            ;
        }

        return str;
    }

    private static int skipUTF8Char(byte b) {
        if ((byte)(b & 128) == 0) {
            return 1;
        } else if ((byte)(b & 224) == -64) {
            return 2;
        } else {
            return (byte)(b & 240) == -32 ? 3 : 1;
        }
    }

    static byte[] fromFile(String _file) throws IOException {
        _file = checkTilde(_file);
        File file = new File(_file);
        FileInputStream fis = new FileInputStream(_file);

        try {
            byte[] result = new byte[(int)file.length()];
            int len = 0;

            while(true) {
                int i = fis.read(result, len, result.length - len);
                if (i <= 0) {
                    fis.close();
                    byte[] var9 = result;
                    return var9;
                }

                len += i;
            }
        } finally {
            if (fis != null) {
                fis.close();
            }

        }
    }
}
