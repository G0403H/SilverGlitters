package com.wordpress.zeel.uploadapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String mProductTitle, mDescription, mWeblink, mCategoryName;
    String subimageURL;
    double subprice;

    TextView mTextViewPrice, mTextViewDescription, mTextViewWeblink;
    EditText mEditTextPrice, mEditTextDescription, mEditTextWeblink;
    Button mBtnSavePrice, mBtnSaveDescription, mBtnSaveWeblink,mBtnAdd;
    ImageView mToolbarImage;
    private Uri mImageUri;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private String mDocumentKey;
    private DatabaseReference mDatabaseRef;
    private Upload upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextViewPrice = findViewById(R.id.text_price);
        mTextViewDescription = findViewById(R.id.text_description);
        mTextViewWeblink = findViewById(R.id.text_link);
        mToolbarImage = findViewById(R.id.toolbar_image);
        mEditTextPrice = findViewById(R.id.text_price_edit);
        mEditTextDescription = findViewById(R.id.text_description_edit);
        mEditTextWeblink = findViewById(R.id.text_link_edit);
        mBtnSavePrice = findViewById(R.id.btn_save_price);
        mBtnSaveDescription = findViewById(R.id.btn_save_description);
        mBtnSaveWeblink = findViewById(R.id.btn_save_weblink);
        mBtnAdd = findViewById(R.id.btn_add);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mProductTitle = bundle.getString("Subcategory_title");
            mCategoryName = bundle.getString("Subcategory_categoryName");
            subimageURL = bundle.getString("Subcategory_imageURL");
            subprice = bundle.getDouble("Subcategory_price");
            mDescription = bundle.getString("Subcategory_description");
            mWeblink = bundle.getString("Subcategory_weblink");
            mDocumentKey = bundle.getString("DB_KEY");

            upload = new Upload(mProductTitle, subimageURL, mCategoryName, String.valueOf(subprice), mDescription, mWeblink);
            upload.setOtherImageURLs(bundle.getStringArrayList("Subcategory_urlList"));

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(mProductTitle);

            Picasso.get()
                    .load(subimageURL)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .fit()
                    .centerCrop()
                    .into(mToolbarImage);

            mTextViewPrice.setText("₹" + String.format("%.2f", subprice));
            mTextViewDescription.setText(mDescription);
            mTextViewWeblink.setText(mWeblink);
        }

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads").child(mCategoryName).child(mDocumentKey);

        mTextViewPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewPrice.setVisibility(View.INVISIBLE);
                mEditTextPrice.setVisibility(View.VISIBLE);
                mBtnSavePrice.setVisibility(View.VISIBLE);

                mEditTextPrice.setText(mTextViewPrice.getText().toString().substring(1));
            }
        });

        mTextViewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewDescription.setVisibility(View.INVISIBLE);
                mEditTextDescription.setVisibility(View.VISIBLE);
                mBtnSaveDescription.setVisibility(View.VISIBLE);

                mEditTextDescription.setText(mTextViewDescription.getText());
            }
        });

        mTextViewWeblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextViewWeblink.setVisibility(View.INVISIBLE);
                mEditTextWeblink.setVisibility(View.VISIBLE);
                mBtnSaveWeblink.setVisibility(View.VISIBLE);

                mEditTextWeblink.setText(mTextViewWeblink.getText());
            }
        });

        mBtnSavePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrice();
            }
        });

        mBtnSaveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDescription();
            }
        });

        mBtnSaveWeblink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWeblink();
            }
        });

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            uploadFile();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String downloadURL = taskSnapshot.getDownloadUrl().toString();
//                            Log.d("myTag", downloadURL);

                            upload.addUrl(downloadURL);
                            mDatabaseRef.setValue(upload);
                            Toast.makeText(DetailActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePrice() {
        mTextViewPrice.setText("₹" + mEditTextPrice.getText().toString());

        mBtnSavePrice.setVisibility(View.INVISIBLE);
        mEditTextPrice.setVisibility(View.INVISIBLE);
        mTextViewPrice.setVisibility(View.VISIBLE);

        double newPrice = Double.parseDouble(mEditTextPrice.getText().toString());
        upload.setPrice(newPrice);
        mDatabaseRef.setValue(upload);
    }

    private void saveDescription() {
        mTextViewDescription.setText(mEditTextDescription.getText().toString());

        mBtnSaveDescription.setVisibility(View.INVISIBLE);
        mEditTextDescription.setVisibility(View.INVISIBLE);
        mTextViewDescription.setVisibility(View.VISIBLE);

        upload.setDescription(mTextViewDescription.getText().toString());
        mDatabaseRef.setValue(upload);
    }

    private void saveWeblink() {
        mTextViewWeblink.setText(mEditTextWeblink.getText().toString());

        mBtnSaveWeblink.setVisibility(View.INVISIBLE);
        mEditTextWeblink.setVisibility(View.INVISIBLE);
        mTextViewWeblink.setVisibility(View.VISIBLE);

        upload.setWeblink(mTextViewWeblink.getText().toString());
        mDatabaseRef.setValue(upload);
    }
}
