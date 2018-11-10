package com.framework.qrcode.util;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.core.scheme.VCard;
import net.glxn.qrgen.javase.QRCode;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 二维码工具类
 * 默认输出png图片, 默认宽高: 500 x 500, 默认字符集: UTF-8
 * (目前第三方库的maven依赖命名不规范, 通过inno适配一层, 方便需要的话后续可以切换到其他库)
 * <code>
 // 文本型二维码
 String notificationTemplate = "【任务下发通知】任务编号+任务名称，请@微信号或者任务负责人中文名@微信号或者任务负责人中文名，请来汇杰8楼指令管理处领取任务，谢谢配合！";
 String forceReceiveTemplate = "【任务催收通知】任务编号+任务名称，请尚未“复核通过”的人员：@微信号或者任务负责人中文名@微信号或者任务负责人中文名，请速来汇杰8楼指令管理处交回任务书并变更状态，谢谢配合！";
 QRCodeUtils.generate(notificationTemplate, new File("/Users/bluethinking/Downloads/a-njcb-template.png"));
 QRCodeUtils.generate(forceReceiveTemplate, new File("/Users/bluethinking/Downloads/b-njcb-template.png"));

 // url型二维码
 QRCodeUtils.generate("http://www.e-vada.com", new File("/Users/bluethinking/Downloads/evadaSite.png"));

 // 名片型二维码
 VCard xiongDa = new VCard("杨小雄")
 .setEmail("xiaoxiong.yang@e-vada.com")
 .setAddress("厦门观音山运营中心18号楼1305单元")
 .setTitle("扫地的")
 .setCompany("深圳英华达科技有限公司")
 .setPhoneNumber("13799270328")
 .setWebsite("www.e-vada.com/");
 QRCodeUtils.generate(xiongDa, new File("/Users/bluethinking/Downloads/xiongda-vcard.png"));
 </code>
 */
public class QRCodeUtils {
    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 500;
    public static final ImageType DEFAULT_IMAGE_TYPE = ImageType.PNG;
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 生成文本/url型二维码图片流
     * @param textOrUrl     二维码中存放文本或url
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param outputStream  生成的图片写入的流
     * @param charset       字符集
     */
    public static void generate(String textOrUrl, ImageType imageType, int width, int height, OutputStream outputStream, Charset charset) {
        try {
            QRCode.from(textOrUrl)
                    .withCharset(charset.name())
                    .to(imageType)
                    .withSize(width, height)
                    .stream()
                    .writeTo(outputStream);
        } catch (IOException e) {
            throw new IllegalStateException("二维码生成到文件系统失败。", e);
        }
    }

    /**
     * 生成文本/url型二维码图片
     * @param textOrUrl     二维码中存放文本或url
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param targetFile    生成的图片文件
     * @param charset       字符集
     */
    public static void generate(String textOrUrl, ImageType imageType, int width, int height, File targetFile, Charset charset) {
        try {
            generate(textOrUrl, imageType, width, height, new FileOutputStream(targetFile), charset);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("二维码生成的目标文件找不到。", e);
        }
    }

    /**
     * 生成文本/url型二维码图片
     * 默认使用UTF-8编码
     * @param textOrUrl     二维码中存放文本或url
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param targetFile    生成的图片文件
     */
    public static void generate(String textOrUrl, ImageType imageType, int width, int height, File targetFile) {
        generate(textOrUrl, imageType, width, height, targetFile, DEFAULT_CHARSET);
    }

    /**
     * 生成文本/url型二维码图片
     * 默认生成PNG图片, 使用UTF-8编码
     * @param textOrUrl     二维码中存放文本或url
     * @param width         图片宽度
     * @param height        图片高度
     * @param targetFile    生成的图片文件
     */
    public static void generate(String textOrUrl, int width, int height, File targetFile) {
        generate(textOrUrl, DEFAULT_IMAGE_TYPE, width, height, targetFile, DEFAULT_CHARSET);
    }

    /**
     * 生成文本/url型二维码图片
     * 默认生成500 x 500的PNG图片, 使用UTF-8编码
     * @param textOrUrl     二维码中存放文本或url
     * @param targetFile    生成的图片文件
     */
    public static void generate(String textOrUrl, File targetFile) {
        generate(textOrUrl, DEFAULT_IMAGE_TYPE, DEFAULT_WIDTH, DEFAULT_HEIGHT, targetFile, DEFAULT_CHARSET);
    }

    /**
     * 生成文本/url型二维码图片流
     * 默认使用UTF-8编码
     * @param textOrUrl     二维码中存放文本或url
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param outputStream  生成的图片写入的流
     */
    public static void generate(String textOrUrl, ImageType imageType, int width, int height, OutputStream outputStream) {
        generate(textOrUrl, imageType, width, height, outputStream, DEFAULT_CHARSET);
    }

    /**
     * 生成文本/url型二维码图片流
     * 默认生成PNG图片, 使用UTF-8编码
     * @param textOrUrl     二维码中存放文本或url
     * @param width         图片宽度
     * @param height        图片高度
     * @param outputStream  生成的图片写入的流
     */
    public static void generate(String textOrUrl, int width, int height, OutputStream outputStream) {
        generate(textOrUrl, DEFAULT_IMAGE_TYPE, width, height, outputStream, DEFAULT_CHARSET);
    }

    /**
     * 生成文本/url型二维码图片流
     * 默认生成500 x 500的PNG图片, 使用UTF-8编码
     * @param textOrUrl     二维码中存放文本或url
     * @param outputStream  生成的图片写入的流
     */
    public static void generate(String textOrUrl, OutputStream outputStream) {
        generate(textOrUrl, DEFAULT_IMAGE_TYPE, DEFAULT_WIDTH, DEFAULT_HEIGHT, outputStream, DEFAULT_CHARSET);
    }

    /**
     * 生成名片型二维码图片
     * @param vCard         二维码名片
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param outputStream  生成的图片写入的流
     * @param charset       字符集
     */
    public static void generate(VCard vCard, ImageType imageType, int width, int height, OutputStream outputStream, Charset charset) {
        try {
            QRCode.from(vCard)
                    .withCharset(charset.name())
                    .to(imageType)
                    .withSize(width, height)
                    .stream()
                    .writeTo(outputStream);
        } catch (IOException e) {
            throw new IllegalStateException("二维码生成到文件系统失败。", e);
        }
    }

    /**
     * 生成名片型二维码图片
     * @param vCard         二维码名片
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param targetFile    生成的图片文件
     * @param charset       字符集
     */
    public static void generate(VCard vCard, ImageType imageType, int width, int height, File targetFile, Charset charset) {
        try {
            generate(vCard, imageType, width, height, new FileOutputStream(targetFile), charset);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("二维码生成的目标文件找不到。", e);
        }
    }

    /**
     * 生成名片型二维码图片
     * 默认使用UTF-8编码
     * @param vCard         二维码名片
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param targetFile    生成的图片文件
     */
    public static void generate(VCard vCard, ImageType imageType, int width, int height, File targetFile) {
        generate(vCard, imageType, width, height, targetFile, DEFAULT_CHARSET);
    }

    /**
     * 生成名片型二维码图片
     * 默认生成PNG图片, 使用UTF-8编码
     * @param vCard         二维码名片
     * @param width         图片宽度
     * @param height        图片高度
     * @param targetFile    生成的图片文件
     */
    public static void generate(VCard vCard, int width, int height, File targetFile) {
        generate(vCard, DEFAULT_IMAGE_TYPE, width, height, targetFile, DEFAULT_CHARSET);
    }

    /**
     * 生成名片型二维码图片
     * 默认生成500 x 500的PNG图片, 使用UTF-8编码
     * @param vCard         二维码名片
     * @param targetFile    生成的图片文件
     */
    public static void generate(VCard vCard, File targetFile) {
        generate(vCard, DEFAULT_IMAGE_TYPE, DEFAULT_WIDTH, DEFAULT_HEIGHT, targetFile, DEFAULT_CHARSET);
    }

    /**
     * 生成名片型二维码图片
     * 默认使用UTF-8编码
     * @param vCard         二维码名片
     * @param imageType     二维码图片类型: PNG, GIF, JPEG等
     * @param width         图片宽度
     * @param height        图片高度
     * @param outputStream  生成的图片写入的流
     */
    public static void generate(VCard vCard, ImageType imageType, int width, int height, OutputStream outputStream) {
        generate(vCard, imageType, width, height, outputStream, DEFAULT_CHARSET);
    }

    /**
     * 生成名片型二维码图片
     * 默认生成PNG图片, 使用UTF-8编码
     * @param vCard         二维码名片
     * @param width         图片宽度
     * @param height        图片高度
     * @param outputStream  生成的图片写入的流
     */
    public static void generate(VCard vCard, int width, int height, OutputStream outputStream) {
        generate(vCard, DEFAULT_IMAGE_TYPE, width, height, outputStream, DEFAULT_CHARSET);
    }

    /**
     * 生成名片型二维码图片
     * 默认生成500 x 500的PNG图片, 使用UTF-8编码
     * @param vCard         二维码名片
     * @param outputStream  生成的图片写入的流
     */
    public static void generate(VCard vCard, OutputStream outputStream) {
        generate(vCard, DEFAULT_IMAGE_TYPE, DEFAULT_WIDTH, DEFAULT_HEIGHT, outputStream, DEFAULT_CHARSET);
    }

}
