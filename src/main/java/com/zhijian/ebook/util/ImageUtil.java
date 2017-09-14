package com.zhijian.ebook.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

    private static Image img;

    private static int width;

    private static int height;

    private static void init(byte[] imgData) throws IOException {
        InputStream is = new ByteArrayInputStream(imgData);
        img = ImageIO.read(is);
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }

    private static void init(InputStream is) throws IOException {
        img = ImageIO.read(is);
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }

    // public static byte[] Image(byte[] imgData) throws IOException {
    // init(imgData);
    // int ss = imgData.length/1024;
    // if(ss > 300){
    // int ff = ss/300;
    // int w = width/ff;
    // int h = height/ff;
    // return resizeFix(w, h, imgData);
    // }else{
    // return imgData;
    // }
    // //System.out.println(width);
    // //System.out.println(height);
    // }
    /**
     * 按照宽度还是高度进行压缩
     * 
     * @param w
     *            int 最大宽度
     * @param h
     *            int 最大高度
     * @param imgData
     *            图片的字节数组
     */
    public static byte[] resizeFix(int w, int h, byte[] imgData) throws IOException {
        init(imgData);
        if (width / height > w / h) {
            return resizeByWidth(w);
        } else {
            return resizeByHeight(h);
        }
    }

    /**
     * 按照宽度还是高度进行压缩
     * 
     * @param w
     *            int 最大宽度
     * @param h
     *            int 最大高度
     * @param is
     *            图片的输入流
     */
    public static InputStream resizeFix(int w, int h, InputStream is) throws IOException {
        init(is);
        if (width / height > w / h) {
            return new ByteArrayInputStream(resizeByWidth(w));
        } else {
            return new ByteArrayInputStream(resizeByWidth(h));
        }
    }

    /**
     * 以宽度为基准，等比例放缩图片
     * 
     * @param w
     *            int 新宽度
     */
    private static byte[] resizeByWidth(int w) throws IOException {
        int h = (int) (height * w / width);
        return resize(w, h);
    }

    /**
     * 以高度为基准，等比例缩放图片
     * 
     * @param h
     *            int 新高度
     */
    private static byte[] resizeByHeight(int h) throws IOException {
        int w = (int) (width * h / height);
        return resize(w, h);
    }

    /**
     * 强制压缩/放大图片到固定的大小
     * 
     * @param w
     *            int 新宽度
     * @param h
     *            int 新高度
     */
    private static byte[] resize(int w, int h) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        byte[] data = os.toByteArray();
        os.close();
        return data;
    }
}