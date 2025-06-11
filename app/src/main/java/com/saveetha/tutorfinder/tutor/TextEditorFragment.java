package com.saveetha.tutorfinder.tutor;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.saveetha.tutorfinder.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TextEditorFragment extends Fragment {
    private AppCompatImageButton ibAddImage, ibAlignRight, ibAlignLeft, ibAlignCenter, ibAlignJustify,
            ibNone, ibColorPicker, ibCopy;
    private AppCompatButton abUnderline, abBold, abItalic,abUndo,abRedo;
    private EditText etTextArea;
    private int mDefaultColor;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_editor, container, false);
        ibAddImage = view.findViewById(R.id.add_image);
        ibAlignLeft = view.findViewById(R.id.align_left);
        ibAlignRight = view.findViewById(R.id.align_right);
        ibAlignCenter = view.findViewById(R.id.align_center);
        ibAlignJustify = view.findViewById(R.id.align_justify);
        etTextArea = view.findViewById(R.id.text_area);
        abBold = view.findViewById(R.id.bold);
        abItalic = view.findViewById(R.id.italic);
        ibCopy = view.findViewById(R.id.copy);
        abUnderline = view.findViewById(R.id.underline);
        ibNone = view.findViewById(R.id.none);
        abUndo=view.findViewById(R.id.undo);
        abRedo=view.findViewById(R.id.redo);
        ibColorPicker = view.findViewById(R.id.color_picker);

        onClickListener(view);
        return view;
    }

    private void addImageBetweentext(Drawable drawable, EditText editText1) {
        drawable .setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        int selectionCursor = editText1.getSelectionStart();
        editText1.getText().insert(selectionCursor, ".");
        selectionCursor = editText1.getSelectionStart();

        SpannableStringBuilder builder = new SpannableStringBuilder(editText1.getText());
        ImageSpan imageSpan=new ImageSpan(drawable);
        builder.setSpan(imageSpan, selectionCursor - ".".length(), selectionCursor,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        editText1.setText(builder);
        editText1.setSelection(selectionCursor);

    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // Checking request code
            if (requestCode == 1) {
                // Getting selected image URI
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    // Updating image view
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
//                                    Drawable.createFromStream(inputStream, uri.toString());
                        addImageBetweentext(Drawable.createFromStream(inputStream, selectedImageUri.toString()), etTextArea);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void onClickListener(View view) {
        TextViewUndoRedo mTextViewUndoRedo = new TextViewUndoRedo(etTextArea);
        abUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewUndoRedo.undo();
            }

        });
        abRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewUndoRedo.redo();
            }

        });
        ibAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Starting activity for result
                startActivityForResult(intent, 1);
//                addImageBetweentext(getResources().getDrawable(R.drawable.me), etTextArea);
            }

        });
        ibAlignLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextArea.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                etTextArea.setText(spannableString);
            }
        });
        ibAlignRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextArea.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                etTextArea.setText(spannableString);
            }
        });
        ibAlignCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextArea.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                etTextArea.setText(spannableString);

            }
        });
        ibAlignJustify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etTextArea.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                etTextArea.setText(spannableString);
            }
        });
        abBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), etTextArea.getSelectionStart(),
                        etTextArea.getSelectionEnd(), 0);
                etTextArea.setText(spannableString);
            }
        });
        abItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                spannableString.setSpan(new StyleSpan(Typeface.ITALIC), etTextArea.getSelectionStart(),
                        etTextArea.getSelectionEnd(), 0);
                etTextArea.setText(spannableString);
            }
        });
        abUnderline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                spannableString.setSpan(new UnderlineSpan(), etTextArea.getSelectionStart(),
                        etTextArea.getSelectionEnd(), 0);
                etTextArea.setText(spannableString);
            }
        });
        ibNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spannableString = etTextArea.getText().toString();
                etTextArea.setText(spannableString);
            }
        });
        ibColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                spannableString.setSpan(new UnderlineSpan(), etTextArea.getSelectionStart(),
                        etTextArea.getSelectionEnd(), 0);
                etTextArea.setText(spannableString);
                openColorPickerDialogue();
            }
        });
        ibCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spannable spannableString = new SpannableStringBuilder(etTextArea.getText());
                spannableString.setSpan(new UnderlineSpan(), etTextArea.getSelectionStart(),
                        etTextArea.getSelectionEnd(), 0);
                etTextArea.setText(spannableString);
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", spannableString);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Copied to Copied Clipboard", Toast.LENGTH_SHORT).show();

//
//                    ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
//                    String txtCopy = etTextArea.getText().toString();
//                    ClipData clipData = ClipData.newPlainText("text", txtCopy);
//                    clipboardManager.setPrimaryClip(clipData);
//                    Toast.makeText(getContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
//
            }
        });
        //        etTextArea.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//            @Override
//            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//                return true;
//            }
//
//            @Override
//            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
////                menu.clear();
//                return true;
//            }
//
//            @Override
//            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                return true;
//            }
//
//            @Override
//            public void onDestroyActionMode(ActionMode actionMode) {
//
//            }
//        });

    }

    public int openColorPickerDialogue() {
        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(getContext(), mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        mDefaultColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
//                        mColorPreview.setBackgroundColor(mDefaultColor);
                    }
                });
        colorPickerDialogue.show();

        return mDefaultColor;
    }
}