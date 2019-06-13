package irstit.transport.Drivers;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import irstit.transport.R;

public class ReportDriverInfo extends Fragment {

    ProgressBar progressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_driver_info, container, false);

        ImageView imageView = view.findViewById(R.id.ReportDriverInfo_Picture);
        progressbar = view.findViewById(R.id.progressbar);
        Log.e("id123", getArguments().getString("id") + " |");
        Picasso.with(getContext())
                .load("http://cpanel.traffictakestan.ir/upload/decree/" + getArguments().getString("id") + ".jpg")
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressbar.setVisibility(View.GONE);
//                        getTarget();
                        Picasso.with(getContext()).load("http://cpanel.traffictakestan.ir/upload/decree/" + getArguments().getString("id") + ".jpg")
                                .into(picassoImageTarget(getContext(), "imageDir", "my_image.jpeg"));
                        Toast.makeText(getContext(), "عکس در پوشه دانلود ذخیره شد", Toast.LENGTH_LONG).show();
//                        SaveImage();
//                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
//
//                                Uri.parse("file://" + Environment.getExternalStorageDirectory())));

                    }

                    @Override
                    public void onError() {
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "حطا در برقراری ارتباط با سرور", Toast.LENGTH_LONG).show();

                    }
                });


        return view;
    }


    private static Target getTarget(final String url) {
        Target target = new Target() {

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(() -> {

//                    File file = new File("Traffic");
                    File myDir = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "/Traffic");
                    try {
                        if (!myDir.exists()) {
                            myDir.mkdirs();
                        }
                        FileOutputStream ostream = new FileOutputStream(myDir);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                        ostream.flush();
                        ostream.close();
                    } catch (IOException e) {
                        Log.e("IOException", e.getLocalizedMessage());
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }


    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
//        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        final File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Random generator = new Random();
                        int n = 10000;
                        n = generator.nextInt(n);
                        String fname = "Image-" + n + ".jpeg";
                        final File myImageFile = new File(directory, fname); // Create image file
                        FileOutputStream fos = null;
                        try {

                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Traffic");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpeg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
