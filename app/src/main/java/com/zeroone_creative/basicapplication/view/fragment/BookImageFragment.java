package com.zeroone_creative.basicapplication.view.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zeroone_creative.basicapplication.R;

/**
 * A simple {@link android.support.v4.app.DialogFragment} subclass.
 * This fragment is view one message.
 * Use the {@link com.zeroone_creative.basicapplication.view.fragment.BookImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookImageFragment extends DialogFragment {

    private static String EXTRA_IMAGE_URL = "extra_image_url";
    private ImageView mImageView;

    public static BookImageFragment newInstance(String imageUrl) {
        BookImageFragment fragment = new BookImageFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_IMAGE_URL, imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_book_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mImageView = (ImageView) dialog.findViewById(R.id.book_image_imageview);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            Picasso.with(getActivity()).load(args.getString(EXTRA_IMAGE_URL)).into(mImageView);
        } else {
            dismiss();
        }
        return dialog;
    }

}