package glvis;

import glvis.renderers.*;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector2f;

public class VisCore {

    public static float[] avgbuff = new float[4096];
    static ArrayList<String> blacklist = new ArrayList();
    public final int width = 1280;
    public final int height = 720;
    private int fragmentShader;
    private int shaderProgram;
    int mx = 400;
    int my = 300;
    float lx = 0.0F;
    float ly = 0.0F;
    float or = 0.0F;
    float og = 0.0F;
    float dist = 0.0F;
    boolean first = true;
    int itr = 0;
    int sitr = 0;
    float mxc = 0.0F;
    float qwerp = 0.0F;
    float randadd = 0.0F;
    float acl_x = 640.0F;
    float acl_y = 280.0F;
    float osc_x = 0.0F;
    float osc_y = 0.0F;
    float vpm_x = 0.0F;
    float vpm_y = 0.0F;
    boolean flck = false;
    int uloc0 = 0;
    float clca = 0.0F;
    int uloc1 = 0;
    double calc0 = 0.0D;
    double calc1 = 0.0D;
    float hei = 0.0F;
    int ssitr = 0;
    int lwheel = 0;
    float olx = 0.0F;
    float oly = 0.0F;
    int obx = 0;
    int oby = 0;
    int flckstp = 0;
    boolean adown = false;
    boolean ddown = false;
    boolean wdown = false;
    boolean sdown = false;
    boolean skip = false;
    boolean entcmd = false;
    boolean lbd = false;
    boolean ischarblock = true;
    float velacly = 0.0F;
    boolean down = false;
    boolean needsrel = false;
    boolean rtcreate = false;
    int rtstartx = 0;
    int rtstarty = 0;
    int rtcx = 0;
    int rtcy = 0;
    String ent = "";
//   static Player stpl = new Player(false);
    static ArrayList<File> songs = new ArrayList();
    static String ons = "";
    boolean rectool = false;
//    static TrueTypeFont font = null;
    boolean editor = true;
    boolean lighttool = false;

    public static void setLibraryPath(String path) throws Exception {

        System.setProperty("java.library.path", path);
        Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
        sysPathsField.setAccessible(true);
        sysPathsField.set((Object) null, (Object) null);
    }

    public static void addLibraryPath(String pathToAdd) throws Exception {
        Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);
        String[] paths = (String[]) ((String[]) usrPathsField.get((Object) null));
        String[] newPaths = paths;
        int var4 = paths.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            String path = newPaths[var5];
            if (path.equals(pathToAdd)) {
                return;
            }
        }

        newPaths = (String[]) Arrays.copyOf(paths, paths.length + 1);
        newPaths[newPaths.length - 1] = pathToAdd;
        usrPathsField.set((Object) null, newPaths);
    }

    
    boolean initial = true;

    AudioProvider provider;
    VisRender renderer;
    
    private void render() {
        renderer.render();
        Display.update();
    }
    
    private void initialize() {
        try {
            Display.setDisplayMode(new DisplayMode(1280, 720));
            Display.setTitle("2D Lighting");
            Display.create(new PixelFormat(0, 16, 1));
        } catch (LWJGLException var4) {
            var4.printStackTrace();
        }

        this.shaderProgram = GL20.glCreateProgram();
        this.fragmentShader = GL20.glCreateShader('\u8b30');
        StringBuilder fragmentShaderSource = new StringBuilder();

        try {
            fragmentShaderSource.append(NXUtils.readUTF8(NXUtils.getInputStream("jar:///glvis/shader.frag")));
        } catch (IOException v) {
            Logger.getLogger(VisCore.class.getName()).log(Level.SEVERE, (String) null, v);
        }

        GL20.glShaderSource(this.fragmentShader, (CharSequence) fragmentShaderSource);
        GL20.glCompileShader(this.fragmentShader);
        if (GL20.glGetShaderi(this.fragmentShader, '\u8b81') == 0) {
            System.err.println("Fragment shader not compiled!");
        }

        GL20.glAttachShader(this.shaderProgram, this.fragmentShader);
        GL20.glLinkProgram(this.shaderProgram);
        GL20.glValidateProgram(this.shaderProgram);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, 1280.0D, 720.0D, 0.0D, 1.0D, -1.0D);
        GL11.glMatrixMode(5888);
        GL11.glEnable(2960);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Font awtFont = new Font("Arial", 0, 12);
        provider = new JLayerProvider();
        renderer = new HighIntensityAccurate(provider, shaderProgram);
        Display.sync(60);
        
    }

    private void cleanup() {
        GL20.glDeleteShader(this.fragmentShader);
        GL20.glDeleteProgram(this.shaderProgram);
        Display.destroy();
    }
    
    public static void main(String[] args) {
        for(int i = 1; i < 101; i++)System.out.println((i%3==0&&i%5==0)?"FizzBuzz":i%3==0?"Fizz":i%5==0?"Buzz":i);

        try {
            setLibraryPath("./native/");
            addLibraryPath("./native/");
        } catch (Exception var2) {
            ;
        }

        VisCore vc = new VisCore();
        vc.initialize();

        while (!Display.isCloseRequested()) {
            vc.render();
        }

        vc.cleanup();
    }


}
