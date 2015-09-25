package glvis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import javax.net.ssl.HttpsURLConnection;

public class NXUtils {

   public static String useragent = "Mozilla/5.0 (JVM; rv:1.7.0) ModLaunch/1.0";
   public static HashMap<String, String> lastcookies = new HashMap();
   public static String lastdisposition = "";
   public static long lastcontentlength = 0L;


   public static InputStream getInputStream(String str) {
      HashMap map = new HashMap();
      map.put("mlrev", "0");

      try {
         return getInputStream(str, map, (new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")).parse("Sat, 01 Jan 4000 00:00:00 GMT"));
      } catch (ParseException var3) {
         Logger.getLogger(NXUtils.class.getName()).log(Level.SEVERE, (String)null, var3);
         return null;
      }
   }

   public static InputStream getInputStream(String str, HashMap<String, String> cookie, Date expire) {
      lastcookies = cookie;
      System.out.println("Requested get input stream " + str);
      String redirect = "";
      if(str.startsWith("jar://")) {
         lastcontentlength = -1L;
         return NXUtils.class.getResourceAsStream(str.substring(6));
      } else {
         Map map;
         String[] fs;
         Iterator hst;
         String s;
         Iterator r;
         String r1;
         String[] var11;
         int var12;
         int var13;
         String f;
         String eq;
         int er;
         if(str.startsWith("http://")) {
            try {
               URLConnection var22 = (new URL(str)).openConnection();
               var22.setRequestProperty("User-Agent", useragent);
               if(expire.after(new Date())) {
                  var22.setRequestProperty("Cookie", format(cookie));
               }

               map = var22.getHeaderFields();
               lastcontentlength = var22.getContentLengthLong();
               fs = null;
               hst = map.keySet().iterator();

               while(hst.hasNext()) {
                  s = (String)hst.next();

                  try {
                     r = ((List)map.get(s)).iterator();

                     while(r.hasNext()) {
                        r1 = (String)r.next();
                        System.out.println(s + "->" + r1);
                        if(s.equals("Expires")) {
                           expire = (new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")).parse(r1);
                        }

                        if(s.equals("Content-Disposition")) {
                           lastdisposition = r1;
                        }

                        if(s.equals("Set-Cookie")) {
                           var11 = r1.split("; ");
                           var12 = var11.length;

                           for(var13 = 0; var13 < var12; ++var13) {
                              f = var11[var13];
                              if(f.contains("=")) {
                                 fs = f.split("=");
                                 if(cookie.containsKey(fs[0])) {
                                    cookie.remove(fs[0]);
                                 }

                                 eq = fs[1];
                                 if(fs.length > 2) {
                                    for(er = 2; er < fs.length; ++er) {
                                       eq = eq + "=" + fs[er];
                                    }
                                 }

                                 cookie.put(fs[0], eq);
                              } else {
                                 cookie.put(f, null);
                              }
                           }
                        }

                        lastcookies = cookie;
                        if(s.equals("Location")) {
                           redirect = r1;
                        }
                     }
                  } catch (Throwable var20) {
                     ;
                  }
               }

               if(redirect.length() > 1) {
                  System.out.println("Redirecting to->" + redirect);
                  return getInputStream(redirect, cookie, expire);
               }

               if(str.contains("wikispaces")) {
                  System.out.println("THIS. IS. WIKISPACES!");
                  String var23 = str.split("://")[1].split("/")[0];
                  System.out.println("Opening " + str.replaceAll(var23, "localhost:21421"));
                  s = readUTF8(var22.getInputStream());
                  String var24 = "script type=\"text/javascript\">";
                  var24 = var24 + "var xmlhttp = new XMLHttpRequest();";
                  var24 = var24 + "var encodedbod = encodeURIComponent(document.documentElement.innerHTML);";
                  var24 = var24 + "xmlhttp.open(\"POST\",\"callback\",true);";
                  var24 = var24 + "xmlhttp.setRequestHeader(\"Content-type\",\"application/x-www-form-urlencoded\");";
                  var24 = var24 + "xmlhttp.send(\"body=\" + encodedbod);";
                  var24 = var24 + "alert(\'ModLaunch is done with this page now, feel free to close it\');";
                  var24 = var24 + "</script>";
                  s = s.replace("/body", var24 + "</body");
                  byte[] var25 = s.getBytes();
                  return null;
               }

               return var22.getInputStream();
            } catch (Exception var21) {
               ;
            }
         }

         if(str.startsWith("https://")) {
            try {
               HttpsURLConnection ex = (HttpsURLConnection)(new URL(str)).openConnection();
               ex.setRequestProperty("User-Agent", useragent);
               if(expire.after(new Date())) {
                  ex.setRequestProperty("Cookie", format(cookie));
               }

               map = ex.getHeaderFields();
               lastcontentlength = ex.getContentLengthLong();
               fs = null;
               hst = map.keySet().iterator();

               while(hst.hasNext()) {
                  s = (String)hst.next();

                  try {
                     r = ((List)map.get(s)).iterator();

                     while(r.hasNext()) {
                        r1 = (String)r.next();
                        System.out.println(s + "->" + r1);
                        if(s.equals("Expires")) {
                           expire = (new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z")).parse(r1);
                        }

                        if(s.equals("Content-Disposition")) {
                           lastdisposition = r1;
                        }

                        if(s.equals("Set-Cookie")) {
                           var11 = r1.split("; ");
                           var12 = var11.length;

                           for(var13 = 0; var13 < var12; ++var13) {
                              f = var11[var13];
                              if(f.contains("=")) {
                                 fs = f.split("=");
                                 if(cookie.containsKey(fs[0])) {
                                    cookie.remove(fs[0]);
                                 }

                                 eq = fs[1];
                                 if(fs.length > 2) {
                                    for(er = 2; er < fs.length; ++er) {
                                       eq = eq + "=" + fs[er];
                                    }
                                 }

                                 cookie.put(fs[0], eq);
                              } else {
                                 cookie.put(f, null);
                              }
                           }
                        }

                        if(s.equals("Location")) {
                           redirect = r1;
                        }
                     }
                  } catch (Throwable var18) {
                     ;
                  }
               }

               if(redirect.length() > 1) {
                  System.out.println("Redirecting to->" + redirect + " with cookie " + format(cookie));
                  return getInputStream(redirect, cookie, expire);
               }

               return ex.getInputStream();
            } catch (Exception var19) {
               ;
            }
         }

         if(str.startsWith("file://")) {
            try {
               lastcontentlength = (new File(str.substring(7))).length();
               return new FileInputStream(str.substring(7));
            } catch (FileNotFoundException var17) {
               ;
            }
         }

         return null;
      }
   }

   public static String getSum(String file) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         DigestInputStream ex = new DigestInputStream(new FileInputStream(file), md);
         boolean r = false;
         byte[] b = new byte[16258];

         while(ex.read(b) > -1) {
            ;
         }

         b = md.digest();
         String ret = "";
         byte[] var6 = b;
         int var7 = b.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            byte q = var6[var8];
            ret = ret + Integer.toString((q & 255) + 256, 16).substring(1);
         }

         return ret;
      } catch (Exception var10) {
         return null;
      }
   }

   public static String readUTF8(String from) throws IOException {
      String ret = "";
      InputStream in = getInputStream(from);
      boolean r = false;

      int r1;
      for(byte[] b = new byte[2048]; (r1 = in.read(b)) > -1; ret = ret + new String(b, 0, r1)) {
         ;
      }

      return ret;
   }

   public static String cleanscript(String s) throws IOException {
      if(s.startsWith(" type=\"text/javascript\">")) {
         s = s.substring(24);
      } else if(s.startsWith(" src=\"")) {
         String[] bits = s.split("\"");
         String remotescript = readUTF8(bits[1]);
         System.err.println(remotescript);
      }

      return s;
   }

   public static void extractZIP(String file, String file0) throws FileNotFoundException, IOException {
      JarFile fil = new JarFile(new File(file));
      String[] splits = file.split("/");
      String packname = splits[splits.length - 1];
      System.out.println("Extracting " + packname);
      Enumeration ents = fil.entries();
      ZipEntry ent = null;

      while(ents.hasMoreElements()) {
         ent = (ZipEntry)ents.nextElement();
         InputStream in = fil.getInputStream(ent);
         ByteArrayOutputStream tout = new ByteArrayOutputStream();
         boolean r = false;
         byte[] b = new byte[1024];

         int r1;
         while((r1 = in.read(b)) > -1) {
            tout.write(b, 0, r1);
         }

         b = tout.toByteArray();
         if(ent.isDirectory()) {
            (new File(file0 + "/" + ent.getName())).mkdirs();
         } else {
            FileOutputStream out = new FileOutputStream(file0 + "/" + ent.getName());
            out.write(b);
            out.flush();
            out.close();
         }
      }

   }

   public static String readUTF8(InputStream in) throws IOException {
      String ret = "";
      boolean r = false;

      int r1;
      for(byte[] b = new byte[2048]; (r1 = in.read(b)) > -1; ret = ret + new String(b, 0, r1)) {
         ;
      }

      return ret;
   }

   public static void extractZIP(String file, String file0, String ignore) throws FileNotFoundException, IOException {
      JarFile fil = new JarFile(new File(file));
      String[] splits = file.split("/");
      String packname = splits[splits.length - 1];
      System.out.println("Extracting " + packname);
      Enumeration ents = fil.entries();
      ZipEntry ent = null;

      while(ents.hasMoreElements()) {
         ent = (ZipEntry)ents.nextElement();
         InputStream in = fil.getInputStream(ent);
         ByteArrayOutputStream tout = new ByteArrayOutputStream();
         boolean r = false;
         byte[] b = new byte[1024];

         int r1;
         while((r1 = in.read(b)) > -1) {
            tout.write(b, 0, r1);
         }

         b = tout.toByteArray();
         if(ent.isDirectory()) {
            (new File(file0 + ent.getName().replaceAll(ignore, ""))).mkdirs();
         } else {
            FileOutputStream out = new FileOutputStream(file0 + ent.getName().replaceAll(ignore, ""));
            out.write(b);
            out.flush();
            out.close();
         }
      }

   }

   public static String format(HashMap<String, String> cookie) {
      String ret = "";
      String get = "";
      Iterator var3 = cookie.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         if(ret.length() > 1) {
            ret = ret + "; ";
         }

         ret = ret + key;
         get = (String)cookie.get(key);
         if(get != null) {
            ret = ret + "=";
            ret = ret + get;
         }
      }

      return ret;
   }

   public static void copyFile(File from, File to, String basedir) throws FileNotFoundException, IOException {
      int r;
      if(from.isDirectory()) {
         File[] in = from.listFiles();
         int out = in.length;

         for(r = 0; r < out; ++r) {
            File b = in[r];
            copyFile(b, new File(to.toString() + b.toString().substring(0, basedir.length())), basedir);
         }
      } else if(!to.exists()) {
         FileInputStream var7 = new FileInputStream(from);
         FileOutputStream var8 = new FileOutputStream(to);
         boolean var9 = false;
         byte[] var10 = new byte[8129];

         while((r = var7.read(var10)) > -1) {
            var8.write(var10, 0, r);
         }

         var8.flush();
         var7.close();
         var8.close();
      }

   }

   public static void copyFile(File from, File to) throws FileNotFoundException, IOException {
      if(!to.exists()) {
         FileInputStream in = new FileInputStream(from);
         FileOutputStream out = new FileOutputStream(to);
         boolean r = false;
         byte[] b = new byte[8129];

         int r1;
         while((r1 = in.read(b)) > -1) {
            out.write(b, 0, r1);
         }

         out.flush();
         in.close();
         out.close();
      }

   }

}
