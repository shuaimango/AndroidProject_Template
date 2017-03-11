package com.mango.framework.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Util_bitmap {

    private static final String TAG = "Util_bitmap";
    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public static int getPictureDegree(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }
        return degree;
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getExpectBitmap(Bitmap bitmap, float sx, float sy) {
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static Bitmap getScreenShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        // 获取屏幕宽和高
        int widths = display.getWidth();
        int heights = display.getHeight();
        System.out.println("getScreenShot view.getDrawingCache().getWidth():" + view.getDrawingCache().getWidth() + ",view.getDrawingCache().getHeight():" + view.getDrawingCache().getHeight() + "width:" + widths + ",height:" + heights + ",statusBarHeights:" + statusBarHeights);
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        if (view.getDrawingCache().getWidth() < widths) {
            widths = view.getDrawingCache().getWidth();
        }
        if (view.getDrawingCache().getHeight() < heights - statusBarHeights) {
            heights = view.getDrawingCache().getWidth() + statusBarHeights;
        }
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0,
                statusBarHeights, widths, heights - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 屏幕上截图的二种方式：http://blog.csdn.net/jokers_i/article/details/39549633
     */
    public static Bitmap getViewBitmap(View view, int width, int height) {
        view.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height,
                View.MeasureSpec.EXACTLY);
        // validate view.measurewidth and view.measureheight
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.layout(0, 0, width, height);
        Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas cvs = new Canvas(bmp);
        view.draw(cvs);
        return bmp;
    }

    public static Bitmap getViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bm, String pathName) {
        // pathName后缀需是png格式
        saveBitmap(bm, pathName, Bitmap.CompressFormat.PNG);
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bm, String pathName, Bitmap.CompressFormat format) {
        saveBitmap(bm, pathName, format, 75);
    }

    /**
     * 保存方法
     */
    public static void saveBitmap(Bitmap bm, String pathName, Bitmap.CompressFormat format, int quality) {
        // pathName后缀需是png格式
        File f = new File(pathName);
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            bm.compress(format, quality, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 图片的缩放方法
     *
     * @param srcImg    ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap srcImg, int newWidth, int newHeight) {
        // 获取这个图片的宽和高
        int width = srcImg.getWidth();
        int height = srcImg.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = (float) newWidth / (float) width;
        float scaleHeight = (float) newHeight / (float) height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(srcImg, 0, 0, width, height, matrix, true);
        return bitmap;
    }


    /**
     * 将bitmap转换成指定大小的Options
     *
     * @return
     */
    public static BitmapFactory.Options getFitOptions(String pathName, int reqWidth) {
        return getFitOptions(pathName, reqWidth, 1024);
    }


    /**
     * 将bitmap转换成指定大小的Options
     *
     * @return
     */
    public static BitmapFactory.Options getFitOptions(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 设置为true,表示解析Bitmap对象，该对象不占内存
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false; // 设置为false,解析Bitmap对象加入到内存中
        // 当isPurgable设为true时，系统中内存不足时，可以回收部分Bitmap占据的内存空间，这时一般不会出现OutOfMemory
        // 错误.inInputShareable与inPurgeable结合使用
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inDither = false;    /*不进行图片抖动处理*/
        options.inPreferredConfig = null;  /*设置让解码器以最佳方式解码*/
        // ALPHA_8：每个像素占用 1byte 内存、ARGB_4444:每个像素占用 2byte
        // 内存、ARGB_8888:每个像素占用
        // 4byte 内存、RGB_565:每个像素占用 2byte
        // 内存。Android默认的颜色模式为ARGB_8888，这个颜色模式色彩最细腻，显示质量最高。但同样的，占用的内存也最大。以上代码即是将图片资源以
        // RGB_565 （或以 ARGB_4444）模式读出。内存减少虽然不如第一种方法明显，但是对于大多数图片，看不出与
        // ARGB_8888
        // 模式有什么差别。不过在读取有渐变效果的图片时，可能有颜色条出现。另外，会影响图片的特效处理。
        // options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return options;
    }

    /**
     * @return[width,height]
     */
    public static int[] getImgWH(String pathName) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 设置为true,表示解析Bitmap对象，该对象不占内存
        options.inSampleSize = 1;//设为1才是返回图片原始大小
        BitmapFactory.decodeFile(pathName, options);
        return new int[]{options.outWidth, options.outHeight};
    }

    /**
     * 获取合适尺寸的bitmap,以节约内存
     *
     * @return
     */
    public static Bitmap getFitBitmap(String pathName, int reqWidth, int maxHeight) {
        return BitmapFactory.decodeFile(pathName, getFitOptions(pathName, reqWidth, maxHeight));
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 合并两张bitmap为一张
     *
     * @param background
     * @param foreground
     * @return Bitmap
     */
    public static Bitmap combineBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }
        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        int fgWidth = foreground.getWidth();
        int fgHeight = foreground.getHeight();
        Bitmap newmap = Bitmap
                .createBitmap(bgWidth, bgHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(newmap);
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(foreground, (bgWidth - fgWidth) / 2,
                (bgHeight - fgHeight) / 2, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return newmap;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toCenterRectBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            float clip = (height - width) / 2;
            top = clip;
            bottom = height - clip;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static String getCompressUrl(String srcUrl, int w) {
        return getCompressUrl(srcUrl, w, -1);
    }

    public static String getCompressUrl(String srcUrl, int w, int h) {
        return getCompressUrl(srcUrl, w, h, 0);
    }

    public static String getFormat(String url) {
        String format = null;
        try {
            String path = Uri.parse(url).getPath();
            int lastDot = path.lastIndexOf(".");
            format = path.substring(lastDot + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 获取推荐的图片格式
     *
     * @param url
     * @return
     */
    public static String getRecommendFormat(String url) {
        /*String format = getFormat(url);
        if (isValidFormat(format)) {
            format = "webp";
        }
        return format;*/
        //全面支持webp格式
        return "webp";
    }

    public static String getCompressUrl(String srcUrl, int w, int h, int q) {
        return getCompressUrl(srcUrl, w, h, q, getRecommendFormat(srcUrl));
    }

    public static String changeDefaultFormat(String url) {
        return changeFormat(url, getRecommendFormat(url));
    }

    public static String changeFormat(String url, String format) {
        return getCompressUrl(url, 0, 0, 0, format);
    }

    /**
     * 处理网络图片的请求参数,非网络图片直接返回传入的url
     *
     * @param srcUrl
     * @param w
     * @param h
     * @param q
     * @param format
     * @return
     */
    public static String getCompressUrl(String srcUrl, int w, int h, int q, String format) {
        if (srcUrl == null) return "";
        if (!srcUrl.startsWith("http")) {
            return srcUrl;
        }
        Uri srcUri = Uri.parse(srcUrl);
        StringBuffer querySb = new StringBuffer();
        querySb.append("imageView2/0");
        //宽度
        if (w > 0) {
            querySb.append("/w/" + w);
        }
        //高度
        if (h > 0) {
            querySb.append("/h/" + h);
        }
        //质量
        if (q > 0) {
            querySb.append("/q/" + q);
        }
        //格式
        if (!TextUtils.isEmpty(format)) {
            querySb.append("/format/" + format);
        }
        //ignore-error/1的意思是当出现错误时,返回原图
        querySb.append("/ignore-error/1");
        srcUri = srcUri.buildUpon().query(querySb.toString()).build();
        String outUrl = Uri.decode(srcUri.toString());
        StringBuffer logSb = new StringBuffer();
        logSb.append("====================================================================================================================\n");
        logSb.append("       srcUrl:" + srcUrl + "\n");
        logSb.append("compressedUrl:" + outUrl + "\n");
        logSb.append("====================================================================================================================\n");
        Log.d(TAG, logSb.toString());
        return outUrl;
    }


    public static void recycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public static final boolean isJpg(String format) {
        return "jpg".equalsIgnoreCase(format)
                || "jpeg".equalsIgnoreCase(format);
    }

    public static final boolean isStaticFormat(String format) {
        return isJpg(format)
                || "png".equalsIgnoreCase(format)
                || "bmp".equalsIgnoreCase(format);
    }

    public static final boolean isDynamicFormat(String format) {
        return "gif".equalsIgnoreCase(format);
    }

    public static final boolean isValidFormat(String format) {
        return isStaticFormat(format) || isDynamicFormat(format);
    }

    public static Bitmap addPadding(Bitmap bmp, int color) {

        if (bmp == null) {
            return null;
        }

        int biggerParam = Math.max(bmp.getWidth(), bmp.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(biggerParam, biggerParam, bmp.getConfig());
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);

        int top = bmp.getHeight() > bmp.getWidth() ? 0 : (bmp.getWidth() - bmp.getHeight()) / 2;
        int left = bmp.getWidth() > bmp.getHeight() ? 0 : (bmp.getHeight() - bmp.getWidth()) / 2;

        canvas.drawBitmap(bmp, left, top, null);
        return bitmap;
    }
}
